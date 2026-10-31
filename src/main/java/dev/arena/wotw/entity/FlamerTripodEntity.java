package dev.arena.wotw.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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

    public FlamerTripodEntity(EntityType<? extends FlamerTripodEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
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
        if (this.squaredDistanceTo(target) > (RANGE + 4.0D) * (RANGE + 4.0D)) {
            return;
        }
        Vec3d forward = this.getRotationVec(1.0F);
        Box box = this.getBoundingBox().expand(RANGE);
        boolean hit = false;
        for (LivingEntity victim : this.getWorld().getEntitiesByClass(LivingEntity.class, box,
                entity -> entity.isAlive() && entity != this && !(entity instanceof AbstractTripodEntity))) {
            Vec3d toVictim = victim.getPos().add(0.0D, victim.getHeight() * 0.5D, 0.0D)
                    .subtract(this.getBeamOrigin());
            if (toVictim.lengthSquared() > RANGE * RANGE) {
                continue;
            }
            if (forward.dotProduct(toVictim.normalize()) < 0.55D) {
                continue;
            }
            victim.setOnFireFor(6);
            victim.damage(this.getDamageSources().mobAttack(this), 2.5F);
            hit = true;
        }
        this.setAbilityCooldown(hit ? FIRE_INTERVAL : 10);
        if (hit) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.HOSTILE, 2.0F, 0.7F);
        }
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d origin = this.getBeamOrigin();
            for (int step = 1; step <= 5; step++) {
                Vec3d point = origin.add(forward.multiply(step * 1.3D));
                serverWorld.spawnParticles(ParticleTypes.FLAME, point.x, point.y, point.z,
                        6, 0.25D, 0.25D, 0.25D, 0.02D);
            }
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) == 0) {
            Vec3d origin = this.getBeamOrigin();
            this.getWorld().addParticle(ParticleTypes.SMOKE, origin.x, origin.y, origin.z, 0.0D, 0.02D, 0.0D);
        }
    }

    @Override
    public void playChargeSound() {
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.HOSTILE, 1.8F, 0.6F);
    }
}
