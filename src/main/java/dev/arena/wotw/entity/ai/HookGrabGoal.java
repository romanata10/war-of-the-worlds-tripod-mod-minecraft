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
 * The crane hook of the 1906 machine: the hook is swung out, the target is caught and hauled up into
 * the air, and what comes down comes down hard.
 */
public class HookGrabGoal extends Goal {

    private final AbstractTripodEntity tripod;
    private final double range;
    private final float damage;
    private final int cooldown;

    private int timer;

    public HookGrabGoal(AbstractTripodEntity tripod, double range, float damage, int cooldown) {
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
        if (target == null || !target.isAlive()) {
            return false;
        }
        double squared = this.tripod.distanceToSqr(target);
        return squared <= this.range * this.range && squared > 9.0D;
    }

    @Override
    public boolean canContinueToUse() {
        return this.timer < 40;
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
        if (target == null) {
            return;
        }
        this.tripod.getLookControl().setLookAt(target, 45.0F, 45.0F);
        if (this.timer == 30) {
            this.hook(target);
        }
    }

    private void hook(LivingEntity target) {
        if (this.tripod.distanceToSqr(target) > this.range * this.range) {
            return;
        }
        target.hurt(this.tripod.damageSources().mobAttack(this.tripod), this.damage);
        // hauled off the ground and then dropped: the fall does the rest
        target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 45, 2));
        Vec3 pull = new Vec3(this.tripod.getX() - target.getX(), 0.0D, this.tripod.getZ() - target.getZ());
        if (pull.lengthSqr() > 0.01D) {
            pull = pull.normalize().scale(0.45D);
            target.setDeltaMovement(target.getDeltaMovement().add(pull.x, 0.4D, pull.z));
            target.hurtMarked = true;
        }
        if (this.tripod.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CRIT, target.getX(), target.getY() + 1.0D, target.getZ(),
                    18, 0.4D, 0.6D, 0.4D, 0.05D);
            server.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.CHAIN_BREAK,
                    SoundSource.HOSTILE, 2.0F, 0.6F);
        }
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldown);
    }
}
