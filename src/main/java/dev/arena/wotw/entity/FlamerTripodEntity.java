package dev.arena.wotw.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

/**
 * Flamer tripod — the heat ray was replaced with two fuel tanks and a nozzle.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Fire cone</b> — a short, wide jet of burning fuel. It only reaches seven blocks, but
 *     anything in front of it is set on fire and cooked alive, and it does not care about walls of
 *     mobs, only about the Martians being immune to it.</li>
 * </ul>
 */
public class FlamerTripodEntity extends AbstractTripodEntity {

    private static final double RANGE = 7.5D;
    private static final int FIRE_INTERVAL = 22;

    public FlamerTripodEntity(EntityType<? extends FlamerTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(170.0D, 0.30D, 10.0D, 8.0D, 4.0D, 0.8D, 40.0D);
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
        if (this.distanceToSqr(target) > (RANGE + 4.0D) * (RANGE + 4.0D)) {
            return;
        }
        Vec3 forward = this.getViewVector(1.0F);
        AABB box = this.getBoundingBox().inflate(RANGE);
        boolean hit = false;
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, box,
                entity -> entity.isAlive() && entity != this && !(entity instanceof AbstractTripodEntity))) {
            Vec3 toVictim = victim.position().add(0.0D, victim.getBbHeight() * 0.5D, 0.0D)
                    .subtract(this.getBeamOrigin());
            if (toVictim.lengthSqr() > RANGE * RANGE) {
                continue;
            }
            if (forward.dotProduct(toVictim.normalize()) < 0.55D) {
                continue;
            }
            victim.setSecondsOnFire(6);
            victim.hurt(this.damageSources().mobAttack(this), 2.5F);
            hit = true;
        }
        this.setAbilityCooldown(hit ? FIRE_INTERVAL : 10);
        if (hit) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.FIRECHARGE_USE, SoundSource.HOSTILE, 2.0F, 0.7F);
        }
        if (this.level() instanceof ServerLevel serverWorld) {
            Vec3 origin = this.getBeamOrigin();
            for (int step = 1; step <= 5; step++) {
                Vec3 point = origin.add(forward.scale(step * 1.3D));
                serverWorld.sendParticles(ParticleTypes.FLAME, point.x, point.y, point.z,
                        6, 0.25D, 0.25D, 0.25D, 0.02D);
            }
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) == 0) {
            Vec3 origin = this.getBeamOrigin();
            this.level().addParticle(ParticleTypes.SMOKE, origin.x, origin.y, origin.z, 0.0D, 0.02D, 0.0D);
        }
    }

    @Override
    public void playChargeSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.FIRECHARGE_USE, SoundSource.HOSTILE, 1.8F, 0.6F);
    }
}
