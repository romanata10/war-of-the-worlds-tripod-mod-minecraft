package dev.arena.wotw.block;

import dev.arena.wotw.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Root spreader: a squat seed pod that keeps sowing the red weed. Every four seconds it grows a
 * couple of roots on the soil around it, so a Martian base slowly turns into a red garden.
 */
public class RootSpreaderBlockEntity extends AbstractMartianDeviceBlockEntity {

    private static final int RADIUS = 5;

    public RootSpreaderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROOT_SPREADER, pos, state, 80);
    }

    @Override
    protected void pulse(Level world) {
        int grown = 0;
        for (int attempt = 0; attempt < 12 && grown < 2; attempt++) {
            int dx = world.random.nextInt(RADIUS * 2 + 1) - RADIUS;
            int dz = world.random.nextInt(RADIUS * 2 + 1) - RADIUS;
            BlockPos target = this.worldPosition.offset(dx, 0, dz);
            target = this.ground(world, target);
            if (target == null) {
                continue;
            }
            if (RedRootBlock.plant(world, target)) {
                grown++;
                if (world instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.CRIMSON_SPORE, target.getX() + 0.5D,
                            target.getY() + 0.2D, target.getZ() + 0.5D, 6, 0.3D, 0.2D, 0.3D, 0.0D);
                }
            }
        }
        if (grown > 0) {
            world.playSound(null, this.worldPosition, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS,
                    0.8F, 0.6F);
        }
    }

    /** Finds the first free spot above the ground under `pos`, or null when the soil is wrong. */
    private BlockPos ground(Level world, BlockPos pos) {
        for (int dy = 1; dy >= -2; dy--) {
            BlockPos soil = pos.offset(0, dy, 0);
            BlockState state = world.getBlockState(soil);
            if (state.is(BlockTags.DIRT) || state.is(BlockTags.SAND)) {
                BlockPos above = soil.above();
                if (world.getBlockState(above).isAir()
                        && world.getBlockState(above).getFluidState().isEmpty()) {
                    return above;
                }
                return null;
            }
        }
        return null;
    }
}
