package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * The pencil sketch machine: a shallow saucer with two antennae on legs as thin as a drawn line.
 *
 * <p><b>Signature ability</b>: the <b>needle shot</b> — a lance beam that reaches far past the range
 * of every other machine — and the <b>sidestep</b>: when something does hit it, the machine is no
 * longer where the shot went, it is ten blocks to the side.</p>
 */
public class SketchTripodEntity extends AbstractTripodEntity {

    private static final int SHOT_INTERVAL = 90;
    private static final double SHOT_RANGE = 62.0D;

    public SketchTripodEntity(EntityType<? extends SketchTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(180.0D, 0.33D, 9.0D, 4.0D, 3.0D, 0.6D, 68.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(SHOT_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > SHOT_RANGE * SHOT_RANGE
                || !this.hasLineOfSight(target)) {
            return;
        }
        this.setAbilityCharging(true);
        this.fireBeamAt(target, BeamKind.LANCE, 9.0F);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (!hurt || this.level().isClientSide || this.random.nextInt(3) != 0
                || !(this.level() instanceof ServerLevel server)) {
            return hurt;
        }
        // sidestep: pick a spot a few blocks away and be there instead
        for (int attempt = 0; attempt < 8; attempt++) {
            double angle = this.random.nextDouble() * 6.283185307179586D;
            double distance = 8.0D + this.random.nextDouble() * 4.0D;
            double x = this.getX() + Math.cos(angle) * distance;
            double z = this.getZ() + Math.sin(angle) * distance;
            int y = server.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING,
                    (int) Math.floor(x), (int) Math.floor(z));
            if (Math.abs(y - this.getY()) > 6.0D) {
                continue;
            }
            server.sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY() + this.getBbHeight() * 0.5D,
                    this.getZ(), 16, 0.6D, 0.8D, 0.6D, 0.05D);
            this.teleportTo(x, y, z);
            server.sendParticles(ParticleTypes.CLOUD, x, y + this.getBbHeight() * 0.5D, z,
                    16, 0.6D, 0.8D, 0.6D, 0.05D);
            server.playSound(null, x, y, z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0F, 1.5F);
            break;
        }
        return hurt;
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        Vec3 eye = this.getBeamOrigin();
        this.level().addParticle(ParticleTypes.END_ROD, eye.x, eye.y, eye.z, 0.0D, 0.0D, 0.0D);
    }
}
