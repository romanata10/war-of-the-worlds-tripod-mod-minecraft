package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.projectile.ShellEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.Mob;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * "Aim, bang, wait": the human weapon goal. With {@code shellPower > 0} it lobs a shell like a
 * tank or a ship, otherwise it fires a flat bullet that only hurts what it hits.
 */
public class GunAttackGoal extends Goal {

    private final Mob shooter;
    private final double range;
    private final int cooldownTicks;
    private final float shellPower;
    private final float bulletDamage;

    private int lastShot = -10000;

    public GunAttackGoal(Mob shooter, double range, int cooldownTicks, float shellPower, float bulletDamage) {
        this.shooter = shooter;
        this.range = range;
        this.cooldownTicks = cooldownTicks;
        this.shellPower = shellPower;
        this.bulletDamage = bulletDamage;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.shooter.getTarget();
        if (target == null || !target.isAlive() || !this.shooter.canAttack(target)) {
            return false;
        }
        if (this.shooter.tickCount - this.lastShot < this.cooldownTicks) {
            return false;
        }
        if (this.shooter.distanceToSqr(target) > this.range * this.range) {
            return false;
        }
        return this.shooter.hasLineOfSight(target);
    }

    @Override
    public void start() {
        LivingEntity target = this.shooter.getTarget();
        if (target == null) {
            return;
        }
        this.shooter.getNavigation().stop();
        this.shooter.getLookControl().setLookAt(target, 40.0F, 40.0F);
        this.fire(target);
        this.lastShot = this.shooter.tickCount;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    private void fire(LivingEntity target) {
        Vec3 origin = this.shooter.getEyePosition();
        Vec3 delta = target.getEyePosition().subtract(origin);
        double distance = delta.length();
        if (distance < 0.1D) {
            return;
        }
        if (this.shellPower > 0.0F) {
            Vec3 velocity = delta.normalize().scale(0.9D + distance * 0.02D);
            ShellEntity.launch(this.shooter.level(), this.shooter, origin,
                    new Vec3(velocity.x, velocity.y + 0.05D * distance, velocity.z), this.shellPower, false);
            this.shooter.level().playSound(null, this.shooter.getX(), this.shooter.getY(), this.shooter.getZ(),
                    SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 0.8F, 0.6F);
        } else {
            ShellEntity.launchBullet(this.shooter.level(), this.shooter, origin,
                    delta.normalize().scale(3.2D), this.bulletDamage);
            this.shooter.level().playSound(null, this.shooter.getX(), this.shooter.getY(), this.shooter.getZ(),
                    SoundEvents.ARROW_SHOOT, SoundSource.NEUTRAL, 1.0F, 1.8F);
        }
    }
}
