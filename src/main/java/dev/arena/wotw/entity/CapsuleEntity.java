package dev.arena.wotw.entity;

import dev.arena.wotw.registry.ModBlocks;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

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

    public CapsuleEntity(EntityType<? extends CapsuleEntity> type, World world) {
        super(type, world);
        this.setNoGravity(true);
    }

    /**
     * Drops a capsule over the given column and returns it, or {@code null} if the entity type
     * refused to create an instance.
     */
    public static CapsuleEntity drop(ServerWorld world, double x, double z, boolean announce) {
        CapsuleEntity capsule = ModEntities.CAPSULE.create(world);
        if (capsule == null) {
            return null;
        }
        int top = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MathHelper.floor(x), MathHelper.floor(z));
        capsule.refreshPositionAndAngles(x, top + DROP_HEIGHT, z, 0.0F, 0.0F);
        capsule.announced = announce;
        capsule.tripodsCarryRoots = world.getRandom().nextFloat() < 0.65F;
        world.spawnEntity(capsule);
        if (announce) {
            Text warning = Text.translatable("wotw.event.capsule_incoming").formatted(Formatting.GOLD);
            for (ServerPlayerEntity player : world.getPlayers()) {
                if (player.squaredDistanceTo(x, top, z) < 200.0D * 200.0D) {
                    player.sendMessage(warning, false);
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
        if (this.getWorld().isClient) {
            this.clientTick();
            return;
        }
        if (this.landedTicks >= 0) {
            this.tickLanded();
            return;
        }

        this.fallTicks++;
        double before = this.getY();
        this.setVelocity(0.0D, -1.15D, 0.0D);
        this.move(MovementType.SELF, this.getVelocity());
        boolean stuck = this.getY() > before - 0.02D;
        if (this.isOnGround() || stuck || this.fallTicks > 600) {
            this.land();
        }
    }

    private void clientTick() {
        if (this.random.nextInt(3) == 0) {
            this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE,
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
        if (!(this.getWorld() instanceof ServerWorld world)) {
            this.discard();
            return;
        }

        this.landedTicks = 0;
        this.setVelocity(Vec3d.ZERO);
        BlockPos center = this.getBlockPos();

        this.digCrater(world, center);
        world.setBlockState(center.down(), ModBlocks.MARTIAN_CYLINDER.getDefaultState(), Block.NOTIFY_ALL);
        world.setBlockState(center, ModBlocks.MARTIAN_CYLINDER.getDefaultState(), Block.NOTIFY_ALL);

        world.playSound(null, center, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 8.0F, 0.35F);
        world.playSound(null, center, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 4.0F, 0.5F);
        world.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, center.getX() + 0.5D, center.getY() + 0.5D,
                center.getZ() + 0.5D, 6, 2.0D, 0.6D, 2.0D, 0.0D);
        world.spawnParticles(ParticleTypes.LARGE_SMOKE, center.getX() + 0.5D, center.getY() + 0.8D,
                center.getZ() + 0.5D, 120, 3.0D, 1.2D, 3.0D, 0.08D);

        this.burn(world, center);

        if (this.announced) {
            Text message = Text.translatable("wotw.event.landing").formatted(Formatting.RED);
            for (ServerPlayerEntity player : world.getPlayers()) {
                if (player.squaredDistanceTo(center.getX(), center.getY(), center.getZ()) < 160.0D * 160.0D) {
                    player.sendMessage(message, false);
                }
            }
        }
    }

    private void digCrater(ServerWorld world, BlockPos center) {
        for (BlockPos pos : BlockPos.iterate(center.add(-4, -4, -4), center.add(4, 1, 4))) {
            double distance = pos.getSquaredDistance(center.getX() + 0.5D, center.getY() + 0.5D, center.getZ() + 0.5D);
            if (distance > 17.0D || pos.getY() <= world.getBottomY()) {
                continue;
            }
            if (!world.getBlockState(pos).isAir()) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
            }
        }
    }

    /** The heat ray: everything alive inside {@link #BURN_RADIUS} is killed and set alight. */
    private void burn(ServerWorld world, BlockPos center) {
        for (LivingEntity victim : world.getEntitiesByClass(LivingEntity.class,
                this.getBoundingBox().expand(BURN_RADIUS),
                entity -> !(entity instanceof MartianEntity) && !(entity instanceof MartianMasterEntity))) {
            if (victim.squaredDistanceTo(center.getX(), center.getY(), center.getZ()) > BURN_RADIUS * BURN_RADIUS) {
                continue;
            }
            victim.setOnFireFor(20);
            victim.damage(world.getDamageSources().magic(), 1000.0F);
            world.spawnParticles(ParticleTypes.FLAME, victim.getX(), victim.getY() + 0.8D, victim.getZ(),
                    24, 0.4D, 0.8D, 0.4D, 0.04D);
        }
    }

    private void tickLanded() {
        ServerWorld world = (ServerWorld) this.getWorld();
        this.landedTicks++;

        if (this.landedTicks == 30) {
            this.releaseCrew(world);
            this.discard();
        }
    }

    private void releaseCrew(ServerWorld world) {
        BlockPos center = this.getBlockPos();
        for (int i = 0; i < MARTIAN_COUNT; i++) {
            EntityType<?> type = i == 0 && world.getRandom().nextFloat() < 0.25F
                    ? ModEntities.MARTIAN_MASTER
                    : ModEntities.MARTIAN;
            spawnAt(world,
                    MathHelper.floor(center.getX() + world.getRandom().nextDouble() * 8.0D - 4.0D),
                    MathHelper.floor(center.getZ() + world.getRandom().nextDouble() * 8.0D - 4.0D),
                    type);
        }

        for (int i = 0; i < TRIPOD_COUNT; i++) {
            EntityType<?> type = ModEntities.randomTripod(world.getRandom());
            double x = center.getX() + world.getRandom().nextDouble() * 20.0D - 10.0D;
            double z = center.getZ() + world.getRandom().nextDouble() * 20.0D - 10.0D;
            Entity entity = spawnAt(world, MathHelper.floor(x), MathHelper.floor(z), type);
            if (entity instanceof AbstractTripodEntity tripod && this.tripodsCarryRoots) {
                tripod.setRootTrail(true);
            }
        }
    }

    private Entity spawnAt(ServerWorld world, int x, int z, EntityType<?> type) {
        Entity entity = type.create(world);
        if (entity == null) {
            return null;
        }
        int y = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
        entity.refreshPositionAndAngles(x + 0.5D, y, z + 0.5D, world.getRandom().nextFloat() * 360.0F, 0.0F);
        if (entity instanceof MobEntity mob) {
            mob.setPersistent();
            mob.initialize(world, world.getLocalDifficulty(entity.getBlockPos()), SpawnReason.EVENT, null, null);
        }
        world.spawnEntity(entity);
        return entity;
    }

    /* ------------------------------------------------------------------ */
    /*  Plumbing                                                           */
    /* ------------------------------------------------------------------ */

    @Override
    protected void initDataTracker() {
    }

    @Override
    public boolean canHit() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("FallTicks", this.fallTicks);
        nbt.putInt("LandedTicks", this.landedTicks);
        nbt.putBoolean("Announced", this.announced);
        nbt.putBoolean("Roots", this.tripodsCarryRoots);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.fallTicks = nbt.getInt("FallTicks");
        this.landedTicks = nbt.getInt("LandedTicks");
        this.announced = nbt.getBoolean("Announced");
        this.tripodsCarryRoots = nbt.getBoolean("Roots");
    }
}
