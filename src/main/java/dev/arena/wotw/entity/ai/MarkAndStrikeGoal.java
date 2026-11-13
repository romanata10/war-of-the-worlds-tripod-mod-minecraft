package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

/**
 * The hunting pattern of the 2019 machine: it paints the target with a searchlight, holds the mark
 * for three seconds while the lance charges, and then puts a single, very accurate beam through it.
 */
public class MarkAndStrikeGoal extends Goal {

    private final AbstractTripodEntity tripod;
    private final BeamKind kind;
    private final float damage;
    private final double range;
    private final int markTicks;
    private final int cooldown;

    private int timer;

    public MarkAndStrikeGoal(AbstractTripodEntity tripod, BeamKind kind, float damage, double range,
                             int markTicks, int cooldown) {
        this.tripod = tripod;
        this.kind = kind;
        this.damage = damage;
        this.range = range;
        this.markTicks = markTicks;
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
        return this.tripod.canSeeTarget(target) && this.tripod.distanceToSqr(target) <= this.range * this.range;
    }

    @Override
    public boolean canContinueToUse() {
        return this.timer < this.markTicks + 20;
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
        this.tripod.getLookControl().setLookAt(target, 60.0F, 60.0F);
        if (this.timer == 6) {
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, this.markTicks + 20, 0));
            if (this.tripod.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.END_ROD, target.getX(), target.getY() + 1.2D, target.getZ(),
                        24, 0.35D, 0.8D, 0.35D, 0.02D);
                server.playSound(null, target.getX(), target.getY(), target.getZ(),
                        SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 1.4F, 1.6F);
            }
        }
        if (this.timer == this.markTicks) {
            this.tripod.fireBeamAt(target, this.kind, this.damage);
        }
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldown);
    }
}
