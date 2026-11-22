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
 * The Spore machine: the cartoon cyclops with one huge eye and a sprout growing out of its crown.
 *
 * <p><b>Signature ability</b>: the <b>spore cloud</b>. It breathes out a cloud of spores that
 * poisons everything living around it for six seconds, and the cloud hangs where it was breathed.</p>
 */
public class SporeTripodEntity extends AbstractTripodEntity {

    private static final int CLOUD_INTERVAL = 120;
    private static final double CLOUD_RANGE = 8.0D;

    public SporeTripodEntity(EntityType<? extends SporeTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(300.0D, 0.30D, 8.0D, 8.0D, 4.0D, 0.8D, 44.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(CLOUD_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        this.setAbilityCharging(true);
        for (LivingEntity victim : this.enemiesIn(server.getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(CLOUD_RANGE),
                entity -> entity != this && entity.isAlive()))) {
            victim.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 0), this);
        }
        server.sendParticles(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY() + this.getBbHeight() * 0.5D,
                this.getZ(), 90, CLOUD_RANGE * 0.5D, 1.2D, CLOUD_RANGE * 0.5D, 0.02D);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.SNIFFER_SNIFFING,
                SoundSource.HOSTILE, 2.0F, 0.7F);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.CRIMSON_SPORE,
                this.getX() + (this.random.nextDouble() - 0.5D) * 3.0D,
                this.getY() + this.getBbHeight() * 0.6D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 3.0D, 0.0D, -0.01D, 0.0D);
    }

    /** The sprout is Martian growth, not the red weed: no roots behind it. */
    @Override
    public boolean hasRootTrail() {
        return false;
    }
}
