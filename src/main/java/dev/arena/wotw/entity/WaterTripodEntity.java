package dev.arena.wotw.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

/**
 * The water tripod — an oil rig standing on three enormous legs.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Undertow</b> — it drags everything nearby towards its legs, so a player caught in the
 *     water is pulled under the hull where the melee attack waits.</li>
 *     <li><b>Seabed walker</b> — it never floats: currents do not move it and it strides along the
 *     ocean floor. Capsules that land in the sea bring this machine instead of a walker.</li>
 * </ul>
 */
public class WaterTripodEntity extends AbstractTripodEntity {

    private static final int PULL_INTERVAL = 80;
    private static final double PULL_RANGE = 14.0D;

    public WaterTripodEntity(EntityType<? extends WaterTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(260.0D, 0.22D, 16.0D, 12.0D, 8.0D, 1.0D, 44.0D);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public float maxUpStep() {
        return 4.0F;
    }

    @Override
    protected void serverTick() {
        if (!this.isAbilityReady()) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        if (this.distanceToSqr(target) > PULL_RANGE * PULL_RANGE) {
            return;
        }
        Vec3 pull = new Vec3(this.getX() - target.getX(), 0.0D, this.getZ() - target.getZ());
        if (pull.lengthSqr() < 0.01D) {
            return;
        }
        pull = pull.normalize().scale(0.55D);
        target.push(pull.x, 0.18D, pull.z);
        target.hurtMarked = true;
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, false, false));

        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.DOLPHIN_SPLASH, SoundSource.HOSTILE, 2.0F, 0.6F);
        if (this.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.SPLASH,
                    this.getX(), this.getY() + 0.5D, this.getZ(), 40, 2.4D, 0.6D, 2.4D, 0.1D);
        }
        this.setAbilityCooldown(PULL_INTERVAL);
    }
}
