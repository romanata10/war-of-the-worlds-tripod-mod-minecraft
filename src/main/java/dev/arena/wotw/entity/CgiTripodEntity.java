package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The CGI machine of the Mars clip: a black wedge of a head under a wide brim, three white eyes in
 * the face and very thin legs.
 *
 * <p><b>Signature ability</b>: the <b>double ray</b>. The three eyes fire in sequence, so the second
 * ray leaves the moment a player thinks the first one is over.</p>
 */
public class CgiTripodEntity extends AbstractTripodEntity {

    private static final int RAY_INTERVAL = 100;
    private static final int SECOND_RAY_DELAY = 8;

    private int secondRay;

    public CgiTripodEntity(EntityType<? extends CgiTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(320.0D, 0.32D, 10.0D, 7.0D, 4.0D, 0.8D, 46.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (this.secondRay > 0) {
            this.secondRay--;
            if (this.secondRay == 0) {
                LivingEntity target = this.getTarget();
                if (target != null && target.isAlive()) {
                    this.fireBeamAt(target, BeamKind.LASER, 7.0F);
                }
            }
        }
        if (!this.onIntervalZero(RAY_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }
        this.setAbilityCharging(true);
        this.fireBeamAt(target, BeamKind.LASER, 7.0F);
        this.secondRay = SECOND_RAY_DELAY;
        if (this.level() instanceof ServerLevel server) {
            server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BEACON_POWER_SELECT,
                    SoundSource.HOSTILE, 1.8F, 1.4F);
        }
    }
}
