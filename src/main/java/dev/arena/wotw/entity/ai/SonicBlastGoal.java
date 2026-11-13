package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * The sonic cannon of the 1950s machine: it winds up, then hits everything in a cone in front of it
 * with a pressure wave — heavy damage that falls off with distance, a hard shove and a ringing head.
 */
public class SonicBlastGoal extends Goal {

    private final AbstractTripodEntity tripod;
    private final double range;
    private final float damage;
    private final int cooldown;

    private int timer;

    public SonicBlastGoal(AbstractTripodEntity tripod, double range, float damage, int cooldown) {
        this.tripod = tripod;
        this.range = range;
        this.damage = damage;
        this.cooldown = cooldown;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.tripod.isAbilityReady()) {
            return false;
        }
        LivingEntity target = this.tripod.getTarget();
        return target != null && target.isAlive() && this.tripod.distanceToSqr(target) <= this.range * this.range;
    }

    @Override
    public boolean canContinueToUse() {
        return this.timer < 46;
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
            this.tripod.getLookControl().setLookAt(target, 40.0F, 40.0F);
        }
        if (this.timer == 34) {
            this.blast();
        }
    }

    private void blast() {
        Vec3 origin = new Vec3(this.tripod.getX(), this.tripod.getY() + this.tripod.getBbHeight() * 0.7D,
                this.tripod.getZ());
        Vec3 facing = this.tripod.getViewVector(1.0F).normalize();
        for (LivingEntity victim : this.tripod.level().getEntitiesOfClass(LivingEntity.class,
                this.tripod.getBoundingBox().inflate(this.range))) {
            if (victim == this.tripod || victim instanceof AbstractTripodEntity) {
                continue;
            }
            Vec3 delta = victim.position().subtract(origin);
            double distance = delta.length();
            if (distance > this.range || distance < 0.1D) {
                continue;
            }
            Vec3 direction = delta.normalize();
            if (direction.dot(facing) < 0.45D) {
                continue;                       // only what stands in the cone
            }
            float falloff = (float) Math.max(0.35D, 1.0D - distance / this.range);
            victim.hurt(this.tripod.damageSources().mobAttack(this.tripod), this.damage * falloff);
            victim.push(direction.x * 1.6D * falloff, 0.55D * falloff, direction.z * 1.6D * falloff);
            victim.hurtMarked = true;
            victim.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 140, 1));
            victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
        }
        if (this.tripod.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SONIC_BOOM, origin.x + facing.x * 2.0D, origin.y,
                    origin.z + facing.z * 2.0D, 3, 0.4D, 0.2D, 0.4D, 0.0D);
            server.playSound(null, origin.x, origin.y, origin.z, SoundEvents.WARDEN_SONIC_BOOM,
                    SoundSource.HOSTILE, 3.0F, 0.7F);
        }
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldown);
    }
}
