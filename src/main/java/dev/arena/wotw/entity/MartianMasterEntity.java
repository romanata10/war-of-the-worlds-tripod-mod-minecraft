package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;

/**
 * The Martian commander: bigger, darker and wrapped in a crown of feelers.
 *
 * <p><b>Signature ability — command.</b> Every few seconds it broadcasts an order that gives every
 * tripod and Martian around it Strength and Speed for fifteen seconds; and unlike the common
 * Martian it also carries a hand held heat ray.</p>
 */
public class MartianMasterEntity extends MartianEntity {

    public MartianMasterEntity(EntityType<? extends MartianMasterEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(70.0D, 0.28D, 9.0D, 8.0D, 4.0D, 0.0D, 32.0D);
    }

    @Override
    public boolean isCommander() {
        return true;
    }

    @Override
    protected int getSmokeCooldown() {
        return 110;
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (this.level().isClientSide) {
            return;
        }
        if (this.onIntervalZero(90)) {
            LivingEntity target = this.getTarget();
            if (target != null && this.canSeeTarget(target)
                    && this.distanceToSqr(target) < 28.0D * 28.0D) {
                this.fireBeamAt(target, BeamKind.HEAT_RAY, 7.0F);
            }
        }
        if (this.onIntervalZero(240)) {
            this.command();
        }
    }

    private void command() {
        if (!(this.level() instanceof ServerLevel world)) {
            return;
        }
        AABB area = new AABB(this.position().add(-24.0D, -10.0D, -24.0D), this.position().add(24.0D, 10.0D, 24.0D));
        for (AbstractTripodEntity ally : world.getEntitiesOfClass(AbstractTripodEntity.class, area,
                entity -> entity.isAlive())) {
            ally.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 300, 0));
            ally.addEffect(new MobEffectInstance(MobEffects.SPEED, 300, 0));
        }
        world.sendParticles(ParticleTypes.ENCHANT, this.getX(), this.getY() + 1.0D, this.getZ(),
                60, 6.0D, 2.0D, 6.0D, 0.4D);
        this.playSound(SoundEvents.BEACON_POWER_SELECT, 2.2F, 0.5F);
    }

    @Override
    public int getExperienceReward() {
        return 60;
    }
}
