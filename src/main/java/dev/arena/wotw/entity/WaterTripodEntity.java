package dev.arena.wotw.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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

    public WaterTripodEntity(EntityType<? extends WaterTripodEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(260.0D, 0.22D, 16.0D, 12.0D, 8.0D, 1.0D, 44.0D);
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public float getStepHeight() {
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
        if (this.squaredDistanceTo(target) > PULL_RANGE * PULL_RANGE) {
            return;
        }
        Vec3d pull = new Vec3d(this.getX() - target.getX(), 0.0D, this.getZ() - target.getZ());
        if (pull.lengthSquared() < 0.01D) {
            return;
        }
        pull = pull.normalize().multiply(0.55D);
        target.addVelocity(pull.x, 0.18D, pull.z);
        target.velocityModified = true;
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1, false, false, false));

        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_DOLPHIN_SPLASH, SoundCategory.HOSTILE, 2.0F, 0.6F);
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.SPLASH,
                    this.getX(), this.getY() + 0.5D, this.getZ(), 40, 2.4D, 0.6D, 2.4D, 0.1D);
        }
        this.setAbilityCooldown(PULL_INTERVAL);
    }
}
