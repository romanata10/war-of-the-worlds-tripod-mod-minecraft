package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.ai.SmokeBombGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The Bombardier — a mortar tube on three legs.
 *
 * <p><b>Signature ability</b>: instead of a heat ray it throws a bomb of black smoke on a high arc.
 * The bomb bursts on impact and leaves the killing cloud behind, so a trench or a rooftop that the
 * ray cannot reach is exactly where this machine aims.</p>
 */
public class BombTripodEntity extends AbstractTripodEntity {

    public BombTripodEntity(EntityType<? extends BombTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(270.0D, 0.24D, 12.0D, 12.0D, 8.0D, 0.9D, 48.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new SmokeBombGoal(this, 46.0D, 160));
        // something for when the enemy is already in its face
        this.goalSelector.addGoal(3, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 5.0F, 22.0D, 18, 120));
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                this.getX(), this.getY() + this.getBbHeight() * 0.9D, this.getZ(), 0.0D, 0.06D, 0.0D);
        this.level().addParticle(ParticleTypes.SMOKE,
                this.getX(), this.getY() + this.getBbHeight() * 0.55D, this.getZ(), 0.0D, 0.03D, 0.0D);
    }
}
