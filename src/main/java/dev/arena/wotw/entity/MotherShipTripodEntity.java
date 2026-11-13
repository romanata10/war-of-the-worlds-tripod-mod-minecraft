package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.ai.HoverGoal;
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
 * The mother ship from the Saddleback cover: a saucer with a glazed cabin band, one articulated arm
 * carrying the ray emitter and a little probe tripod hanging underneath.
 *
 * <p><b>Signature ability</b>: the <b>landing party</b>. Hovering over its prey it opens the hatch in
 * the underside and drops Martian lice, which dig into the ground and chase the player on foot. The
 * saucer itself only shoots from a distance, so the lice are what actually forces a fight.</p>
 */
public class MotherShipTripodEntity extends AbstractTripodEntity {

    /** How often the hatch opens. */
    private static final int DROP_INTERVAL = 320;
    /** Lice it keeps out at once; more would turn the fight into a swarm. */
    private static final int MAX_ESCORTS = 4;

    public MotherShipTripodEntity(EntityType<? extends MotherShipTripodEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(420.0D, 0.28D, 14.0D, 10.0D, 8.0D, 0.9D, 72.0D);
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.LANCE, 9.0F, 44.0D, 30, 90));
        this.goalSelector.addGoal(3, new HoverGoal(this, 14.0D, 12.0D, 0.5D));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(DROP_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }
        int escorts = this.level().getEntitiesOfClass(MartianLouseEntity.class,
                this.getBoundingBox().inflate(28.0D)).size();
        if (escorts >= MAX_ESCORTS) {
            return;
        }
        MartianLouseEntity louse = ModEntities.MARTIAN_LOUSE.create(this.level());
        if (louse == null) {
            return;
        }
        double x = this.getX() + (this.random.nextDouble() - 0.5D) * 4.0D;
        double z = this.getZ() + (this.random.nextDouble() - 0.5D) * 4.0D;
        double y = this.getY() - 1.0D;
        louse.moveTo(x, Math.max(this.level().getMinBuildHeight() + 1, y), z,
                this.random.nextFloat() * 360.0F, 0.0F);
        louse.setTarget(target);
        this.level().addFreshEntity(louse);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CLOUD, x, this.getY() + 0.5D, z, 18, 0.8D, 0.2D, 0.8D, 0.04D);
            server.playSound(null, x, this.getY(), z, SoundEvents.SHULKER_BULLET_HURT, SoundSource.HOSTILE,
                    1.8F, 0.6F);
        }
    }

    @Override
    protected void clientTick() {
        // the rim lights of the saucer
        if (this.random.nextInt(3) != 0) {
            return;
        }
        double angle = this.random.nextDouble() * Math.PI * 2.0D;
        double radius = this.getBbWidth() * 0.45D;
        this.level().addParticle(ParticleTypes.END_ROD,
                this.getX() + Math.cos(angle) * radius,
                this.getY() + this.getBbHeight() * 0.62D,
                this.getZ() + Math.sin(angle) * radius, 0.0D, -0.01D, 0.0D);
    }
}
