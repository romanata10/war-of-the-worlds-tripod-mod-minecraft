package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The 2021 machine: a narrow black torso under wide cape plates with a red core burning in its
 * chest, on two raptor legs and a lashing tail.
 *
 * <p><b>Signature ability</b>: the <b>cape</b>. Farther than eighteen blocks from its prey it fades
 * out of sight completely — no outline, no name, only the red core — and it drops the cloak only
 * when it is close enough to strike.</p>
 */
public class Attack2021TripodEntity extends AbstractTripodEntity {

    /** Distance at which the cloak falls away. */
    private static final double CLOAK_RANGE = 18.0D;

    public Attack2021TripodEntity(EntityType<? extends Attack2021TripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(260.0D, 0.34D, 14.0D, 8.0D, 6.0D, 0.7D, 46.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 8.0F, 28.0D, 16, 80));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        LivingEntity target = this.getTarget();
        boolean shouldHide = target == null || this.distanceToSqr(target) > CLOAK_RANGE * CLOAK_RANGE;
        boolean hidden = this.hasEffect(MobEffects.INVISIBILITY);
        if (shouldHide && !hidden) {
            this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 120, 0, false, false, false));
        } else if (!shouldHide && hidden) {
            this.removeEffect(MobEffects.INVISIBILITY);
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(2) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.SMOKE,
                this.getX() + (this.random.nextDouble() - 0.5D) * 2.0D,
                this.getY() + this.getBbHeight() * 0.55D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 2.0D, 0.0D, 0.01D, 0.0D);
    }
}
