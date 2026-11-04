package dev.arena.wotw.entity;

import dev.arena.wotw.registry.ModBlocks;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.Level;

/**
 * The giant Martian capsule.
 *
 * <p>It is launched far above the world and falls straight down. On impact it digs a crater, burns
 * everything inside a twelve block radius with a heat ray, releases a squad of Martians and then
 * sends three random tripods out into the world — some of them carrying the red root seed bed that
 * turns the ground behind them into a Martian thicket.</p>
 */
public class CapsuleEntity extends Entity {

    /** Radius of the heat ray burst: everything alive inside dies. */
    public static final double BURN_RADIUS = 12.0D;
    /** How many Martians climb out of a freshly landed capsule. */
    private static final int MARTIAN_COUNT = 4;
    /** How many machines follow them. */
    private static final int TRIPOD_COUNT = 3;
    /** Drop height above the surface. */
    private static final double DROP_HEIGHT = 70.0D;

    private int fallTicks;
    private int landedTicks = -1;
    private boolean announced;
    private boolean tripodsCarryRoots;

    public CapsuleEntity(EntityType<? extends CapsuleEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    /**
     * Drops a capsule over the given column and returns it, or {@code null} if the entity type
     * refused to create an instance.
     */
    public static CapsuleEntity drop(ServerLevel world, double x, double z, boolean announce) {
        CapsuleEntity capsule = ModEntities.CAPSULE.create(world);
        if (capsule == null) {
            return null;
        }
        int top = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mth.floor(x), Mth.floor(z));
        capsule.moveTo(x, top + DROP_HEIGHT, z, 0.0F, 0.0F);
        capsule.announced = announce;
        capsule.tripodsCarryRoots = world.getRandom().nextFloat() < 0.65F;
        world.addFreshEntity(capsule);
        if (announce) {
            Component warning = Component.translatable("wotw.event.capsule_incoming").formatted(ChatFormatting.GOLD);
            for (ServerPlayer player : world.getPlayers()) {
                if (player.distanceToSqr(x, top, z) < 200.0D * 200.0D) {
                    player.displayClientMessage(warning, false);
                }
            }
        }
        return capsule;
    }

    public boolean isLanded() {
        return this.landedTicks >= 0;
    }

    /* ------------------------------------------------------------------ */
    /*  Falling                                                            */
    /* ------------------------------------------------------------------ */

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.clientTick();
            return;
        }
        if (this.landedTicks >= 0) {
            this.tickLanded();
            return;
        }

        this.fallTicks++;
        double before = this.getY();
        this.setDeltaMovement(0.0D, -1.15D, 0.0D);
        this.move(MoverType.SELF, this.getDeltaMovement());
        boolean stuck = this.getY() > before - 0.02D;
        if (this.onGround() || stuck || this.fallTicks > 600) {
            this.land();
        }
    }

    private void clientTick() {
        if (this.random.nextInt(3) == 0) {
            this.level().addParticle(ParticleTypes.LARGE_SMOKE,
                    this.getX() + (this.random.nextDouble() - 0.5D) * 1.6D,
                    this.getY() + 1.2D,
                    this.getZ() + (this.random.nextDouble() - 0.5D) * 1.6D,
                    0.0D, 0.02D, 0.0D);
        }
    }

    /* ------------------------------------------------------------------ */
    /*  Impact                                                             */
    /* ------------------------------------------------------------------ */

    private void land() {
        if (!(this.level() instanceof ServerLevel world)) {
            this.discard();
            return;
        }

        this.landedTicks = 0;
        this.setDeltaMovement(Vec3.ZERO);
        BlockPos center = this.blockPosition();

        this.digCrater(world, center);
        world.setBlock(center.down(), ModBlocks.MARTIAN_CYLINDER.defaultBlockState(), Block.UPDATE_ALL);
        world.setBlock(center, ModBlocks.MARTIAN_CYLINDER.defaultBlockState(), Block.UPDATE_ALL);

        world.playSound(null, center, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 8.0F, 0.35F);
        world.playSound(null, center, SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 4.0F, 0.5F);
        world.sendParticles(ParticleTypes.EXPLOSION_EMITTER, center.getX() + 0.5D, center.getY() + 0.5D,
                center.getZ() + 0.5D, 6, 2.0D, 0.6D, 2.0D, 0.0D);
        world.sendParticles(ParticleTypes.LARGE_SMOKE, center.getX() + 0.5D, center.getY() + 0.8D,
                center.getZ() + 0.5D, 120, 3.0D, 1.2D, 3.0D, 0.08D);

        this.burn(world, center);

        if (this.announced) {
            Component message = Component.translatable("wotw.event.landing").formatted(ChatFormatting.RED);
            for (ServerPlayer player : world.getPlayers()) {
                if (player.distanceToSqr(center.getX(), center.getY(), center.getZ()) < 160.0D * 160.0D) {
                    player.displayClientMessage(message, false);
                }
            }
        }
    }

    private void digCrater(ServerLevel world, BlockPos center) {
        for (BlockPos pos : BlockPos.betweenClosed(center.add(-4, -4, -4), center.add(4, 1, 4))) {
            double distance = pos.distToCenterSqr(center.getX() + 0.5D, center.getY() + 0.5D, center.getZ() + 0.5D);
            if (distance > 17.0D || pos.getY() <= world.getMinBuildHeight()) {
                continue;
            }
            if (!world.getBlockState(pos).isAir()) {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            }
        }
    }

    /** The heat ray: everything alive inside {@link #BURN_RADIUS} is killed and set alight. */
    private void burn(ServerLevel world, BlockPos center) {
        for (LivingEntity victim : world.getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(BURN_RADIUS),
                entity -> !(entity instanceof MartianEntity) && !(entity instanceof MartianMasterEntity))) {
            if (victim.distanceToSqr(center.getX(), center.getY(), center.getZ()) > BURN_RADIUS * BURN_RADIUS) {
                continue;
            }
            victim.setSecondsOnFire(20);
            victim.hurt(world.damageSources().magic(), 1000.0F);
            world.sendParticles(ParticleTypes.FLAME, victim.getX(), victim.getY() + 0.8D, victim.getZ(),
                    24, 0.4D, 0.8D, 0.4D, 0.04D);
        }
    }

    private void tickLanded() {
        ServerLevel world = (ServerLevel) this.level();
        this.landedTicks++;

        if (this.landedTicks == 30) {
            this.releaseCrew(world);
            this.discard();
        }
    }

    private void releaseCrew(ServerLevel world) {
        BlockPos center = this.blockPosition();
        // a capsule that came down in the sea brings the water tripod instead of a walker
        boolean atSea = world.getFluidState(center).is(net.minecraft.tags.FluidTags.WATER)
                || world.getFluidState(center.down(3)).is(net.minecraft.tags.FluidTags.WATER);
        for (int i = 0; i < MARTIAN_COUNT; i++) {
            EntityType<?> type = i == 0 && world.getRandom().nextFloat() < 0.25F
                    ? ModEntities.MARTIAN_MASTER
                    : ModEntities.MARTIAN;
            spawnAt(world,
                    Mth.floor(center.getX() + world.getRandom().nextDouble() * 8.0D - 4.0D),
                    Mth.floor(center.getZ() + world.getRandom().nextDouble() * 8.0D - 4.0D),
                    type);
        }

        for (int i = 0; i < TRIPOD_COUNT; i++) {
            EntityType<?> type = atSea ? ModEntities.WATER_TRIPOD : ModEntities.randomTripod(world.getRandom());
            double x = center.getX() + world.getRandom().nextDouble() * 20.0D - 10.0D;
            double z = center.getZ() + world.getRandom().nextDouble() * 20.0D - 10.0D;
            Entity entity = spawnAt(world, Mth.floor(x), Mth.floor(z), type);
            if (entity instanceof AbstractTripodEntity tripod && this.tripodsCarryRoots) {
                tripod.setRootTrail(true);
            }
        }
    }

    private Entity spawnAt(ServerLevel world, int x, int z, EntityType<?> type) {
        Entity entity = type.create(world);
        if (entity == null) {
            return null;
        }
        int y = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
        entity.moveTo(x + 0.5D, y, z + 0.5D, world.getRandom().nextFloat() * 360.0F, 0.0F);
        if (entity instanceof Mob mob) {
            mob.setPersistenceRequired();
            mob.finalizeSpawn(world, world.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.EVENT, null, null);
        }
        world.addFreshEntity(entity);
        return entity;
    }

    /* ------------------------------------------------------------------ */
    /*  Plumbing                                                           */
    /* ------------------------------------------------------------------ */

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putInt("FallTicks", this.fallTicks);
        nbt.putInt("LandedTicks", this.landedTicks);
        nbt.putBoolean("Announced", this.announced);
        nbt.putBoolean("Roots", this.tripodsCarryRoots);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        this.fallTicks = nbt.getInt("FallTicks");
        this.landedTicks = nbt.getInt("LandedTicks");
        this.announced = nbt.getBoolean("Announced");
        this.tripodsCarryRoots = nbt.getBoolean("Roots");
    }
}
