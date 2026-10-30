package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.ai.BlackSmokeGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

/**
 * Tempest — the tallest Martian walker: a tower on four heavy legs.
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Twin heat rays</b> — two cannon pods fire one after another.</li>
 *     <li><b>Black smoke vents</b> — the stacks on its back launch the same killing cloud the 1898
 *     machines used.</li>
 * </ul>
 */
public class TempestEntity extends AbstractTripodEntity {

    public TempestEntity(EntityType<? extends TempestEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(300.0D, 0.24D, 22.0D, 16.0D, 10.0D, 1.0D, 52.0D);
    }

    @Override
    protected void addExtraGoals() {
        // two shots, so both cannon pods fire
        this.goalSelector.add(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 11.0F, 52.0D, 32, 110, 2, 10, true));
        this.goalSelector.add(3, new BlackSmokeGoal(this, 28.0D, 260));
    }

    @Override
    protected void clientTick() {
        for (int side = -1; side <= 1; side += 2) {
            if (this.random.nextInt(3) != 0) {
                continue;
            }
            this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE,
                    this.getX() + side * 0.9D, this.getY() + this.getHeight() * 0.92D,
                    this.getZ() + 1.2D, 0.0D, 0.05D, 0.0D);
        }
    }
}
