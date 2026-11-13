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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Jeff Wayne's war machine: a polished teardrop with two green eyes and a trumpet on each flank.
 *
 * <p><b>Signature ability</b>: the <b>war drum</b>. Every few seconds it beats out the famous
 * three note call: every Martian machine within twenty four blocks fights harder (strength and
 * resistance) while people inside the ring are left deafened and reeling. Then the trumpets fire.</p>
 */
public class WayneTripodEntity extends AbstractTripodEntity {

    private static final double DRUM_RADIUS = 24.0D;

    public WayneTripodEntity(EntityType<? extends WayneTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(300.0D, 0.24D, 14.0D, 14.0D, 9.0D, 0.8D, 48.0D);
    }

    @Override
    protected void addExtraGoals() {
        // both trumpets, one after the other
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 8.0F, 40.0D, 24, 100, 2, 10, true));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(160)) {
            return;
        }
        boolean drummed = false;
        for (AbstractTripodEntity ally : this.level().getEntitiesOfClass(AbstractTripodEntity.class,
                this.getBoundingBox().inflate(DRUM_RADIUS))) {
            ally.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 1));
            ally.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 0));
            ally.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 0));
            drummed = true;
        }
        for (Player player : this.level().getEntitiesOfClass(Player.class,
                this.getBoundingBox().inflate(DRUM_RADIUS))) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, 1));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 1));
            drummed = true;
        }
        if (drummed) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.RAID_HORN.value(),
                    SoundSource.HOSTILE, 3.0F, 0.6F);
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.NOTE, this.getX(), this.getY() + this.getBbHeight() * 0.8D,
                        this.getZ(), 24, 3.0D, 2.0D, 3.0D, 0.02D);
            }
        }
    }
}
