package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.ai.HookGrabGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Pendragon's beetle: a hammered golden carapace with a plated mask, four hooked legs and a mane of
 * braided feelers.
 *
 * <p><b>Signature ability</b>: the braided whips. They lash everything around the machine at once,
 * drag the closest victim under the hull, and the machine then finishes it with its short range heat
 * ray.</p>
 */
public class PendragonTripodEntity extends AbstractTripodEntity {

    public PendragonTripodEntity(EntityType<? extends PendragonTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(280.0D, 0.26D, 13.0D, 11.0D, 7.0D, 0.9D, 40.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new HookGrabGoal(this, 20.0D, 9.0F, 150));
        this.goalSelector.addGoal(3, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 7.0F, 26.0D, 20, 100));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(120)) {
            return;
        }
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(8.0D),
                entity -> entity != this && !(entity instanceof AbstractTripodEntity))) {
            victim.hurt(this.damageSources().mobAttack(this), 5.0F);
            Vec3 push = victim.position().subtract(this.position()).normalize().scale(0.6D);
            victim.push(push.x, 0.35D, push.z);
            victim.hurtMarked = true;
            this.level().addParticle(ParticleTypes.CRIT,
                    victim.getX(), victim.getY() + 1.0D, victim.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }
}
