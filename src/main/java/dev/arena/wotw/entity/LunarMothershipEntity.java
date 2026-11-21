package dev.arena.wotw.entity;

import dev.arena.wotw.registry.ModBlocks;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

/**
 * The mothership: a lens of a hull a thousand blocks across, hanging in the sky over the invasion.
 *
 * <p><b>Signature ability</b>: it is a <b>place</b> as much as a machine. The ship carries a deck of
 * lunar plate inside it — a real, walkable room built out of blocks when the ship arrives — and it
 * keeps dropping flying ships and nomads onto the world below.</p>
 */
public class LunarMothershipEntity extends AbstractTripodEntity {

    private static final int DROP_INTERVAL = 400;
    private static final int DECK_RADIUS = 17;

    private boolean deckBuilt;

    public LunarMothershipEntity(EntityType<? extends LunarMothershipEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(2400.0D, 0.16D, 24.0D, 14.0D, 10.0D, 1.0D, 80.0D);
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        if (!this.deckBuilt) {
            this.buildDeck(server);
            this.deckBuilt = true;
        }
        if (!this.onIntervalZero(DROP_INTERVAL)) {
            return;
        }
        for (int index = 0; index < 2; index++) {
            LunarFlyerEntity flyer = ModEntities.LUNAR_FLYER.create(server);
            if (flyer == null) {
                continue;
            }
            double angle = this.random.nextDouble() * 6.283185307179586D;
            flyer.moveTo(this.getX() + Math.cos(angle) * 26.0D, this.getY() - 12.0D,
                    this.getZ() + Math.sin(angle) * 26.0D, this.random.nextFloat() * 360.0F, 0.0F);
            flyer.finalizeSpawn(server, server.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            server.addFreshEntity(flyer);
        }
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BEACON_POWER_SELECT,
                SoundSource.HOSTILE, 4.0F, 0.5F);
    }

    /** Builds the habitable deck inside the hull: a floor, a wall of glass and four lamps. */
    private void buildDeck(ServerLevel server) {
        BlockPos center = this.blockPosition().below(6);
        for (int x = -DECK_RADIUS; x <= DECK_RADIUS; x++) {
            for (int z = -DECK_RADIUS; z <= DECK_RADIUS; z++) {
                BlockPos floor = center.offset(x, 0, z);
                server.setBlockAndUpdate(floor, ModBlocks.MOON_STONE.defaultBlockState());
                if (Math.abs(x) == DECK_RADIUS || Math.abs(z) == DECK_RADIUS) {
                    for (int y = 1; y <= 4; y++) {
                        server.setBlockAndUpdate(center.offset(x, y, z),
                                y == 3 ? ModBlocks.MOON_GLASS.defaultBlockState()
                                        : ModBlocks.LUNAR_HULL.defaultBlockState());
                    }
                    server.setBlockAndUpdate(center.offset(x, 5, z), ModBlocks.MOON_STONE.defaultBlockState());
                } else {
                    for (int y = 1; y <= 5; y++) {
                        server.setBlockAndUpdate(center.offset(x, y, z), Blocks.AIR.defaultBlockState());
                    }
                }
            }
        }
        for (int x = -10; x <= 10; x += 10) {
            for (int z = -10; z <= 10; z += 10) {
                server.setBlockAndUpdate(center.offset(x, 1, z), ModBlocks.LENS_PILLAR.defaultBlockState());
            }
        }
        server.playSound(null, center.getX(), center.getY(), center.getZ(), SoundEvents.BEACON_ACTIVATE,
                SoundSource.HOSTILE, 4.0F, 0.4F);
    }

    /** Where a player teleporting aboard ends up: the middle of the deck. */
    public BlockPos getDeckCenter() {
        return this.blockPosition().below(5);
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        for (int index = 0; index < 6; index++) {
            LunarNomadRedEntity nomad = ModEntities.LUNAR_NOMAD_RED.create(server);
            if (nomad == null) {
                continue;
            }
            double angle = this.random.nextDouble() * 6.283185307179586D;
            nomad.moveTo(this.getX() + Math.cos(angle) * 8.0D, this.getY() - 10.0D,
                    this.getZ() + Math.sin(angle) * 8.0D, this.random.nextFloat() * 360.0F, 0.0F);
            nomad.finalizeSpawn(server, server.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            server.addFreshEntity(nomad);
        }
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 12.0F, false,
                Level.ExplosionInteraction.MOB);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(2) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.END_ROD,
                this.getX() + (this.random.nextDouble() - 0.5D) * 60.0D,
                this.getY() + (this.random.nextDouble() - 0.5D) * 8.0D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 60.0D, 0.0D, -0.02D, 0.0D);
    }

    @Override
    public boolean canAttack(net.minecraft.world.entity.LivingEntity target) {
        return !(target instanceof LunarMothershipEntity) && super.canAttack(target);
    }
}
