package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * The machine that came back to Surrey: a faceted head with two big green eyes on six legs.
 *
 * <p><b>Signature ability</b>: the <b>tremor</b>. It slams all six legs down at once and the ground
 * throws everything standing near it into the air.</p>
 */
public class SurreyTripodEntity extends AbstractTripodEntity {

    private static final int TREMOR_INTERVAL = 120;
    private static final double TREMOR_RANGE = 10.0D;

    public SurreyTripodEntity(EntityType<? extends SurreyTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(420.0D, 0.27D, 16.0D, 10.0D, 7.0D, 1.0D, 52.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.GREEN_BEAM, 8.0F, 36.0D, 50, 80));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(TREMOR_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        this.setAbilityCharging(true);
        for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(TREMOR_RANGE),
                entity -> entity != this && entity.isAlive() && this.canAttack(entity))) {
            victim.hurt(this.damageSources().mobAttack(this), 8.0F);
            Vec3 away = victim.position().subtract(this.position());
            if (away.lengthSqr() > 0.01D) {
                away = away.normalize().scale(0.45D);
            }
            victim.push(away.x, 0.85D, away.z);
            victim.hurtMarked = true;
        }
        server.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + 0.3D, this.getZ(),
                12, 4.0D, 0.3D, 4.0D, 0.05D);
        server.sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY() + 0.2D, this.getZ(),
                40, 5.0D, 0.2D, 5.0D, 0.08D);
        server.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE,
                SoundSource.HOSTILE, 2.0F, 0.5F);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.HAPPY_VILLAGER,
                this.getX() + (this.random.nextDouble() - 0.5D) * 2.6D,
                this.getY() + 0.4D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 2.6D, 0.0D, 0.02D, 0.0D);
    }
}
