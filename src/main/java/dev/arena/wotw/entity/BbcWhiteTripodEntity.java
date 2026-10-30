package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.CaptureFieldGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

/**
 * The white tripod of the 1984 BBC series "The Tripods". It does not fight — it harvests.
 *
 * <p><b>Signatures abilities</b></p>
 * <ul>
 *     <li><b>Capture field</b> — everything within 12 blocks is dragged towards the machine and
 *     lifted into the air (levitation + slowness), the way people are Capped in the series.</li>
 * </ul>
 */
public class BbcWhiteTripodEntity extends AbstractTripodEntity {

    public BbcWhiteTripodEntity(EntityType<? extends BbcWhiteTripodEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(130.0D, 0.24D, 10.0D, 8.0D, 4.0D, 0.9D, 48.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.add(2, new CaptureFieldGoal(this, 12.0D, 120, 260));
    }

    @Override
    protected void clientTick() {
        if (!this.isAbilityCharging()) {
            return;
        }
        for (int i = 0; i < 2; i++) {
            double angle = this.random.nextDouble() * Math.PI * 2.0D;
            double radius = 2.0D + this.random.nextDouble() * 7.0D;
            this.getWorld().addParticle(ParticleTypes.END_ROD,
                    this.getX() + Math.cos(angle) * radius,
                    this.getY() + this.random.nextDouble() * 5.0D,
                    this.getZ() + Math.sin(angle) * radius,
                    -Math.cos(angle) * 0.08D, 0.06D, -Math.sin(angle) * 0.08D);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_BEACON_AMBIENT;
    }
}
