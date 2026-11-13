package dev.arena.wotw.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;

/**
 * The Martian factory: a riveted barrel of slats with two furnace mouths, a chimney spitting flame,
 * pincer cranes and four thin legs.
 *
 * <p><b>Signature ability</b>: the <b>molten pour</b>. The furnace opens and slag runs out of the
 * mouths, setting a ring of fire around its prey and burning whatever stands in it. It never chases
 * anybody — it builds the trap where you are standing.</p>
 */
public class FactoryTripodEntity extends AbstractTripodEntity {

    private static final int POUR_INTERVAL = 200;
    private static final double POUR_RANGE = 18.0D;

    public FactoryTripodEntity(EntityType<? extends FactoryTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(320.0D, 0.26D, 12.0D, 9.0D, 6.0D, 0.9D, 54.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(POUR_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > POUR_RANGE * POUR_RANGE) {
            return;
        }
        int fires = 0;
        for (int index = 0; index < 10; index++) {
            double angle = Math.PI * 2.0D * index / 10.0D;
            double radius = 2.4D + (index % 2) * 1.6D;
            BlockPos pos = BlockPos.containing(target.getX() + Math.cos(angle) * radius,
                    target.getY(), target.getZ() + Math.sin(angle) * radius);
            if (this.placeFire(pos)) {
                fires++;
            }
        }
        target.hurt(this.damageSources().mobAttack(this), 6.0F);
        target.setSecondsOnFire(5);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.LAVA, target.getX(), target.getY() + 0.4D, target.getZ(),
                    16, 1.6D, 0.3D, 1.6D, 0.0D);
            server.playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.LAVA_POP, SoundSource.HOSTILE, 2.0F, 0.7F);
        }
    }

    private boolean placeFire(BlockPos pos) {
        if (!this.level().getBlockState(pos).isAir()) {
            return false;
        }
        BlockPos below = pos.below();
        if (!this.level().getBlockState(below).isFaceSturdy(this.level(), below, Direction.UP)) {
            return false;
        }
        return this.level().setBlockAndUpdate(pos, BaseFireBlock.getState(this.level(), pos));
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        double y = this.getY() + this.getBbHeight() * 0.92D;
        this.level().addParticle(ParticleTypes.FLAME,
                this.getX() + (this.random.nextDouble() - 0.5D) * 1.2D, y,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 1.2D, 0.0D, 0.03D, 0.0D);
    }
}
