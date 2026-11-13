package dev.arena.wotw.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The Martian mining machine: a red hull bent forward over a bucket of iron teeth, with a drill cone
 * under the chin and two exhaust stacks.
 *
 * <p><b>Signature ability</b>: it <b>tears up the ground</b>. Every few seconds it drives its bucket
 * into the soil under its prey, shredding soft blocks and showering the victim with crushed rock
 * that slows it down. Running away over dirt does not work — it simply digs the floor out from
 * under your feet.</p>
 */
public class MiningTripodEntity extends AbstractTripodEntity {

    private static final int DIG_INTERVAL = 140;
    private static final double DIG_RANGE = 14.0D;

    public MiningTripodEntity(EntityType<? extends MiningTripodEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return tripodAttributes(300.0D, 0.30D, 15.0D, 10.0D, 6.0D, 0.85D, 48.0D);
    }

    @Override
    protected void addExtraGoals() {
        // no beam at all: this machine works with its bucket
    }

    @Override
    protected void serverTick() {
        super.serverTick();
        if (!this.onIntervalZero(DIG_INTERVAL)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || this.distanceToSqr(target) > DIG_RANGE * DIG_RANGE) {
            return;
        }
        BlockPos feet = target.blockPosition();
        int broken = 0;
        for (int dx = -2; dx <= 2 && broken < 8; dx++) {
            for (int dz = -2; dz <= 2 && broken < 8; dz++) {
                for (int dy = 0; dy <= 1 && broken < 8; dy++) {
                    BlockPos pos = feet.offset(dx, -dy, dz);
                    BlockState state = this.level().getBlockState(pos);
                    if (state.isAir()) {
                        continue;
                    }
                    float hardness = state.getDestroySpeed(this.level(), pos);
                    if (hardness < 0.0F || hardness > 3.0F) {
                        continue;
                    }
                    if (this.level() instanceof ServerLevel server) {
                        server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
                                pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                                6, 0.3D, 0.3D, 0.3D, 0.05D);
                    }
                    this.level().destroyBlock(pos, false);
                    broken++;
                }
            }
        }
        if (broken > 0) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1, false, true, true));
            target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 1, false, true, true));
            this.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.GRAVEL_BREAK, SoundSource.HOSTILE, 1.8F, 0.6F);
        }
    }

    @Override
    protected void onMeleeHit(LivingEntity victim) {
        victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 0, false, true, true));
    }
}
