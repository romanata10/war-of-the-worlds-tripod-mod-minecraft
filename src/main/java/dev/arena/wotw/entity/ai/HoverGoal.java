package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

/**
 * Flying movement for the 1953 manta machine: it circles above its prey at a fixed altitude
 * and slowly spirals in. No navigation is used, the tripod is driven purely by velocity.
 */
public class HoverGoal extends Goal {
    private final AbstractTripodEntity tripod;
    private final double hoverHeight;
    private final double orbitRadius;
    private final double maxSpeed;

    private double angle;

    public HoverGoal(AbstractTripodEntity tripod, double hoverHeight, double orbitRadius, double maxSpeed) {
        this.tripod = tripod;
        this.hoverHeight = hoverHeight;
        this.orbitRadius = orbitRadius;
        this.maxSpeed = maxSpeed;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        LivingEntity target = this.tripod.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity target = this.tripod.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.angle = this.tripod.getRandom().nextDouble() * Math.PI * 2.0D;
    }

    @Override
    public void tick() {
        LivingEntity target = this.tripod.getTarget();
        if (target == null) {
            return;
        }

        this.angle += 0.02D;
        double closeness = Math.min(1.0D, this.tripod.distanceTo(target) / 40.0D);
        double radius = this.orbitRadius * (0.35D + closeness);

        Vec3d orbit = new Vec3d(Math.cos(this.angle) * radius, 0.0D, Math.sin(this.angle) * radius);
        double desiredY = Math.max(this.tripod.getY(), target.getY() + this.hoverHeight);
        Vec3d desired = target.getPos().add(orbit).add(0.0D, this.hoverHeight * 0.5D + (desiredY - target.getY()) * 0.5D, 0.0D);

        Vec3d delta = desired.subtract(this.tripod.getPos());
        double distance = delta.length();
        Vec3d velocity = this.tripod.getVelocity().multiply(0.86D);
        if (distance > 1.0D) {
            velocity = velocity.add(delta.multiply(1.0D / distance).multiply(0.18D));
        }
        if (velocity.length() > this.maxSpeed) {
            velocity = velocity.normalize().multiply(this.maxSpeed);
        }
        this.tripod.setVelocity(velocity);
        this.tripod.velocityModified = true;
        this.tripod.getLookControl().lookAt(target, 25.0F, 25.0F);
    }
}
