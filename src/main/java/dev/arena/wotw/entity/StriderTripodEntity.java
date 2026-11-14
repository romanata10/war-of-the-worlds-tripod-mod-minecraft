package dev.arena.wotw.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * The soldier of the Martian city: armoured shoulders, a helmed head with two lenses and a cannon on
 * each arm. It is the machine that walks the walls.
 *
 * <p><b>Signature ability</b>: the <b>lockstep</b>. While one strider stands, every machine around it
 * fights better — armour and speed for the whole line, and the strider itself patches them up. Break
 * the strider first or the garrison will not break at all.</p>
 */
public class StriderTripodEntity extends AbstractTripodEntity {

    private static final int LOCKSTEP_INTERVAL = 60;
    private static final double LOCKSTEP_RANGE = 16.0D;

    public StriderTripodEntity(EntityType<? extends StriderTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(420.0D, 0.28D, 17.0D, 12.0D, 8.0D, 0.95D, 58.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(LOCKSTEP_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        List<Mob> line = server.getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(LOCKSTEP_RANGE),
                mob -> mob != this && mob.isAlive() && mob instanceof AbstractTripodEntity);
        if (line.isEmpty()) {
            return;
        }
        for (Mob machine : line) {
            machine.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, 0, false, false, false));
            machine.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 120, 0, false, false, false));
            if (machine.getHealth() < machine.getMaxHealth()) {
                machine.heal(4.0F);
            }
        }
        server.sendParticles(ParticleTypes.END_ROD, this.getX(), this.getY() + this.getBbHeight() * 0.55D,
                this.getZ(), 20, 2.4D, 0.6D, 2.4D, 0.02D);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BEACON_POWER_SELECT,
                SoundSource.HOSTILE, 1.4F, 0.8F);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(6) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.END_ROD,
                this.getX() + (this.random.nextDouble() - 0.5D) * 3.0D,
                this.getY() + this.getBbHeight() * 0.55D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 3.0D, 0.0D, 0.01D, 0.0D);
    }
}
