package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * True War of the Worlds 2012: a heavy copper engine bolted onto a braced tripod, leaking coolant
 * everywhere.
 *
 * <p><b>Signature ability</b>: the coolant. The engine drips <b>acid</b> as it walks; whoever stands in
 * the pool is poisoned and slowed, and the pool keeps burning for a while after the machine moves on.</p>
 */
public class True2012TripodEntity extends AbstractTripodEntity {

    public True2012TripodEntity(EntityType<? extends True2012TripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(230.0D, 0.23D, 10.0D, 13.0D, 9.0D, 0.95D, 42.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.GREEN_BEAM, 7.0F, 30.0D, 22, 110));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(20)) {
            return;
        }
        boolean moved = this.getDeltaMovement().horizontalDistanceSqr() > 0.001D;
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(5.5D).expandTowards(0.0D, -2.0D, 0.0D),
                entity -> entity != this && !(entity instanceof AbstractTripodEntity))) {
            victim.hurt(this.damageSources().magic(), 1.0F);
            victim.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 0), this);
            victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1), this);
        }
        if (moved) {
            this.level().addParticle(ParticleTypes.ITEM_SLIME,
                    this.getX() + (this.random.nextDouble() - 0.5D) * 3.0D,
                    this.getY() + 0.2D,
                    this.getZ() + (this.random.nextDouble() - 0.5D) * 3.0D, 0.0D, 0.0D, 0.0D);
        }
    }
}
