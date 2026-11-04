package dev.arena.wotw.entity.ai;

import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * BBC "The Tripods" abduction field. The white tripod does not shoot: it drags everything around
 * it into the air and holds it there, exactly like the Capped are collected in the series.
 */
public class CaptureFieldGoal extends Goal {
    private final AbstractTripodEntity tripod;
    private final double radius;
    private final int duration;
    private final int cooldown;

    private int timer;

    public CaptureFieldGoal(AbstractTripodEntity tripod, double radius, int duration, int cooldown) {
        this.tripod = tripod;
        this.radius = radius;
        this.duration = duration;
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
                && this.tripod.distanceToSqr(target) <= this.radius * this.radius * 1.44D;
    }

    @Override
    public boolean canContinueToUse() {
        return this.timer < this.duration;
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

        if (this.timer % 5 != 0) {
            return;
        }

        Vec3 base = this.tripod.position();
        Vec3 center = base.add(0.0D, this.tripod.getBbHeight() * 0.5D, 0.0D);
        AABB area = new AABB(base.x - this.radius, base.y - 1.0D, base.z - this.radius,
                base.x + this.radius, base.y + this.tripod.getBbHeight() + 2.0D, base.z + this.radius);
        for (Entity entity : this.tripod.level().getEntities(this.tripod, area,
                e -> e instanceof LivingEntity && e.isAlive())) {
            LivingEntity living = (LivingEntity) entity;
            if (living instanceof AbstractTripodEntity) {
                continue;
            }
            Vec3 pull = center.subtract(living.position());
            double length = pull.length();
            if (length < 0.5D) {
                continue;
            }
            Vec3 direction = pull.scale(1.0D / length);
            living.push(direction.x * 0.16D, 0.13D, direction.z * 0.16D);
            living.hurtMarked = true;
            living.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 40, 0, false, false, false));
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, true, true));
        }

        if (this.tripod.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.END_ROD,
                    this.tripod.getX(), this.tripod.getY() + 1.0D, this.tripod.getZ(),
                    20, this.radius * 0.35D, 0.6D, this.radius * 0.35D, 0.06D);
            serverWorld.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    this.tripod.getX(), this.tripod.getY() + this.tripod.getBbHeight() * 0.5D, this.tripod.getZ(),
                    14, this.radius * 0.3D, 1.0D, this.radius * 0.3D, 0.12D);
        }
        this.tripod.level().playSound(null, this.tripod.getX(), this.tripod.getY(), this.tripod.getZ(),
                SoundEvents.BEACON_AMBIENT, SoundSource.HOSTILE, 1.6F, 0.6F);
    }

    @Override
    public void stop() {
        this.tripod.setAbilityCharging(false);
        this.tripod.setAbilityCooldown(this.cooldown);
    }
}
