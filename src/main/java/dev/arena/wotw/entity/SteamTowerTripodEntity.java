package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Steam Towers: a dark dome with a brass equator, hanging between two drilled brass yoke arms.
 *
 * <p><b>Signature ability</b>: the boiler vents. The cloud blinds and slows anything human, and the
 * Martians standing in the same cloud get faster — their own machines breathe it.</p>
 */
public class SteamTowerTripodEntity extends AbstractTripodEntity {

    public SteamTowerTripodEntity(EntityType<? extends SteamTowerTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(300.0D, 0.24D, 10.0D, 14.0D, 10.0D, 1.0D, 44.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 6.0F, 28.0D, 24, 120));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(180) || !this.isAbilityReady()) {
            return;
        }
        this.setAbilityCooldown(180);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY() + 1.0D, this.getZ(),
                    120, 7.0D, 1.5D, 7.0D, 0.08D);
            server.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY() + 1.0D,
                    this.getZ(), 40, 5.0D, 1.0D, 5.0D, 0.03D);
        }
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(11.0D), entity -> entity != this)) {
            if (victim instanceof AbstractTripodEntity) {
                victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 140, 0), this);
                continue;
            }
            victim.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 0), this);
            victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1), this);
            victim.hurt(this.damageSources().magic(), 2.0F);
        }
    }
}
