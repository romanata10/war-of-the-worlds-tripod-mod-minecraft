package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The machine that fought the F-15: a low dark hull under a swept crest, built to close fast and
 * leave.
 *
 * <p><b>Signature ability</b>: the <b>burst</b> — three laser shots in quick succession from a hull
 * that never stands still between them.</p>
 */
public class FalconTripodEntity extends AbstractTripodEntity {

    private static final int BURST_INTERVAL = 95;
    private static final int SHOT_GAP = 4;
    private static final double BURST_RANGE = 52.0D;

    private int burstLeft;
    private int nextShot;

    public FalconTripodEntity(EntityType<? extends FalconTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(250.0D, 0.36D, 11.0D, 6.0D, 4.0D, 0.7D, 62.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        LivingEntity target = this.getTarget();
        if (target == null) {
            this.burstLeft = 0;
            return;
        }
        if (this.burstLeft > 0 && this.tickCount >= this.nextShot
                && this.distanceToSqr(target) <= BURST_RANGE * BURST_RANGE && this.hasLineOfSight(target)) {
            this.setAbilityCharging(true);
            this.fireBeamAt(target, BeamKind.LASER, 5.0F);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARROW_SHOOT,
                    SoundSource.HOSTILE, 1.6F, 2.0F);
            this.burstLeft--;
            this.nextShot = this.tickCount + SHOT_GAP;
            return;
        }
        if (this.onIntervalZero(BURST_INTERVAL)) {
            this.burstLeft = 3;
            this.nextShot = this.tickCount;
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.SMOKE,
                this.getX() - this.getLookAngle().x * 1.4D,
                this.getY() + this.getBbHeight() * 0.55D,
                this.getZ() - this.getLookAngle().z * 1.4D, 0.0D, 0.0D, 0.0D);
    }
}
