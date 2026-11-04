package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * H.G. Wells' black smoke. The 1898 machine launches a canister that bursts into the famous
 * "black smoke" — a lingering cloud that poisons, blinds and withers everything inside it.
 */
public class BlackSmokeGoal extends Goal {
    private final AbstractTripodEntity tripod;
    private final double range;
    private final int cooldown;

    private int timer;

    public BlackSmokeGoal(AbstractTripodEntity tripod, double range, int cooldown) {
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
        return squared <= this.range * this.range && squared > 16.0D;
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
        if (target != null) {
            this.tripod.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
        if (this.timer == 22) {
            this.launch(target);
        }
    }

    private void launch(LivingEntity target) {
        Vec3 impact = target != null ? target.position() : this.tripod.position().add(this.tripod.getViewVector(1.0F).scale(8.0D));
        AreaEffectCloud cloud = new AreaEffectCloud(this.tripod.level(), impact.x, impact.y, impact.z);
        cloud.setOwner(this.tripod);
        cloud.setParticle(ParticleTypes.LARGE_SMOKE);
        cloud.setRadius(4.5F);
        cloud.setRadiusPerTick(0.02F);
        cloud.setRadiusOnUse(-0.05F);
        cloud.setDuration(260);
        cloud.setWaitTime(0);
        cloud.addEffect(new MobEffectInstance(MobEffects.POISON, 220, 1));
        cloud.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 220, 0));
        cloud.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 120, 0));
        cloud.addEffect(new MobEffectInstance(MobEffects.WITHER, 120, 0));
        this.tripod.level().addFreshEntity(cloud);

        this.tripod.level().playSound(null, impact.x, impact.y, impact.z,
                SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 1.8F, 0.5F);
        this.tripod.level().playSound(null, impact.x, impact.y, impact.z,
                SoundEvents.FIRE_EXTINGUISH, SoundSource.HOSTILE, 2.0F, 0.4F);
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldown);
    }
}
