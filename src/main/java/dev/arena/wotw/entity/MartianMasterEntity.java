package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

/**
 * The Martian commander: bigger, darker and wrapped in a crown of feelers.
 *
 * <p><b>Signature ability — command.</b> Every few seconds it broadcasts an order that gives every
 * tripod and Martian around it Strength and Speed for fifteen seconds; and unlike the common
 * Martian it also carries a hand held heat ray.</p>
 */
public class MartianMasterEntity extends MartianEntity {

    public MartianMasterEntity(EntityType<? extends MartianMasterEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
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
        if (this.getWorld().isClient) {
            return;
        }
        if (this.onIntervalZero(90)) {
            LivingEntity target = this.getTarget();
            if (target != null && this.canSeeTarget(target)
                    && this.squaredDistanceTo(target) < 28.0D * 28.0D) {
                this.fireBeamAt(target, BeamKind.HEAT_RAY, 7.0F);
            }
        }
        if (this.onIntervalZero(240)) {
            this.command();
        }
    }

    private void command() {
        if (!(this.getWorld() instanceof ServerWorld world)) {
            return;
        }
        Box area = new Box(this.getPos().add(-24.0D, -10.0D, -24.0D), this.getPos().add(24.0D, 10.0D, 24.0D));
        for (AbstractTripodEntity ally : world.getEntitiesByClass(AbstractTripodEntity.class, area,
                entity -> entity.isAlive())) {
            ally.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 300, 0));
            ally.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 300, 0));
        }
        world.spawnParticles(ParticleTypes.ENCHANT, this.getX(), this.getY() + 1.0D, this.getZ(),
                60, 6.0D, 2.0D, 6.0D, 0.4D);
        this.playSound(SoundEvents.BLOCK_BEACON_POWER_SELECT, 2.2F, 0.5F);
    }

    @Override
    public int getXpToDrop() {
        return 60;
    }
}
