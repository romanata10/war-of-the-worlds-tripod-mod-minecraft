package dev.arena.wotw.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The lava machine of the factory floor: a slatted dome over a molten core.
 *
 * <p><b>Signature ability</b>: the <b>molten trail</b>. Where it walks the ground catches fire, and
 * anything that touches the hull is set alight. It does not need to aim: it only needs to keep
 * striding through a village.</p>
 */
public class LavaTripodEntity extends AbstractTripodEntity {

    private static final int TRAIL_INTERVAL = 16;

    public LavaTripodEntity(EntityType<? extends LavaTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(340.0D, 0.27D, 15.0D, 8.0D, 6.0D, 0.9D, 46.0D);
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(TRAIL_INTERVAL) || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        if (this.getTarget() == null || this.random.nextInt(3) != 0) {
            return;
        }
        BlockPos feet = this.blockPosition();
        if (!server.getBlockState(feet).isAir()) {
            return;
        }
        BlockState below = server.getBlockState(feet.below());
        if (!below.canOcclude()) {
            return;
        }
        server.setBlockAndUpdate(feet, Blocks.FIRE.defaultBlockState());
        server.sendParticles(ParticleTypes.FLAME, feet.getX() + 0.5D, feet.getY() + 0.2D,
                feet.getZ() + 0.5D, 8, 0.4D, 0.2D, 0.4D, 0.01D);
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        super.onMeleeHit(victim);
        victim.setSecondsOnFire(8);
    }

    @Override
    protected void clientTick() {
        if (this.random.nextInt(2) != 0) {
            return;
        }
        this.level().addParticle(ParticleTypes.LAVA,
                this.getX() + (this.random.nextDouble() - 0.5D) * 1.8D,
                this.getY() + this.getBbHeight() * 0.35D,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 1.8D, 0.0D, -0.05D, 0.0D);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }
}
