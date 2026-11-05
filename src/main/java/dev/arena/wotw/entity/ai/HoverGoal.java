package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

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
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.tripod.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.tripod.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
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

        Vec3 orbit = new Vec3(Math.cos(this.angle) * radius, 0.0D, Math.sin(this.angle) * radius);
        double desiredY = Math.max(this.tripod.getY(), target.getY() + this.hoverHeight);
        Vec3 desired = target.position().add(orbit).add(0.0D, this.hoverHeight * 0.5D + (desiredY - target.getY()) * 0.5D, 0.0D);

        Vec3 delta = desired.subtract(this.tripod.position());
        double distance = delta.length();
        Vec3 velocity = this.tripod.getDeltaMovement().scale(0.86D);
        if (distance > 1.0D) {
            velocity = velocity.add(delta.scale(1.0D / distance).scale(0.18D));
        }
        if (velocity.length() > this.maxSpeed) {
            velocity = velocity.normalize().scale(this.maxSpeed);
        }
        this.tripod.setDeltaMovement(velocity);
        this.tripod.hurtMarked = true;
        this.tripod.getLookControl().setLookAt(target, 25.0F, 25.0F);
    }
}
