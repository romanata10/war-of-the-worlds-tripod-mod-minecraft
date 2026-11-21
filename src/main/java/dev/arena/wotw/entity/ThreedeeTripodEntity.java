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
import net.minecraft.world.phys.Vec3;

/**
 * The 3D-film machine: a ribbed white carapace over a dark hull with an orange core burning inside.
 *
 * <p><b>Signature ability</b>: the <b>sweep</b>. Instead of one beam it drags the ray across
 * everything in front of it — a cone eighteen blocks deep, hitting every living thing caught in it
 * at once.</p>
 */
public class ThreedeeTripodEntity extends AbstractTripodEntity {

    private static final int SWEEP_INTERVAL = 130;
    private static final double SWEEP_RANGE = 21.0D;
    private static final double SWEEP_COS = 0.55D;

    public ThreedeeTripodEntity(EntityType<? extends ThreedeeTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(360.0D, 0.28D, 13.0D, 9.0D, 6.0D, 0.85D, 50.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(SWEEP_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        Vec3 look = this.getLookAngle();
        int caught = 0;
        for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(SWEEP_RANGE),
                entity -> entity != this && entity.isAlive() && this.canAttack(entity))) {
            Vec3 toVictim = victim.position().subtract(this.position()).normalize();
            if (toVictim.dot(look) < SWEEP_COS) {
                continue;
            }
            victim.hurt(this.damageSources().mobAttack(this), 9.0F);
            victim.setSecondsOnFire(5);
            caught++;
        }
        this.setAbilityCharging(true);
        Vec3 origin = this.getBeamOrigin();
        for (int index = 0; index < 24; index++) {
            double spread = (this.random.nextDouble() - 0.5D) * 2.0D;
            Vec3 point = origin.add(look.scale(index)).add(spread, (this.random.nextDouble() - 0.5D) * 2.0D, spread);
            server.sendParticles(ParticleTypes.FLAME, point.x, point.y, point.z, 2, 0.1D, 0.1D, 0.1D, 0.01D);
        }
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BLAZE_SHOOT,
                SoundSource.HOSTILE, 2.0F, 0.7F);
        if (caught == 0) {
            LivingEntity target = this.getTarget();
            if (target == null) {
                this.fireBeamAt(origin, BeamKind.LASER, 6.0F);
            } else {
                this.fireBeamAt(target, BeamKind.LASER, 6.0F);
            }
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(2) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.FLAME,
                this.getX() + (this.random.nextDouble() - 0.5D) * 1.6D,
                this.getY() + this.getBbHeight() * 0.45D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 1.6D, 0.0D, 0.01D, 0.0D);
    }
}
