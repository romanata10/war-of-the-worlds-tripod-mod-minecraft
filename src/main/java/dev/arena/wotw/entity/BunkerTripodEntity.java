package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

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

    public BunkerTripodEntity(EntityType<? extends BunkerTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
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
        if (this.distanceToSqr(target) > 40.0D * 40.0D || !this.hasLineOfSight(target)) {
            return;
        }
        Vec3 aim = target.getEyePosition();
        for (int port = 0; port < PORTS; port++) {
            double angle = Math.PI * 2.0D * port / PORTS;
            double radius = this.getBbWidth() * 0.45D;
            Vec3 origin = new Vec3(
                    this.getX() + Math.cos(angle + this.yBodyRot * 0.0175D) * radius,
                    this.getY() + this.getBbHeight() * (0.35D + 0.06D * (port % 3)),
                    this.getZ() + Math.sin(angle + this.yBodyRot * 0.0175D) * radius);
            Vec3 delta = aim.subtract(origin);
            if (delta.lengthSquared() < 0.1D) {
                continue;
            }
            this.fireBeam(origin, delta.normalize(), BeamKind.LANCE, 3.5F);
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 1.0F, 1.6F);
        if (this.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(net.minecraft.core.particles.ParticleTypes.SMOKE,
                    this.getX(), this.getY() + this.getBbHeight() * 0.5D, this.getZ(), 14, 1.0D, 1.0D, 1.0D, 0.02D);
        }
        this.setAbilityCooldown(FIRE_INTERVAL);
    }
}
