package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * The machine of the 1998 series: a wide blue-steel mantle around a burning orange core, on three very
 * long legs.
 *
 * <p><b>Signature ability</b>: the core fires a <b>piercing beam</b>. The shot goes clean through the
 * first thing it meets and burns everything standing in the line behind it, so hiding in a crowd does
 * not help.</p>
 */
public class Series1998TripodEntity extends AbstractTripodEntity {

    private static final int SWEEP_COOLDOWN = 160;

    public Series1998TripodEntity(EntityType<? extends Series1998TripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(240.0D, 0.27D, 11.0D, 10.0D, 7.0D, 0.85D, 46.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.LANCE, 9.0F, 46.0D, 14, 60));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        LivingEntity target = this.getTarget();
        if (target == null || !this.isAbilityReady()) {
            return;
        }
        this.setAbilityCooldown(SWEEP_COOLDOWN);
        this.fireBeamAt(target, BeamKind.LANCE, 9.0F);

        // everything standing on the line behind the first target is speared as well
        Vec3 origin = this.getBeamOrigin();
        Vec3 direction = target.getEyePosition().subtract(origin).normalize();
        for (int step = 1; step <= 18; step++) {
            Vec3 point = origin.add(direction.scale(step * 2.0D));
            AABB slice = new AABB(point.x - 1.2D, point.y - 1.6D, point.z - 1.2D,
                    point.x + 1.2D, point.y + 1.6D, point.z + 1.2D);
            for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, slice,
                    entity -> entity != this && entity != target && !(entity instanceof AbstractTripodEntity))) {
                victim.hurt(this.damageSources().mobAttack(this), 4.0F);
                victim.setSecondsOnFire(3);
            }
        }
    }
}
