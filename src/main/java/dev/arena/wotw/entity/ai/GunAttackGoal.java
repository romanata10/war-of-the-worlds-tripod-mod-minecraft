package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.projectile.ShellEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

/**
 * "Aim, bang, wait": the human weapon goal. With {@code shellPower > 0} it lobs a shell like a
 * tank or a ship, otherwise it fires a flat bullet that only hurts what it hits.
 */
public class GunAttackGoal extends Goal {

    private final MobEntity shooter;
    private final double range;
    private final int cooldownTicks;
    private final float shellPower;
    private final float bulletDamage;

    private int lastShot = -10000;

    public GunAttackGoal(MobEntity shooter, double range, int cooldownTicks, float shellPower, float bulletDamage) {
        this.shooter = shooter;
        this.range = range;
        this.cooldownTicks = cooldownTicks;
        this.shellPower = shellPower;
        this.bulletDamage = bulletDamage;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        LivingEntity target = this.shooter.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (this.shooter.age - this.lastShot < this.cooldownTicks) {
            return false;
        }
        if (this.shooter.squaredDistanceTo(target) > this.range * this.range) {
            return false;
        }
        return this.shooter.canSee(target);
    }

    @Override
    public void start() {
        LivingEntity target = this.shooter.getTarget();
        if (target == null) {
            return;
        }
        this.shooter.getNavigation().stop();
        this.shooter.getLookControl().lookAt(target, 40.0F, 40.0F);
        this.fire(target);
        this.lastShot = this.shooter.age;
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }

    private void fire(LivingEntity target) {
        Vec3d origin = this.shooter.getEyePos();
        Vec3d delta = target.getEyePos().subtract(origin);
        double distance = delta.length();
        if (distance < 0.1D) {
            return;
        }
        if (this.shellPower > 0.0F) {
            Vec3d velocity = delta.normalize().multiply(0.9D + distance * 0.02D);
            ShellEntity.launch(this.shooter.getWorld(), this.shooter, origin,
                    new Vec3d(velocity.x, velocity.y + 0.05D * distance, velocity.z), this.shellPower, false);
            this.shooter.getWorld().playSound(null, this.shooter.getX(), this.shooter.getY(), this.shooter.getZ(),
                    SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.8F, 0.6F);
        } else {
            ShellEntity.launchBullet(this.shooter.getWorld(), this.shooter, origin,
                    delta.normalize().multiply(3.2D), this.bulletDamage);
            this.shooter.getWorld().playSound(null, this.shooter.getX(), this.shooter.getY(), this.shooter.getZ(),
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.8F);
        }
    }
}
