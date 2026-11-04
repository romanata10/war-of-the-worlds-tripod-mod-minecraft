package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.tripod.isAbilityReady()) {
            return false;
        }
        LivingEntity target = this.tripod.getTarget();
        return target != null && target.isAlive()
                && this.tripod.distanceToSqr(target) <= this.radius * this.radius;
    }

    @Override
    public boolean canContinueToUse() {
        return this.timer < 24;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
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
        Vec3 center = this.tripod.position();
        AABB area = new AABB(center.x - this.radius, center.y - 2.0D, center.z - this.radius,
                center.x + this.radius, center.y + 3.0D, center.z + this.radius);
        for (Entity entity : this.tripod.level().getOtherEntities(this.tripod, area, e -> e instanceof LivingEntity)) {
            LivingEntity living = (LivingEntity) entity;
            if (living instanceof AbstractTripodEntity || !living.isAlive()) {
                continue;
            }
            DamageSource source = this.tripod.level().damageSources().mobAttack(this.tripod);
            living.hurt(source, this.damage);
            Vec3 push = living.position().subtract(center);
            double length = Math.max(0.5D, push.length());
            living.setDeltaMovement(push.x / length * 0.6D, 0.55D, push.z / length * 0.6D);
            living.hurtMarked = true;
        }

        if (this.tripod.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.EXPLOSION_EMITTER, center.x, center.y + 0.2D, center.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            serverWorld.sendParticles(ParticleTypes.LARGE_SMOKE, center.x, center.y + 0.4D, center.z, 40, 2.0D, 0.4D, 2.0D, 0.04D);
        }
        this.tripod.level().playSound(null, center.x, center.y, center.z,
                SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 2.2F, 0.65F);
        this.tripod.level().playSound(null, center.x, center.y, center.z,
                SoundEvents.ANVIL_LAND, SoundSource.HOSTILE, 2.6F, 0.5F);
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldown);
    }
}
