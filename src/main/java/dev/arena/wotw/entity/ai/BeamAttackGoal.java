package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

/**
 * "Aim, charge, fire" behaviour shared by every tripod that owns a heat ray or a laser.
 *
 * <p>Supports bursts: {@code burstShots = 3} with an interval of 6 ticks turns the red BBC
 * tripod into a laser machine gun, while {@code burstShots = 1} is a single clean shot.</p>
 */
public class BeamAttackGoal extends Goal {
    private final AbstractTripodEntity tripod;
    private final BeamKind kind;
    private final float damage;
    private final double range;
    private final int chargeTicks;
    private final int cooldownTicks;
    private final int burstShots;
    private final int burstInterval;
    private final boolean needsSight;

    private int timer;
    private int shotsLeft;
    private int nextShot;
    private boolean finished;

    public BeamAttackGoal(AbstractTripodEntity tripod, BeamKind kind, float damage, double range,
                          int chargeTicks, int cooldownTicks, int burstShots, int burstInterval,
                          boolean needsSight) {
        this.tripod = tripod;
        this.kind = kind;
        this.damage = damage;
        this.range = range;
        this.chargeTicks = chargeTicks;
        this.cooldownTicks = cooldownTicks;
        this.burstShots = Math.max(1, burstShots);
        this.burstInterval = Math.max(1, burstInterval);
        this.needsSight = needsSight;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    /** Convenience constructor for a single shot. */
    public BeamAttackGoal(AbstractTripodEntity tripod, BeamKind kind, float damage, double range,
                          int chargeTicks, int cooldownTicks) {
        this(tripod, kind, damage, range, chargeTicks, cooldownTicks, 1, 1, true);
    }

    @Override
    public boolean canStart() {
        if (!this.tripod.isAbilityReady() || this.tripod.isAbilityCharging()) {
            return false;
        }
        LivingEntity target = this.tripod.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        double squared = this.tripod.squaredDistanceTo(target);
        if (squared > this.range * this.range || squared < 9.0D) {
            return false;
        }
        return !this.needsSight || this.tripod.canSeeTarget(target);
    }

    @Override
    public boolean shouldContinue() {
        if (this.finished) {
            return false;
        }
        LivingEntity target = this.tripod.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        return this.timer < 600 && this.tripod.squaredDistanceTo(target) <= this.range * this.range * 1.6D;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.timer = 0;
        this.shotsLeft = this.burstShots;
        this.nextShot = this.chargeTicks + this.burstInterval;
        this.finished = false;
        this.tripod.setAbilityCharging(true);
        this.tripod.getNavigation().stop();
        this.tripod.playChargeSound();
    }

    @Override
    public void tick() {
        this.timer++;
        LivingEntity target = this.tripod.getTarget();
        if (target != null) {
            this.tripod.getLookControl().lookAt(target, 40.0F, 40.0F);
        }

        if (this.timer == this.chargeTicks) {
            this.shoot(target);
        } else if (this.timer > this.chargeTicks && this.shotsLeft > 0 && this.timer >= this.nextShot) {
            this.shoot(target);
            this.nextShot += this.burstInterval;
        }
    }

    private void shoot(LivingEntity target) {
        if (target != null && target.isAlive()) {
            this.tripod.fireBeamAt(target, this.kind, this.damage);
        }
        this.shotsLeft--;
        if (this.shotsLeft <= 0) {
            this.finished = true;
        }
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldownTicks);
    }
}
