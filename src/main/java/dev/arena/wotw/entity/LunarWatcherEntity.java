package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.HoverGoal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * The watcher: a saucer with a green rim and a skirt of tentacles, hanging over the country.
 *
 * <p><b>Signature ability</b>: the <b>scan</b> — it holds a beam on its target for as long as it can
 * see it, and everything the beam touches catches fire.</p>
 */
public class LunarWatcherEntity extends AbstractLunarMachineEntity {

    private static final int SCAN_INTERVAL = 65;
    private static final double SCAN_RANGE = 44.0D;

    public LunarWatcherEntity(EntityType<? extends LunarWatcherEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(210.0D, 0.30D, 11.0D, 6.0D, 4.0D, 0.6D, 60.0D);
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    protected void addExtraGoals() {
        super.addExtraGoals();
        this.goalSelector.addGoal(3, new HoverGoal(this, 16.0D, 18.0D, 1.0D));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(SCAN_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > SCAN_RANGE * SCAN_RANGE
                || !this.hasLineOfSight(target)) {
            return;
        }
        this.fireLunarLaser(target, 7.0F);
        if (this.level() instanceof ServerLevel server) {
            Vec3 from = this.getBeamOrigin();
            Vec3 step = target.getEyePosition().subtract(from).scale(1.0D / 18.0D);
            for (int index = 0; index < 18; index++) {
                Vec3 point = from.add(step.scale(index));
                server.sendParticles(ParticleTypes.END_ROD, point.x, point.y, point.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void clientTick() {
        super.clientTick();
        if (this.random.nextInt(2) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.END_ROD,
                this.getX() + (this.random.nextDouble() - 0.5D) * 2.4D,
                this.getY() + 0.4D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 2.4D, 0.0D, -0.03D, 0.0D);
    }
}
