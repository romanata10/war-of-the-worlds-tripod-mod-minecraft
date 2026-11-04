package dev.arena.wotw.block;

import dev.arena.wotw.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

/**
 * Infested cobblestone and bricks: the Martian roots have got into the masonry and burst out of it.
 *
 * <p>Roots and vines crawl out of the block over time, so ruins that were touched by the Martians
 * slowly turn into a thicket.</p>
 */
public class InfestedBlock extends Block {

    public InfestedBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() > 0.08F) {
            return;
        }
        BlockPos above = pos.above();
        if (world.getBlockState(above).isAir()) {
            world.setBlock(above, (random.nextBoolean() ? ModBlocks.RED_ROOT : ModBlocks.BLOOD_ROOTS)
                    .defaultBlockState(), 3);
            return;
        }
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos target = pos.relative(direction);
            if (world.getBlockState(target).isAir() && world.getBlockState(target.below()).canOcclude()) {
                world.setBlock(target, ModBlocks.BLOOD_ROOTS.defaultBlockState(), 3);
                return;
            }
        }
    }
}
