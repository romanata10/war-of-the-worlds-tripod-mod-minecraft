package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The Nichols machine: a heavy saucer with two red eyes under the brow and a stubby cannon on each
 * side.
 *
 * <p><b>Signature ability</b>: the <b>twin salvo</b>. Both cannons fire on their own rhythm and with
 * their own beam — a cutting lance from the left, a heat ray from the right — so the machine keeps
 * two separate beams burning at once and can hit a second target while the first is still burning.</p>
 */
public class SaucerTripodEntity extends AbstractTripodEntity {

    private static final int TWIN_INTERVAL = 100;

    public SaucerTripodEntity(EntityType<? extends SaucerTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(380.0D, 0.27D, 16.0D, 10.0D, 7.0D, 0.9D, 56.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(TWIN_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || !this.hasLineOfSight(target)) {
            return;
        }
        this.setAbilityCharging(true);
        this.playChargeSound();
        this.fireBeamAt(target, BeamKind.LANCE, 9.0F);
        LivingEntity second = null;
        if (this.level() instanceof ServerLevel server) {
            second = server.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(30.0D),
                            entity -> entity != this && entity != target && entity.isAlive() && this.canAttack(entity))
                    .stream()
                    .min(java.util.Comparator.comparingDouble(this::distanceToSqr))
                    .orElse(null);
        }
        this.fireBeamAt(second == null ? target : second, BeamKind.HEAT_RAY, 7.0F);
        if (second != null) {
            second.setSecondsOnFire(5);
        }
        if (this.level() instanceof ServerLevel server) {
            server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BEACON_ACTIVATE,
                    SoundSource.HOSTILE, 1.4F, 0.6F);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        this.level().addParticle(this.random.nextBoolean() ? ParticleTypes.SMOKE : ParticleTypes.FLAME,
                this.getX() + (this.random.nextDouble() - 0.5D) * 2.4D,
                this.getY() + this.getBbHeight() * 0.6D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 2.4D, 0.0D, 0.0D, 0.0D);
    }
}
