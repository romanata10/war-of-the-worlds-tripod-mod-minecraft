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
 * Scary Movie 4: a swept dark canopy carrying one huge lamp lens between two smaller ones, on thin
 * riveted legs.
 *
 * <p><b>Signature ability</b>: the <b>blinding flash</b>. The main lens dumps its whole capacitor in a
 * single burst; everyone looking at it goes blind for several seconds, which is exactly how long the
 * machine needs to close in.</p>
 */
public class ScaryMovieTripodEntity extends AbstractTripodEntity {

    public ScaryMovieTripodEntity(EntityType<? extends ScaryMovieTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(210.0D, 0.3D, 11.0D, 8.0D, 5.0D, 0.75D, 46.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 7.0F, 30.0D, 20, 100));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(200) || !this.isAbilityReady()) {
            return;
        }
        this.setAbilityCooldown(200);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY() + this.getBbHeight() * 0.8D,
                    this.getZ(), 4, 0.6D, 0.4D, 0.6D, 0.0D);
            server.sendParticles(ParticleTypes.END_ROD, this.getX(), this.getY() + this.getBbHeight() * 0.8D,
                    this.getZ(), 40, 2.0D, 1.0D, 2.0D, 0.12D);
        }
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(18.0D),
                entity -> entity != this && !(entity instanceof AbstractTripodEntity))) {
            victim.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 160, 0), this);
            victim.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0), this);
            victim.hurt(this.damageSources().magic(), 1.5F);
        }
    }
}
