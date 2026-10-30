package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

/**
 * BBC "The Tripods" abduction field. The white tripod does not shoot: it drags everything around
 * it into the air and holds it there, exactly like the Capped are collected in the series.
 */
public class CaptureFieldGoal extends Goal {
    private final AbstractTripodEntity tripod;
    private final double radius;
    private final int duration;
    private final int cooldown;

    private int timer;

    public CaptureFieldGoal(AbstractTripodEntity tripod, double radius, int duration, int cooldown) {
        this.tripod = tripod;
        this.radius = radius;
        this.duration = duration;
        this.cooldown = cooldown;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (!this.tripod.isAbilityReady()) {
            return false;
        }
        LivingEntity target = this.tripod.getTarget();
        return target != null && target.isAlive()
                && this.tripod.squaredDistanceTo(target) <= this.radius * this.radius * 1.44D;
    }

    @Override
    public boolean shouldContinue() {
        return this.timer < this.duration;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.timer = 0;
        this.tripod.setAbilityCharging(true);
        this.tripod.getNavigation().stop();
        this.tripod.playChargeSound();
    }

    @Override
    public void tick() {
        this.timer++;
        LivingEntity target = this.tripod.getTarget();
        if (target != null) {
            this.tripod.getLookControl().lookAt(target, 30.0F, 30.0F);
        }

        if (this.timer % 5 != 0) {
            return;
        }

        Vec3d base = this.tripod.getPos();
        Vec3d center = base.add(0.0D, this.tripod.getHeight() * 0.5D, 0.0D);
        Box area = new Box(base.x - this.radius, base.y - 1.0D, base.z - this.radius,
                base.x + this.radius, base.y + this.tripod.getHeight() + 2.0D, base.z + this.radius);
        for (Entity entity : this.tripod.getWorld().getOtherEntities(this.tripod, area,
                e -> e instanceof LivingEntity && e.isAlive())) {
            LivingEntity living = (LivingEntity) entity;
            if (living instanceof AbstractTripodEntity) {
                continue;
            }
            Vec3d pull = center.subtract(living.getPos());
            double length = pull.length();
            if (length < 0.5D) {
                continue;
            }
            Vec3d direction = pull.multiply(1.0D / length);
            living.addVelocity(direction.x * 0.16D, 0.13D, direction.z * 0.16D);
            living.velocityModified = true;
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 40, 0, false, false, false));
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1, false, true, true));
        }

        if (this.tripod.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.END_ROD,
                    this.tripod.getX(), this.tripod.getY() + 1.0D, this.tripod.getZ(),
                    20, this.radius * 0.35D, 0.6D, this.radius * 0.35D, 0.06D);
            serverWorld.spawnParticles(ParticleTypes.ELECTRIC_SPARK,
                    this.tripod.getX(), this.tripod.getY() + this.tripod.getHeight() * 0.5D, this.tripod.getZ(),
                    14, this.radius * 0.3D, 1.0D, this.radius * 0.3D, 0.12D);
        }
        this.tripod.getWorld().playSound(null, this.tripod.getX(), this.tripod.getY(), this.tripod.getZ(),
                SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.HOSTILE, 1.6F, 0.6F);
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldown);
    }
}
