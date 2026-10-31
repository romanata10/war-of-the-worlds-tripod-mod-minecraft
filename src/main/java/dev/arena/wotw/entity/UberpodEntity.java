package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

/**
 * Uberpod — the shielded 2005 pod that carries the aliens inside the hood.
 *
 * <p><b>Signature abilities</b></p>
 * <ul>
 *     <li><b>Energy shield</b> — a bubble that throws every projectile back, so arrows and bullets
 *     simply stop for a few seconds.</li>
 *     <li><b>Twin heat rays</b> — a two shot burst from the hood.</li>
 * </ul>
 */
public class UberpodEntity extends AbstractTripodEntity {

    private static final int SHIELD_TICKS = 160;
    private static final int SHIELD_COOLDOWN = 420;

    public UberpodEntity(EntityType<? extends UberpodEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return tripodAttributes(280.0D, 0.28D, 18.0D, 14.0D, 9.0D, 1.0D, 52.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.add(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 14.0F, 50.0D, 20, 70, 2, 6, true));
    }

    @Override
    protected void serverTick() {
        if (this.isShielded() || !this.isAbilityReady()) {
            return;
        }
        if (this.getTarget() == null || this.getAttacker() == null) {
            return;
        }
        this.startShield(SHIELD_TICKS);
        this.setAbilityCooldown(SHIELD_COOLDOWN);
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.HOSTILE, 1.6F, 1.5F);
    }

    @Override
    protected void clientTick() {
        if (!this.isShielded()) {
            return;
        }
        this.getWorld().addParticle(ParticleTypes.END_ROD,
                this.getX() + (this.random.nextDouble() - 0.5D) * this.getWidth(),
                this.getY() + this.random.nextDouble() * this.getHeight(),
                this.getZ() + (this.random.nextDouble() - 0.5D) * this.getWidth(), 0.0D, 0.02D, 0.0D);
    }
}
