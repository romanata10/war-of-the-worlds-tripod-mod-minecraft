package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

/**
 * The red tripod of the BBC series — the armed, "military" variant.
 *
 * <p><b>Signature ability</b></p>
 * <ul>
 *     <li><b>Laser volley</b> — three fast crimson laser bolts per cast, each one piercing and
 *     setting the target on fire.</li>
 * </ul>
 */
public class BbcRedTripodEntity extends AbstractTripodEntity {

    public BbcRedTripodEntity(EntityType<? extends BbcRedTripodEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(150.0D, 0.26D, 12.0D, 10.0D, 5.0D, 0.9D, 52.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.add(2, new BeamAttackGoal(this, BeamKind.LASER, 6.0F, 44.0D, 18, 90, 3, 7, true));
    }

    @Override
    protected void clientTick() {
        double eyeY = this.getY() + this.getHeight() * 0.72D;
        this.getWorld().addParticle(ParticleTypes.CRIT,
                this.getX() + (this.random.nextDouble() - 0.5D) * 0.35D, eyeY,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 0.35D, 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BLAZE_AMBIENT;
    }
}
