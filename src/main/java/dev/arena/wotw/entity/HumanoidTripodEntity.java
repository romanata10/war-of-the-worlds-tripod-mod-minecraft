package dev.arena.wotw.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
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
 * The humanoid Martian: a cowled skull with a lit visor, a chest of instruments, two jointed arms
 * with grippers and three legs.
 *
 * <p><b>Signature ability</b>: the <b>arms</b>. This machine does not shoot — it grabs its prey with
 * both grippers and throws it, hard, away from the hull. Fighting it near a cliff or a lava lake is
 * a losing proposition.</p>
 */
public class HumanoidTripodEntity extends AbstractTripodEntity {

    private static final int THROW_INTERVAL = 220;
    private static final double GRAB_RANGE = 8.0D;

    public HumanoidTripodEntity(EntityType<? extends HumanoidTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(280.0D, 0.32D, 14.0D, 8.0D, 5.0D, 0.7D, 50.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(THROW_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > GRAB_RANGE * GRAB_RANGE) {
            return;
        }
        Vec3 away = target.position().subtract(this.position());
        away = new Vec3(away.x, 0.0D, away.z);
        if (away.lengthSqr() < 0.01D) {
            away = new Vec3(0.0D, 0.0D, 1.0D);
        }
        away = away.normalize();
        target.setDeltaMovement(away.x * 1.15D, 1.05D, away.z * 1.15D);
        target.hurtMarked = true;
        target.hurt(this.damageSources().mobAttack(this), 7.0F);
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 1, false, true, true));
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.IRON_GOLEM_ATTACK, SoundSource.HOSTILE, 1.8F, 0.7F);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CRIT, target.getX(), target.getY() + 1.0D, target.getZ(),
                    18, 0.4D, 0.4D, 0.4D, 0.3D);
        }
    }
}
