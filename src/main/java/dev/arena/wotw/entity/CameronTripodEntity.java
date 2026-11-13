package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.ai.SonicBlastGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The 1950s machine of the Lou Cameron painting: a polished hull under a huge dark glass dome,
 * carried on three thick tubular legs.
 *
 * <p><b>Signature ability</b>: the dome is a <b>sonic cannon</b>. It winds up and fires a pressure
 * wave in a cone in front of it — damage falling off with distance, a hard shove, and a head full of
 * ringing. Its heat ray is short ranged, so the wave is what keeps people away.</p>
 */
public class CameronTripodEntity extends AbstractTripodEntity {

    public CameronTripodEntity(EntityType<? extends CameronTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(260.0D, 0.25D, 12.0D, 12.0D, 8.0D, 0.9D, 44.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new SonicBlastGoal(this, 18.0D, 9.0F, 180));
        this.goalSelector.addGoal(3, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 6.0F, 30.0D, 20, 90));
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.END_ROD,
                this.getX() + (this.random.nextDouble() - 0.5D) * 1.2D,
                this.getY() + this.getBbHeight() * 0.95D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 1.2D, 0.0D, -0.02D, 0.0D);
    }
}
