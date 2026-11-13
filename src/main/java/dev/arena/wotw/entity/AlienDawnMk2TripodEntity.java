package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The second Alien Dawn machine: a mirrored scoop with a slotted grille face on three slender legs.
 *
 * <p><b>Signature ability</b>: the <b>sonic echo</b>. The grille shouts, and every human inside the
 * cone is deafened and stunned — the machine then walks up to the helpless victim.</p>
 */
public class AlienDawnMk2TripodEntity extends AbstractTripodEntity {

    public AlienDawnMk2TripodEntity(EntityType<? extends AlienDawnMk2TripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(240.0D, 0.29D, 11.0D, 8.0D, 6.0D, 0.8D, 48.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 5.0F, 24.0D, 26, 130));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(150) || !this.isAbilityReady()) {
            return;
        }
        this.setAbilityCooldown(150);
        this.level().playSound(null, this.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM,
                SoundSource.HOSTILE, 2.4F, 1.1F);
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(13.0D),
                entity -> entity != this && !(entity instanceof AbstractTripodEntity))) {
            victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 90, 4), this);
            victim.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 140, 1), this);
            victim.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 120, 1), this);
            victim.hurtMarked = true;
        }
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SONIC_BOOM, this.getX(), this.getY() + this.getBbHeight() * 0.6D,
                    this.getZ(), 6, 1.5D, 0.6D, 1.5D, 0.0D);
        }
    }
}
