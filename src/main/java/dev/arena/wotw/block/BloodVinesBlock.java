package dev.arena.wotw.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/**
 * The tall red vines: same spores and the same creeping growth as the blood roots, but they stand a
 * full block high, which makes a thicket look like a wall of red when it fills a ruin.
 */
public class BloodVinesBlock extends BloodRootsBlock {

    private static final VoxelShape TALL_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public BloodVinesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return TALL_SHAPE;
    }
}
