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
 * The Mexican poster machine: a dark hull with two huge yellow eyes and two long black tentacle arms
 * that end in claws.
 *
 * <p><b>Signature ability</b>: the <b>claws are venomous</b>. Every blow it lands leaves poison and
 * weakness behind, and the two arms strike from further out than any other machine can reach with
 * its feet.</p>
 */
public class MexicanTripodEntity extends AbstractTripodEntity {

    public MexicanTripodEntity(EntityType<? extends MexicanTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(250.0D, 0.26D, 16.0D, 12.0D, 8.0D, 0.8D, 46.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.GREEN_BEAM, 8.0F, 36.0D, 20, 100));
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        super.onMeleeHit(victim);
        victim.addEffect(new MobEffectInstance(MobEffects.POISON, 140, 1));
        victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 0));
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.ITEM_SLIME, victim.getX(), victim.getY() + 1.0D, victim.getZ(),
                    16, 0.4D, 0.5D, 0.4D, 0.02D);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(5) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.SMOKE,
                this.getX() + (this.random.nextDouble() - 0.5D) * 3.0D, this.getY() + 0.5D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 3.0D, 0.0D, 0.0D, 0.0D);
    }
}
