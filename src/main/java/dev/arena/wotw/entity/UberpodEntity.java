package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;

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

    public UberpodEntity(EntityType<? extends UberpodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
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
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 1.6F, 1.5F);
    }

    @Override
    protected void clientTick() {
        if (!this.isShielded()) {
            return;
        }
        this.level().addParticle(ParticleTypes.END_ROD,
                this.getX() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(),
                this.getY() + this.random.nextDouble() * this.getBbHeight(),
                this.getZ() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(), 0.0D, 0.02D, 0.0D);
    }
}
