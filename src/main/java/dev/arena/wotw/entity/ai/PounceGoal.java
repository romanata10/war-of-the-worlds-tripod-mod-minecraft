package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;

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
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.tripod.isAbilityReady() || !this.tripod.onGround()) {
            return false;
        }
        LivingEntity target = this.tripod.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        double squared = this.tripod.distanceToSqr(target);
        return squared <= this.range * this.range && squared > 4.0D;
    }

    @Override
    public boolean canContinueToUse() {
        return this.timer < 16;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.timer = 0;
        this.tripod.setAbilityCharging(true);
        this.tripod.getNavigation().stop();
        this.tripod.level().playSound(null, this.tripod.getX(), this.tripod.getY(), this.tripod.getZ(),
                SoundEvents.SPIDER_AMBIENT, SoundSource.HOSTILE, 2.0F, 1.4F);
    }

    @Override
    public void tick() {
        this.timer++;
        LivingEntity target = this.tripod.getTarget();
        if (target != null) {
            this.tripod.getLookControl().setLookAt(target, 40.0F, 40.0F);
        }
        if (this.timer == 8 && target != null) {
            Vec3 delta = target.position().subtract(this.tripod.position());
            Vec3 flat = new Vec3(delta.x, 0.0D, delta.z);
            if (flat.lengthSqr() > 0.01D) {
                flat = flat.normalize().scale(1.35D);
            }
            this.tripod.setDeltaMovement(flat.x, 0.55D, flat.z);
            this.tripod.hurtMarked = true;
            this.tripod.level().playSound(null, this.tripod.getX(), this.tripod.getY(), this.tripod.getZ(),
                    SoundEvents.SPIDER_STEP, SoundSource.HOSTILE, 2.0F, 1.2F);
        }
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldown);
    }
}
