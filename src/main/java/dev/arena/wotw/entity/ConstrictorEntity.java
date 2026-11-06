package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The Martian Constrictor — the machine built to break a human charge.
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Chemical foam</b> — two sprayer barrels hose the ground with a fast-setting foam that
 *     glues boots to the dirt: heavy slowness, no digging, no jumping, and a chemical burn.</li>
 *     <li><b>Heat lens</b> — once the squad cannot move, the lens in its face boils what is stuck.</li>
 * </ul>
 */
public class ConstrictorEntity extends AbstractTripodEntity {

    /** Radius of the foam burst. */
    private static final double FOAM_RADIUS = 11.0D;

    public ConstrictorEntity(EntityType<? extends ConstrictorEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(300.0D, 0.25D, 16.0D, 16.0D, 9.0D, 0.8D, 46.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.GREEN_BEAM, 9.0F, 40.0D, 22, 110));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(100)) {
            return;
        }
        boolean sprayed = false;
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(FOAM_RADIUS))) {
            if (victim instanceof AbstractTripodEntity) {
                continue;
            }
            victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 4));
            victim.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 140, 2));
            victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 0));
            victim.hurt(this.damageSources().magic(), 6.0F);
            sprayed = true;
        }
        if (sprayed && this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.ITEM_SLIME, this.getX(), this.getY() + 0.6D, this.getZ(),
                    50, FOAM_RADIUS * 0.6D, 0.8D, FOAM_RADIUS * 0.6D, 0.04D);
            server.sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY() + 0.4D, this.getZ(),
                    30, FOAM_RADIUS * 0.5D, 0.4D, FOAM_RADIUS * 0.5D, 0.01D);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(2) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.ITEM_SLIME,
                this.getX() + (this.random.nextDouble() - 0.5D) * 4.0D, this.getY() + 0.4D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 4.0D, 0.0D, 0.02D, 0.0D);
    }
}
