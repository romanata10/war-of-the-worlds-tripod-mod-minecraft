package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * The machine from <i>War of the Worlds: The Attack</i>: a bulbous head studded with red eyes and
 * hung with a curtain of tentacles, on three very tall legs.
 *
 * <p><b>Signature ability</b>: it <b>deploys drones</b>. The tentacles drop small walkers out of the
 * head — up to three at a time — and those drones go looking for the player on their own and blow
 * themselves up when they find one.</p>
 */
public class AttackTripodEntity extends AbstractTripodEntity {

    private static final int DEPLOY_INTERVAL = 300;
    private static final int MAX_DRONES = 3;

    public AttackTripodEntity(EntityType<? extends AttackTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(300.0D, 0.24D, 12.0D, 12.0D, 8.0D, 0.8D, 48.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.HEAT_RAY, 7.0F, 34.0D, 20, 110));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(DEPLOY_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }
        int nearby = this.level().getEntitiesOfClass(DroneEntity.class, this.getBoundingBox().inflate(24.0D)).size();
        if (nearby >= MAX_DRONES) {
            return;
        }
        DroneEntity drone = ModEntities.DRONE.create(this.level());
        if (drone == null) {
            return;
        }
        double x = this.getX() + (this.random.nextDouble() - 0.5D) * 3.0D;
        double z = this.getZ() + (this.random.nextDouble() - 0.5D) * 3.0D;
        drone.moveTo(x, this.getY(), z, this.random.nextFloat() * 360.0F, 0.0F);
        drone.setTarget(target);
        this.level().addFreshEntity(drone);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.POOF, x, this.getY() + 0.4D, z, 14, 0.5D, 0.4D, 0.5D, 0.02D);
            server.playSound(null, x, this.getY(), z, SoundEvents.SHULKER_BULLET_HURT, SoundSource.HOSTILE,
                    1.6F, 0.7F);
        }
    }
}
