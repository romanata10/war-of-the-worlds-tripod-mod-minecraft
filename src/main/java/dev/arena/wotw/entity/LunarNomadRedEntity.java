package dev.arena.wotw.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The red nomad: the same hull in the other paint scheme, quicker on its six legs and quicker on the
 * trigger.
 */
public class LunarNomadRedEntity extends AbstractLunarMachineEntity {

    private static final int LASER_INTERVAL = 75;
    private static final double LASER_RANGE = 40.0D;

    public LunarNomadRedEntity(EntityType<? extends LunarNomadRedEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(270.0D, 0.35D, 13.0D, 7.0D, 5.0D, 0.8D, 60.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(LASER_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > LASER_RANGE * LASER_RANGE
                || !this.hasLineOfSight(target)) {
            return;
        }
        this.fireLunarLaser(target, 9.0F);
    }
}
