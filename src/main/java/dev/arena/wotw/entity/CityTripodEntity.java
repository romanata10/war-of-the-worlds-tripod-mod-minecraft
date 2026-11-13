package dev.arena.wotw.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * The city walker: a small dark carriage carrying a glass dome with a whole lit city inside it.
 *
 * <p><b>Signature ability</b>: the <b>city lights</b>. The dome is the power plant of the invasion —
 * every pulse repairs the Martian machines around it and knits its own hull back together. Kill it
 * first, or fight an army that never stays damaged.</p>
 */
public class CityTripodEntity extends AbstractTripodEntity {

    private static final int PULSE_INTERVAL = 180;
    private static final double AURA_RADIUS = 24.0D;

    public CityTripodEntity(EntityType<? extends CityTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(340.0D, 0.28D, 11.0D, 8.0D, 6.0D, 0.8D, 56.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(PULSE_INTERVAL)) {
            return;
        }
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        List<AbstractTripodEntity> allies = server.getEntitiesOfClass(AbstractTripodEntity.class,
                this.getBoundingBox().inflate(AURA_RADIUS));
        int repaired = 0;
        for (AbstractTripodEntity ally : allies) {
            if (ally == this || ally.getHealth() >= ally.getMaxHealth()) {
                continue;
            }
            ally.heal(14.0F);
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, ally.getX(),
                    ally.getY() + ally.getBbHeight() * 0.5D, ally.getZ(), 8, 0.6D, 1.2D, 0.6D, 0.02D);
            repaired++;
        }
        this.heal(6.0F);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BEACON_ACTIVATE,
                SoundSource.HOSTILE, 1.4F, 1.5F);
        server.sendParticles(ParticleTypes.END_ROD, this.getX(), this.getY() + this.getBbHeight() * 0.75D,
                this.getZ(), 18, 1.4D, 0.8D, 1.4D, 0.02D);
        if (repaired > 0) {
            server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BEACON_POWER_SELECT,
                    SoundSource.HOSTILE, 1.2F, 1.2F);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        double angle = this.random.nextDouble() * Math.PI * 2.0D;
        double radius = this.getBbWidth() * 0.32D;
        this.level().addParticle(ParticleTypes.GLOW,
                this.getX() + Math.cos(angle) * radius,
                this.getY() + this.getBbHeight() * 0.78D + this.random.nextDouble() * 1.6D,
                this.getZ() + Math.sin(angle) * radius, 0.0D, 0.01D, 0.0D);
    }
}
