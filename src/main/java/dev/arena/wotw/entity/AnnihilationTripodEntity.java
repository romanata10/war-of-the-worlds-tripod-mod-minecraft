package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.PounceGoal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * War of the Worlds: Annihilation: the only Martian machine that walks on two legs, all angular plates
 * and cyan panels, with two long scythe arms folded in front of it.
 *
 * <p><b>Signature ability</b>: the <b>scythe leap</b>. It springs across the field and comes down with
 * both blades; anything it lands on is cut open, and anything it merely runs past still loses a limb.</p>
 */
public class AnnihilationTripodEntity extends AbstractTripodEntity {

    public AnnihilationTripodEntity(EntityType<? extends AnnihilationTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(320.0D, 0.34D, 15.0D, 10.0D, 6.0D, 0.7D, 48.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new PounceGoal(this, 18.0D, 140));
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        super.onMeleeHit(victim);
        victim.hurt(this.damageSources().mobAttack(this), 6.0F);
        this.level().addParticle(ParticleTypes.SWEEP_ATTACK,
                victim.getX(), victim.getY() + victim.getBbHeight() * 0.5D, victim.getZ(), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit) {
            this.level().playSound(null, this.blockPosition(), net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_SWEEP,
                    net.minecraft.sounds.SoundSource.HOSTILE, 1.4F, 0.8F);
        }
        return hit;
    }
}
