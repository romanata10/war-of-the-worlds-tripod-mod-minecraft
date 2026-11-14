package dev.arena.wotw.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.List;

/**
 * The 1917 Calmann-Levy machine: a tall riveted hull with a scythe arm and a lightning rifle.
 *
 * <p><b>Signature ability</b>: the <b>chain discharge</b>. The rifle does not stop at the first
 * thing it hits — the arc leaps from the first victim to the next two standing near them, so a
 * close-packed squad is the worst way to meet it.</p>
 */
public class SiegeTripodEntity extends AbstractTripodEntity {

    private static final int DISCHARGE_INTERVAL = 140;
    private static final double DISCHARGE_RANGE = 34.0D;
    private static final double CHAIN_RADIUS = 9.0D;

    public SiegeTripodEntity(EntityType<? extends SiegeTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(360.0D, 0.26D, 15.0D, 9.0D, 6.0D, 0.9D, 56.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(DISCHARGE_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > DISCHARGE_RANGE * DISCHARGE_RANGE
                || !this.hasLineOfSight(target)) {
            return;
        }
        this.setAbilityCharging(true);
        this.playChargeSound();
        target.hurt(this.damageSources().lightningBolt(), 9.0F);
        server.sendParticles(ParticleTypes.ELECTRIC_SPARK, target.getX(),
                target.getY() + target.getBbHeight() * 0.6D, target.getZ(), 24, 0.5D, 0.8D, 0.5D, 0.3D);

        List<LivingEntity> chain = server.getEntitiesOfClass(LivingEntity.class,
                        target.getBoundingBox().inflate(CHAIN_RADIUS),
                        entity -> entity != this && entity != target && entity.isAlive() && this.canAttack(entity))
                .stream()
                .sorted(Comparator.comparingDouble(this::distanceToSqr))
                .limit(2)
                .toList();
        for (LivingEntity next : chain) {
            next.hurt(this.damageSources().lightningBolt(), 6.0F);
            server.sendParticles(ParticleTypes.ELECTRIC_SPARK, next.getX(),
                    next.getY() + next.getBbHeight() * 0.6D, next.getZ(), 16, 0.4D, 0.6D, 0.4D, 0.25D);
        }
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT,
                SoundSource.HOSTILE, 1.8F, 1.2F);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                this.getX() + (this.random.nextDouble() - 0.5D) * 1.6D,
                this.getY() + this.getBbHeight() * 0.66D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 1.6D, 0.0D, 0.0D, 0.0D);
    }
}
