package dev.arena.wotw.entity;

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
 * The cyclops of the second cover: an egg of olive plate with one huge eye and two clawed arms.
 *
 * <p><b>Signature ability</b>: the <b>hypnotic stare</b>. As long as it can see its victim the eye
 * holds them rooted — the legs stop answering, the arms go weak, and the machine simply walks up and
 * takes them with the claws.</p>
 */
public class CyclopsTripodEntity extends AbstractTripodEntity {

    private static final int STARE_INTERVAL = 30;
    private static final double STARE_RANGE = 20.0D;

    public CyclopsTripodEntity(EntityType<? extends CyclopsTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(330.0D, 0.27D, 14.0D, 8.0D, 6.0D, 0.85D, 44.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(STARE_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > STARE_RANGE * STARE_RANGE
                || !this.hasLineOfSight(target)) {
            return;
        }
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4, false, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 2, false, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 3, false, true, true));
        target.setDeltaMovement(target.getDeltaMovement().scale(0.1D));
        target.hurtMarked = true;
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        this.setAbilityCharging(true);
        Vec3 eye = this.getBeamOrigin();
        Vec3 aim = target.getEyePosition();
        Vec3 step = aim.subtract(eye).scale(1.0D / 16.0D);
        for (int index = 0; index < 16; index++) {
            Vec3 point = eye.add(step.scale(index));
            server.sendParticles(ParticleTypes.END_ROD, point.x, point.y, point.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ELDER_GUARDIAN_CURSE,
                SoundSource.HOSTILE, 1.2F, 1.6F);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(5) != 0) {
            return;
        }
        Vec3 eye = this.getBeamOrigin();
        this.level().addParticle(ParticleTypes.END_ROD, eye.x, eye.y, eye.z, 0.0D, 0.01D, 0.0D);
    }
}
