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
 * The flying manta machine from George Pal's 1953 "The War of the Worlds".
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Flight</b> — it has no legs on the ground: it permanently hovers and circles its
 *     prey from above.</li>
 *     <li><b>Wing heat ray</b> — a long range beam fired from the sky.</li>
 *     <li><b>Death dive</b> — when destroyed it crashes and explodes.</li>
 * </ul>
 */
public class FlyingMantaEntity extends AbstractTripodEntity {

    public FlyingMantaEntity(EntityType<? extends FlyingMantaEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(180.0D, 0.30D, 12.0D, 6.0D, 4.0D, 0.9D, 64.0D);
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    protected void addExtraGoals() {
        // The beam has a higher priority than the flight pattern, so the manta stops circling
        // for the half second it needs to charge and fire, then resumes its orbit.
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 8.0F, 48.0D, 24, 80));
        this.goalSelector.addGoal(3, new HoverGoal(this, 11.0D, 9.0D, 0.85D));
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0F, false,
                    Level.ExplosionInteraction.MOB);
        }
    }

    @Override
    protected void clientTick() {
        double wingY = this.getY() + this.getBbHeight() * 0.45D;
        this.level().addParticle(ParticleTypes.SMOKE,
                this.getX() + (this.random.nextDouble() - 0.5D) * 3.0D, wingY,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 3.0D, 0.0D, -0.01D, 0.0D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PHANTOM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PHANTOM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PHANTOM_DEATH;
    }

    @Override
    public float getSoundVolume() {
        return 2.4F;
    }

    @Override
    public void playChargeSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.PHANTOM_SWOOP, SoundSource.HOSTILE, 2.2F, 0.5F);
    }
}
