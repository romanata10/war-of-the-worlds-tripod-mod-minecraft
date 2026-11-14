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
 * The Chinese machine: a pale bell hanging over a bundle of long tendrils.
 *
 * <p><b>Signature ability</b>: the <b>drag</b>. The tendrils lash out, latch on and reel the victim
 * in towards the bell, lifting them off the ground while they are pulled — a machine that does not
 * have to chase anybody.</p>
 */
public class JellyTripodEntity extends AbstractTripodEntity {

    private static final int DRAG_INTERVAL = 40;
    private static final double DRAG_RANGE = 22.0D;

    public JellyTripodEntity(EntityType<? extends JellyTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(320.0D, 0.26D, 13.0D, 7.0D, 5.0D, 0.9D, 48.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(DRAG_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > DRAG_RANGE * DRAG_RANGE) {
            return;
        }
        Vec3 pull = this.position().subtract(target.position());
        if (pull.lengthSqr() < 1.0D) {
            return;
        }
        Vec3 direction = pull.normalize().scale(0.85D);
        target.setDeltaMovement(direction.x, 0.42D, direction.z);
        target.hurtMarked = true;
        target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 0, false, false, false));
        target.hurt(this.damageSources().mobAttack(this), 3.0F);
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        this.setAbilityCharging(true);
        Vec3 from = this.getBeamOrigin();
        Vec3 step = target.position().subtract(from).scale(1.0D / 12.0D);
        for (int index = 0; index < 12; index++) {
            Vec3 point = from.add(step.scale(index));
            server.sendParticles(ParticleTypes.ITEM_SLIME, point.x, point.y, point.z, 1, 0.1D, 0.1D, 0.1D, 0.0D);
        }
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.SLIME_ATTACK,
                SoundSource.HOSTILE, 1.4F, 1.2F);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(2) != 0) {
            return;
        }
        for (int index = 0; index < 3; index++) {
            double angle = this.random.nextDouble() * 6.283185307179586D;
            this.level().addParticle(ParticleTypes.ITEM_SLIME,
                    this.getX() + Math.cos(angle) * 1.2D,
                    this.getY() + 1.2D + this.random.nextDouble() * 2.5D,
                    this.getZ() + Math.sin(angle) * 1.2D, 0.0D, -0.02D, 0.0D);
        }
    }
}
