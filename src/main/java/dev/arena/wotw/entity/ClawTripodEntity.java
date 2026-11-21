package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * The pincer machine: a pale cowl, two headlamps and a bundle of claw fingers.
 *
 * <p><b>Signature ability</b>: the <b>grip</b>. The claw closes on the victim and pins them where
 * they stand — no walking, no jumping, no swinging — and while they hang there the heat ray comes
 * up and finishes the job.</p>
 */
public class ClawTripodEntity extends AbstractTripodEntity {

    private static final int HOLD_CHECK = 20;
    private static final int SHOT_INTERVAL = 100;
    private static final double GRIP_RANGE = 15.0D;

    public ClawTripodEntity(EntityType<? extends ClawTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(380.0D, 0.26D, 16.0D, 9.0D, 6.0D, 0.9D, 48.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(HOLD_CHECK)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > GRIP_RANGE * GRIP_RANGE
                || !this.hasLineOfSight(target)) {
            return;
        }
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 6, false, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 4, false, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 2, false, true, true));
        Vec3 hold = target.position();
        target.setDeltaMovement(0.0D, Math.min(0.0D, target.getDeltaMovement().y), 0.0D);
        target.hurtMarked = true;
        this.setAbilityCharging(true);
        if (this.level() instanceof ServerLevel server) {
            Vec3 from = this.getBeamOrigin();
            Vec3 step = hold.add(0.0D, target.getBbHeight() * 0.5D, 0.0D).subtract(from).scale(1.0D / 14.0D);
            for (int index = 0; index < 14; index++) {
                Vec3 point = from.add(step.scale(index));
                server.sendParticles(ParticleTypes.CRIT, point.x, point.y, point.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        if (this.onIntervalZero(SHOT_INTERVAL)) {
            this.fireBeamAt(target, BeamKind.HEAT_RAY, 10.0F);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 1.6F, 0.8F);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        for (int index = 0; index < 3; index++) {
            this.level().addParticle(ParticleTypes.END_ROD,
                    this.getX() + (this.random.nextDouble() - 0.5D) * 2.0D,
                    this.getY() + 1.0D + this.random.nextDouble() * 2.0D,
                    this.getZ() + (this.random.nextDouble() - 0.5D) * 2.0D, 0.0D, -0.01D, 0.0D);
        }
    }
}
