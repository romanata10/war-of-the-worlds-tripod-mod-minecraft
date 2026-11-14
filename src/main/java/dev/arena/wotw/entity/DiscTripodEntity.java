package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

/**
 * The white disc of the 1972 paperback: a wide saucer with a black brow band and a red pupil,
 * standing on four needle legs with ball joints.
 *
 * <p><b>Signature ability</b>: the <b>four-fold salvo</b>. The machine does not pick one victim —
 * its four emitters each find their own, so a squad that surrounds it is cut down from four sides at
 * once.</p>
 */
public class DiscTripodEntity extends AbstractTripodEntity {

    private static final int SALVO_INTERVAL = 120;
    private static final double SALVO_RANGE = 28.0D;

    public DiscTripodEntity(EntityType<? extends DiscTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(300.0D, 0.30D, 11.0D, 7.0D, 5.0D, 0.8D, 54.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(SALVO_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        List<LivingEntity> victims = server.getEntitiesOfClass(LivingEntity.class,
                        this.getBoundingBox().inflate(SALVO_RANGE),
                        entity -> entity != this && entity.isAlive() && this.canAttack(entity))
                .stream()
                .sorted(Comparator.comparingDouble(this::distanceToSqr))
                .limit(4)
                .toList();
        if (victims.isEmpty()) {
            return;
        }
        this.setAbilityCharging(true);
        this.playChargeSound();
        for (LivingEntity victim : victims) {
            this.fireBeamAt(victim, BeamKind.LASER, 7.0F);
        }
        if (victims.size() > 1) {
            server.sendParticles(ParticleTypes.END_ROD, this.getX(),
                    this.getY() + this.getBbHeight() * 0.62D, this.getZ(), 24, 1.6D, 0.5D, 1.6D, 0.05D);
            server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BEACON_ACTIVATE,
                    SoundSource.HOSTILE, 1.6F, 1.3F);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        Vec3 eye = new Vec3(this.getX(), this.getY() + this.getBbHeight() * 0.62D, this.getZ());
        for (int index = 0; index < 4; index++) {
            double angle = 1.5707963267948966 * index + this.tickCount * 0.04D;
            this.level().addParticle(ParticleTypes.END_ROD,
                    eye.x + Math.cos(angle) * 2.6D, eye.y, eye.z + Math.sin(angle) * 2.6D,
                    0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean fireImmune() {
        return true;
    }
}
