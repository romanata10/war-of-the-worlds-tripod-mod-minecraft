package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.ai.StompGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

/**
 * The Asylum "War of the Worlds" (2005, the mockbuster one) walker.
 *
 * <p>A six legged robot, wide and long, dark brown, with a rectangular spiked body and a single
 * green eye in the front that doubles as a thermal beam.</p>
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Green thermal beam</b> — a dirty heat ray from the single cyclops eye.</li>
 *     <li><b>Ground slam</b> — stomps the ground, damaging and launching everything nearby.</li>
 * </ul>
 */
public class AsylumWalkerEntity extends AbstractTripodEntity {

    public AsylumWalkerEntity(EntityType<? extends AsylumWalkerEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(260.0D, 0.20D, 20.0D, 16.0D, 10.0D, 1.0D, 52.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.add(2, new StompGoal(this, 9.0F, 8.0D, 140));
        this.goalSelector.add(3, new BeamAttackGoal(this, BeamKind.GREEN_BEAM, 7.5F, 36.0D, 28, 85));
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        Vec3 push = victim.position().subtract(this.position());
        double length = Math.max(0.5D, push.length());
        victim.setDeltaMovement(push.x / length * 0.8D, 0.35D, push.z / length * 0.8D);
        victim.hurtMarked = true;
    }

    @Override
    protected void clientTick() {
        double eyeY = this.getY() + this.getBbHeight() * 0.62D;
        this.level().addParticle(ParticleTypes.SMOKE,
                this.getX() + (this.random.nextDouble() - 0.5D) * 0.5D, eyeY,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 0.5D, 0.0D, 0.03D, 0.0D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.RAVAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.RAVAGER_HURT;
    }
}
