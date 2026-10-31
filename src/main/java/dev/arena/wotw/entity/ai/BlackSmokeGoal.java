package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

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
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (!this.tripod.isAbilityReady()) {
            return false;
        }
        LivingEntity target = this.tripod.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        double squared = this.tripod.squaredDistanceTo(target);
        return squared <= this.range * this.range && squared > 16.0D;
    }

    @Override
    public boolean shouldContinue() {
        return this.timer < 40;
    }

    @Override
    public boolean shouldRunEveryTick() {
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
            this.tripod.getLookControl().lookAt(target, 30.0F, 30.0F);
        }
        if (this.timer == 22) {
            this.launch(target);
        }
    }

    private void launch(LivingEntity target) {
        Vec3d impact = target != null ? target.getPos() : this.tripod.getPos().add(this.tripod.getRotationVec(1.0F).multiply(8.0D));
        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(this.tripod.getWorld(), impact.x, impact.y, impact.z);
        cloud.setOwner(this.tripod);
        cloud.setParticleType(ParticleTypes.LARGE_SMOKE);
        cloud.setRadius(4.5F);
        cloud.setRadiusGrowth(0.02F);
        cloud.setRadiusOnUse(-0.05F);
        cloud.setDuration(260);
        cloud.setWaitTime(0);
        cloud.setColor(0x121212);
        cloud.addEffect(new StatusEffectInstance(StatusEffects.POISON, 220, 1));
        cloud.addEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 220, 0));
        cloud.addEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 120, 0));
        cloud.addEffect(new StatusEffectInstance(StatusEffects.WITHER, 120, 0));
        this.tripod.getWorld().spawnEntity(cloud);

        this.tripod.getWorld().playSound(null, impact.x, impact.y, impact.z,
                SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.8F, 0.5F);
        this.tripod.getWorld().playSound(null, impact.x, impact.y, impact.z,
                SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.HOSTILE, 2.0F, 0.4F);
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldown);
    }
}
