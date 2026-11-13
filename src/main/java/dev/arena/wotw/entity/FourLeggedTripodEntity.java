package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.ai.StompGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The four legged machine: a copper saucer with two yellow eyes and a horn — the only walker in the
 * mod that does not stand on three legs.
 *
 * <p><b>Signature ability</b>: the <b>brace</b>. Four legs let it plant itself: as soon as it stops
 * moving it digs in and becomes almost impossible to shift or to hurt, and from that stance both
 * pairs of legs can come down in a double stomp that shakes everything around it.</p>
 */
public class FourLeggedTripodEntity extends AbstractTripodEntity {

    public FourLeggedTripodEntity(EntityType<? extends FourLeggedTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(340.0D, 0.22D, 16.0D, 18.0D, 12.0D, 1.0D, 46.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new StompGoal(this, 10.0F, 7.0D, 160));
        this.goalSelector.addGoal(3, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 8.0F, 32.0D, 20, 100));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(20)) {
            return;
        }
        boolean planted = this.getDeltaMovement().horizontalDistanceSqr() < 0.0025D;
        if (planted) {
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 1, false, false, false));
        }
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(4) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.CLOUD,
                this.getX() + (this.random.nextDouble() - 0.5D) * 4.0D, this.getY() + 0.2D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 4.0D, 0.0D, 0.0D, 0.0D);
    }
}
