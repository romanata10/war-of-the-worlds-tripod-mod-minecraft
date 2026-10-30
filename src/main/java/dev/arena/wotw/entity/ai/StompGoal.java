package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

/**
 * Asylum walker ground slam: the six-legged machine lifts a leg and hammers the ground,
 * throwing everything nearby into the air.
 */
public class StompGoal extends Goal {
    private final AbstractTripodEntity tripod;
    private final float damage;
    private final double radius;
    private final int cooldown;

    private int timer;
    private boolean slammed;

    public StompGoal(AbstractTripodEntity tripod, float damage, double radius, int cooldown) {
        this.tripod = tripod;
        this.damage = damage;
        this.radius = radius;
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
                && this.tripod.squaredDistanceTo(target) <= this.radius * this.radius;
    }

    @Override
    public boolean shouldContinue() {
        return this.timer < 24;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.timer = 0;
        this.slammed = false;
        this.tripod.setAbilityCharging(true);
        this.tripod.getNavigation().stop();
        this.tripod.playChargeSound();
    }

    @Override
    public void tick() {
        this.timer++;
        if (this.timer == 14 && !this.slammed) {
            this.slam();
            this.slammed = true;
        }
    }

    private void slam() {
        Vec3d center = this.tripod.getPos();
        Box area = new Box(center.x - this.radius, center.y - 2.0D, center.z - this.radius,
                center.x + this.radius, center.y + 3.0D, center.z + this.radius);
        for (Entity entity : this.tripod.getWorld().getOtherEntities(this.tripod, area, e -> e instanceof LivingEntity)) {
            LivingEntity living = (LivingEntity) entity;
            if (living instanceof AbstractTripodEntity || !living.isAlive()) {
                continue;
            }
            DamageSource source = this.tripod.getWorld().getDamageSources().mobAttack(this.tripod);
            living.damage(source, this.damage);
            Vec3d push = living.getPos().subtract(center);
            double length = Math.max(0.5D, push.length());
            living.setVelocity(push.x / length * 0.6D, 0.55D, push.z / length * 0.6D);
            living.velocityModified = true;
        }

        if (this.tripod.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, center.x, center.y + 0.2D, center.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            serverWorld.spawnParticles(ParticleTypes.LARGE_SMOKE, center.x, center.y + 0.4D, center.z, 40, 2.0D, 0.4D, 2.0D, 0.04D);
        }
        this.tripod.getWorld().playSound(null, center.x, center.y, center.z,
                SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 2.2F, 0.65F);
        this.tripod.getWorld().playSound(null, center.x, center.y, center.z,
                SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.HOSTILE, 2.6F, 0.5F);
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldown);
    }
}
