package dev.arena.wotw.entity;

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
 * The harvester: a wide body with a hopper on its back and two long scythes that sweep the ground.
 *
 * <p><b>Signature ability</b>: the <b>harvest</b>. Every cut feeds it. A hit heals the machine and
 * sends it striding faster, and a kill gives it a full repair, so a harvester that has already eaten
 * a squad of soldiers is a much worse problem than a fresh one.</p>
 */
public class HarvesterTripodEntity extends AbstractTripodEntity {

    public HarvesterTripodEntity(EntityType<? extends HarvesterTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(360.0D, 0.29D, 16.0D, 7.0D, 5.0D, 0.75D, 46.0D);
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        super.onMeleeHit(victim);
        if (this.level().isClientSide) {
            return;
        }
        boolean killed = victim.isDeadOrDying();
        this.heal(killed ? this.getMaxHealth() : 8.0F);
        this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, killed ? 160 : 80, 0, false, false, false));
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.DAMAGE_INDICATOR, victim.getX(),
                    victim.getY() + victim.getBbHeight() * 0.6D, victim.getZ(), killed ? 30 : 12,
                    0.4D, 0.5D, 0.4D, 0.1D);
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(),
                    this.getY() + this.getBbHeight() * 0.5D, this.getZ(), 10, 0.6D, 0.8D, 0.6D, 0.02D);
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.HOSTILE, 1.6F, 0.6F);
    }
}
