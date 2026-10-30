package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.ai.HoverGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

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

    public FlyingMachineEntity(EntityType<? extends FlyingMachineEntity> type, World world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
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
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.6F, false,
                    World.ExplosionSourceType.MOB);
        }
    }

    @Override
    protected void clientTick() {
        double rotorY = this.getY() + this.getHeight() * 0.72D;
        this.getWorld().addParticle(ParticleTypes.CLOUD,
                this.getX() + (this.random.nextDouble() - 0.5D) * 4.0D, rotorY,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 4.0D, 0.0D, -0.02D, 0.0D);
    }

    @Override
    public void playChargeSound() {
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_PHANTOM_SWOOP, SoundCategory.HOSTILE, 2.0F, 0.7F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PHANTOM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PHANTOM_HURT;
    }
}
