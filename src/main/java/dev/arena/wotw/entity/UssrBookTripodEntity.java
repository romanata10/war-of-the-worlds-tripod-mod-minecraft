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
 * The Soviet book machine: a dark bell with a finial and two side cups, hanging on three enormously
 * long legs.
 *
 * <p><b>Signature ability</b>: the <b>jamming bell</b>. It fills the air with a howl that ruins the
 * aim and the hands of everyone nearby — nausea, weakness and a dead radio, while the bell itself
 * keeps its distance.</p>
 */
public class UssrBookTripodEntity extends AbstractTripodEntity {

    public UssrBookTripodEntity(EntityType<? extends UssrBookTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(220.0D, 0.28D, 9.0D, 9.0D, 6.0D, 0.85D, 52.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.LANCE, 6.0F, 40.0D, 22, 120));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(150) || !this.isAbilityReady()) {
            return;
        }
        this.setAbilityCooldown(150);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.NOTE, this.getX(), this.getY() + this.getBbHeight() * 0.5D,
                    this.getZ(), 26, 4.0D, 1.0D, 4.0D, 0.4D);
            server.sendParticles(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY() + 1.0D, this.getZ(),
                    30, 6.0D, 1.5D, 6.0D, 0.02D);
        }
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(16.0D),
                entity -> entity != this && !(entity instanceof AbstractTripodEntity))) {
            victim.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, 0), this);
            victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 1), this);
            victim.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 140, 2), this);
        }
    }
}
