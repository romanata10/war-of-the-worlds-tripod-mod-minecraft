package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Bunker tripod — a grey blockhouse on six thick legs, with six firing ports.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Firing ports</b> — all six windows open up at once: six tracer beams from six points of
 *     the hull, which makes standing right next to it a very bad idea.</li>
 * </ul>
 */
public class BunkerTripodEntity extends AbstractTripodEntity {

    private static final int FIRE_INTERVAL = 70;
    private static final int PORTS = 6;

    public BunkerTripodEntity(EntityType<? extends BunkerTripodEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(320.0D, 0.20D, 12.0D, 16.0D, 10.0D, 1.0D, 56.0D);
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
        if (this.squaredDistanceTo(target) > 40.0D * 40.0D || !this.canSee(target)) {
            return;
        }
        Vec3d aim = target.getEyePos();
        for (int port = 0; port < PORTS; port++) {
            double angle = Math.PI * 2.0D * port / PORTS;
            double radius = this.getWidth() * 0.45D;
            Vec3d origin = new Vec3d(
                    this.getX() + Math.cos(angle + this.bodyYaw * 0.0175D) * radius,
                    this.getY() + this.getHeight() * (0.35D + 0.06D * (port % 3)),
                    this.getZ() + Math.sin(angle + this.bodyYaw * 0.0175D) * radius);
            Vec3d delta = aim.subtract(origin);
            if (delta.lengthSquared() < 0.1D) {
                continue;
            }
            this.fireBeam(origin, delta.normalize(), BeamKind.LANCE, 3.5F);
        }
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0F, 1.6F);
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(net.minecraft.particle.ParticleTypes.SMOKE,
                    this.getX(), this.getY() + this.getHeight() * 0.5D, this.getZ(), 14, 1.0D, 1.0D, 1.0D, 0.02D);
        }
        this.setAbilityCooldown(FIRE_INTERVAL);
    }
}
