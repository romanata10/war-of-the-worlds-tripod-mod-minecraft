package dev.arena.wotw.entity;

import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

import java.util.Comparator;

/**
 * The Russian machine of the war: a melon of gunmetal plate, one big green eye, a cross antenna and
 * a mesh radar dish on the shoulder.
 *
 * <p><b>Signature ability</b>: the <b>double salvo</b> — a cutting laser from the eye and a heat ray
 * from the chin gun, fired at the same moment and at two different targets.</p>
 */
public class RussianTripodEntity extends AbstractTripodEntity {

    private static final int SALVO_INTERVAL = 150;

    public RussianTripodEntity(EntityType<? extends RussianTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(400.0D, 0.25D, 15.0D, 11.0D, 7.0D, 0.95D, 58.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(SALVO_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || !this.hasLineOfSight(target)) {
            return;
        }
        this.setAbilityCharging(true);
        this.playChargeSound();
        this.fireBeamAt(target, BeamKind.LASER, 9.0F);
        LivingEntity second = server.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(34.0D),
                        entity -> entity != this && entity != target && entity.isAlive() && this.canAttack(entity))
                .stream()
                .min(Comparator.comparingDouble(this::distanceToSqr))
                .orElse(target);
        this.fireBeamAt(second, BeamKind.HEAT_RAY, 8.0F);
        server.sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY() + this.getBbHeight() * 0.7D,
                this.getZ(), 18, 1.2D, 0.6D, 1.2D, 0.02D);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIRECHARGE_USE,
                SoundSource.HOSTILE, 1.8F, 0.6F);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(5) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.SMOKE,
                this.getX() + (this.random.nextDouble() - 0.5D) * 1.2D,
                this.getY() + this.getBbHeight() * 0.85D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 1.2D, 0.0D, 0.02D, 0.0D);
    }
}
