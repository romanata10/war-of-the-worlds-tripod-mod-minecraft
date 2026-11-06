package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import dev.arena.wotw.entity.projectile.ShellEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * The mortar of the bombing machine: it charges up, lobs a shell of black smoke on a high arc at
 * where the target is going to be, and settles back into its walk.
 *
 * <p>Unlike a heat ray it can be used over a hill: the shell does the aiming, the machine only has
 * to know roughly where the enemy stands.</p>
 */
public class SmokeBombGoal extends Goal {

    private final AbstractTripodEntity tripod;
    private final double range;
    private final int cooldown;

    private int timer;

    public SmokeBombGoal(AbstractTripodEntity tripod, double range, int cooldown) {
        this.tripod = tripod;
        this.range = range;
        this.cooldown = cooldown;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.tripod.isAbilityReady()) {
            return false;
        }
        LivingEntity target = this.tripod.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        double squared = this.tripod.distanceToSqr(target);
        return squared <= this.range * this.range && squared > 36.0D;
    }

    @Override
    public boolean canContinueToUse() {
        return this.timer < 34;
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
        this.tripod.playChargeSound();
    }

    @Override
    public void tick() {
        this.timer++;
        LivingEntity target = this.tripod.getTarget();
        if (target != null) {
            this.tripod.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
        if (this.timer == 20) {
            this.launch(target);
        }
    }

    private void launch(LivingEntity target) {
        Vec3 origin = this.tripod.getBeamOrigin().add(0.0D, 1.2D, 0.0D);
        Vec3 aim;
        if (target != null) {
            // lead the target: the shell is slow, so aim where it will be in a second
            aim = target.position().add(target.getDeltaMovement().scale(18.0D));
        } else {
            aim = origin.add(this.tripod.getViewVector(1.0F).scale(12.0D));
        }
        Vec3 delta = aim.subtract(origin);
        double distance = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
        Vec3 velocity = new Vec3(delta.x / distance * 0.9D, 0.55D + distance * 0.012D, delta.z / distance * 0.9D);
        ShellEntity.launchSmokeBomb(this.tripod.level(), this.tripod, origin, velocity);
        this.tripod.level().playSound(null, this.tripod.getX(), this.tripod.getY(), this.tripod.getZ(),
                SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.HOSTILE, 2.0F, 0.5F);
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldown);
    }
}
