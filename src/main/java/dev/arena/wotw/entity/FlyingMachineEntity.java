package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.ai.HoverGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;

/**
 * Flying Machine — a flat Martian disc that never touches the ground.
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Permanent flight</b> — it circles its prey from above, well out of sword reach.</li>
 *     <li><b>Rotor heat ray</b> — a long, accurate beam from the sky.</li>
 * </ul>
 */
public class FlyingMachineEntity extends AbstractTripodEntity {

    public FlyingMachineEntity(EntityType<? extends FlyingMachineEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(190.0D, 0.33D, 14.0D, 8.0D, 5.0D, 1.0D, 56.0D);
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.add(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 8.5F, 46.0D, 26, 80));
        this.goalSelector.add(3, new HoverGoal(this, 10.0D, 12.0D, 1.0D));
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.6F, false,
                    Level.ExplosionInteraction.MOB);
        }
    }

    @Override
    protected void clientTick() {
        double rotorY = this.getY() + this.getBbHeight() * 0.72D;
        this.level().addParticle(ParticleTypes.CLOUD,
                this.getX() + (this.random.nextDouble() - 0.5D) * 4.0D, rotorY,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 4.0D, 0.0D, -0.02D, 0.0D);
    }

    @Override
    public void playChargeSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.PHANTOM_SWOOP, SoundSource.HOSTILE, 2.0F, 0.7F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PHANTOM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PHANTOM_HURT;
    }
}
