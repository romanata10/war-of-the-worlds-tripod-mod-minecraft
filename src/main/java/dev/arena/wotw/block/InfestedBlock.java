package dev.arena.wotw.block;

import dev.arena.wotw.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

/**
 * Infested cobblestone and bricks: the Martian roots have got into the masonry and burst out of it.
 *
 * <p>Roots and vines crawl out of the block over time, so ruins that were touched by the Martians
 * slowly turn into a thicket.</p>
 */
public class InfestedBlock extends Block {

    public InfestedBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() > 0.08F) {
            return;
        }
        BlockPos above = pos.up();
        if (world.getBlockState(above).isAir()) {
            world.setBlockState(above, (random.nextBoolean() ? ModBlocks.RED_ROOT : ModBlocks.BLOOD_ROOTS)
                    .getDefaultState());
            return;
        }
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos target = pos.offset(direction);
            if (world.getBlockState(target).isAir() && world.getBlockState(target.down()).isOpaque()) {
                world.setBlockState(target, ModBlocks.BLOOD_ROOTS.getDefaultState());
                return;
            }
        }
    }
}
