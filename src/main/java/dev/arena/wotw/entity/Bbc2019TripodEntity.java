package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.MarkAndStrikeGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The 2019 BBC machine: hunched forward, a dark carapace clustered with cold blue lights on three
 * stilt legs.
 *
 * <p><b>Signature ability</b>: it <b>marks before it strikes</b>. A searchlight settles on you and
 * holds for three seconds while the lance charges; walk out of its sight in that time and you live,
 * stand still and a single beam drills straight through you. Its stilt legs also carry it over four
 * block ledges that stop every other walker.</p>
 */
public class Bbc2019TripodEntity extends AbstractTripodEntity {

    public Bbc2019TripodEntity(EntityType<? extends Bbc2019TripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(280.0D, 0.30D, 12.0D, 10.0D, 8.0D, 0.9D, 52.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new MarkAndStrikeGoal(this, BeamKind.LANCE, 13.0F, 54.0D, 60, 200));
    }

    @Override
    public float maxUpStep() {
        return 4.0F;                       // stilts: it steps over walls the others walk around
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(3) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                this.getX() + (this.random.nextDouble() - 0.5D) * 2.4D,
                this.getY() + this.getBbHeight() * 0.78D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 2.4D, 0.0D, 0.0D, 0.0D);
    }
}
