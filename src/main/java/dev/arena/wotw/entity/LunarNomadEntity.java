package dev.arena.wotw.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The nomad of the Moon: a saucer with a domed top, a ring of green portholes and six legs.
 *
 * <p><b>Signature ability</b>: it <b>wanders</b> — the nomad ignores walls of a fight it cannot see
 * and roams far from where it landed, sweeping the laser across whatever it meets.</p>
 */
public class LunarNomadEntity extends AbstractLunarMachineEntity {

    private static final int LASER_INTERVAL = 95;
    private static final double LASER_RANGE = 38.0D;

    public LunarNomadEntity(EntityType<? extends LunarNomadEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(240.0D, 0.31D, 12.0D, 7.0D, 5.0D, 0.8D, 58.0D);
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
        this.fireLunarLaser(target, 8.0F);
    }
}
