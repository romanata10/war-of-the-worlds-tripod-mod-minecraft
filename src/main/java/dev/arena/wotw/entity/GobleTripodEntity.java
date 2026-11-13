package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.ai.HookGrabGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Warwick Goble's 1906 machine: a wide disc on three lattice girder legs, with a crane arm, a hook
 * and a lamp slung from the jib.
 *
 * <p><b>Signature ability</b>: the <b>crane hook</b>. It swings the jib out, catches whoever is
 * standing too close and hauls them into the air — and what goes up comes down on the rocks.</p>
 */
public class GobleTripodEntity extends AbstractTripodEntity {

    public GobleTripodEntity(EntityType<? extends GobleTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(240.0D, 0.26D, 10.0D, 14.0D, 8.0D, 0.8D, 46.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new HookGrabGoal(this, 22.0D, 7.0F, 200));
        this.goalSelector.addGoal(3, new BeamAttackGoal(this, BeamKind.LASER, 6.0F, 32.0D, 18, 90));
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(5) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.FLAME,
                this.getX() + 0.6D, this.getY() + this.getBbHeight() * 0.72D, this.getZ(),
                0.0D, 0.01D, 0.0D);
    }
}
