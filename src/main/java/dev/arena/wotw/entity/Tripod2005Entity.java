package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.registry.ModSounds;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

/**
 * The 2005 Steven Spielberg tripod. The iconic one: a smooth black-green hull, a single glowing
 * eye, three insect legs… and that horn.
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Heat ray</b> — a slow, wide, world-igniting beam.</li>
 *     <li><b>Tripod shield</b> — every 12 seconds it raises an energy shield that deflects every
 *     projectile (arrows, tridents, snowballs) for 4 seconds.</li>
 *     <li><b>Tentacle grab</b> — melee hits lift the victim into the air with levitation.</li>
 * </ul>
 */
public class Tripod2005Entity extends AbstractTripodEntity {

    public Tripod2005Entity(EntityType<? extends Tripod2005Entity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(220.0D, 0.28D, 18.0D, 12.0D, 8.0D, 1.0D, 48.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.add(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 9.0F, 40.0D, 30, 100));
    }

    @Override
    protected void serverTick() {
        // The war machine idles its shield up and down while it has prey.
        if (this.getTarget() != null && !this.isShielded() && this.onIntervalZero(240)) {
            this.startShield(80);
            this.world.playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.HOSTILE, 2.4F, 0.7F);
            this.world.playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.TRIPOD_HORN, SoundCategory.HOSTILE, 3.0F, 0.45F);
        }
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        victim.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 30, 1));
    }

    @Override
    protected void clientTick() {
        double eyeY = this.getY() + this.getHeight() * 0.78D;
        this.world.addParticle(ParticleTypes.SMOKE,
                this.getX() + (this.random.nextDouble() - 0.5D) * 0.4D, eyeY,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 0.4D, 0.0D, 0.01D, 0.0D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.TRIPOD_HORN;
    }
}
