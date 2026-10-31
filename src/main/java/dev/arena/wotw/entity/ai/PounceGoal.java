package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

/**
 * Martian spider leap. The machine crouches for a moment and then throws itself at the target.
 */
public class PounceGoal extends Goal {
    private final AbstractTripodEntity tripod;
    private final double range;
    private final int cooldown;

    private int timer;

    public PounceGoal(AbstractTripodEntity tripod, double range, int cooldown) {
        this.tripod = tripod;
        this.range = range;
        this.cooldown = cooldown;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.JUMP, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (!this.tripod.isAbilityReady() || !this.tripod.isOnGround()) {
            return false;
        }
        LivingEntity target = this.tripod.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        double squared = this.tripod.squaredDistanceTo(target);
        return squared <= this.range * this.range && squared > 4.0D;
    }

    @Override
    public boolean shouldContinue() {
        return this.timer < 16;
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
        this.tripod.getWorld().playSound(null, this.tripod.getX(), this.tripod.getY(), this.tripod.getZ(),
                SoundEvents.ENTITY_SPIDER_AMBIENT, SoundCategory.HOSTILE, 2.0F, 1.4F);
    }

    @Override
    public void tick() {
        this.timer++;
        LivingEntity target = this.tripod.getTarget();
        if (target != null) {
            this.tripod.getLookControl().lookAt(target, 40.0F, 40.0F);
        }
        if (this.timer == 8 && target != null) {
            Vec3d delta = target.getPos().subtract(this.tripod.getPos());
            Vec3d flat = new Vec3d(delta.x, 0.0D, delta.z);
            if (flat.lengthSquared() > 0.01D) {
                flat = flat.normalize().multiply(1.35D);
            }
            this.tripod.setVelocity(flat.x, 0.55D, flat.z);
            this.tripod.velocityModified = true;
            this.tripod.getWorld().playSound(null, this.tripod.getX(), this.tripod.getY(), this.tripod.getZ(),
                    SoundEvents.ENTITY_SPIDER_STEP, SoundCategory.HOSTILE, 2.0F, 1.2F);
        }
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldown);
    }
}
