package dev.arena.wotw.entity;

import dev.arena.wotw.entity.ai.BeamAttackGoal;
import dev.arena.wotw.entity.projectile.BeamKind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * The storm caller: a tower of copper coils with three prongs and a lightning rod on the crown.
 *
 * <p><b>Signature ability</b>: the <b>strike</b>. It charges its crown until the air crackles, then
 * calls a real bolt of lightning down on whatever it is hunting. The bolt burns, throws the victim
 * into the air, and it only works under open sky — which is the one thing to remember when a storm
 * caller is on the field.</p>
 */
public class StormTripodEntity extends AbstractTripodEntity {

    private static final int STRIKE_INTERVAL = 200;
    private static final double STRIKE_RANGE = 34.0D;

    public StormTripodEntity(EntityType<? extends StormTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(280.0D, 0.30D, 13.0D, 8.0D, 5.0D, 0.8D, 60.0D);
    }

    @Override
    protected void addExtraGoals() {
        this.goalSelector.addGoal(2, new BeamAttackGoal(this, BeamKind.LASER, 6.0F, 30.0D, 20, 90));
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(STRIKE_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > STRIKE_RANGE * STRIKE_RANGE) {
            return;
        }
        if (!server.canSeeSky(target.blockPosition())) {
            // underground the crown cannot reach the sky, so it settles for a crackle of sparks
            server.sendParticles(ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY() + 1.0D,
                    target.getZ(), 24, 0.6D, 0.8D, 0.6D, 0.2D);
            return;
        }
        int surface = server.getHeight(Heightmap.Types.MOTION_BLOCKING, target.getBlockX(), target.getBlockZ());
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(server);
        if (bolt == null) {
            return;
        }
        bolt.moveTo(target.getX(), surface, target.getZ());
        bolt.setCause(null);
        server.addFreshEntity(bolt);
        server.playSound(null, this.getX(), this.getY() + this.getBbHeight(), this.getZ(),
                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, 3.0F, 0.9F);
        server.sendParticles(ParticleTypes.ELECTRIC_SPARK, this.getX(),
                this.getY() + this.getBbHeight() * 0.95D, this.getZ(), 40, 1.0D, 1.4D, 1.0D, 0.4D);
        this.setAbilityCharging(true);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(2) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                this.getX() + (this.random.nextDouble() - 0.5D) * 0.8D,
                this.getY() + this.getBbHeight() * 0.92D + this.random.nextDouble() * 0.8D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 0.8D, 0.0D, 0.02D, 0.0D);
    }
}
