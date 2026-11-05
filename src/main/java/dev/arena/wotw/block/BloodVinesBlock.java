package dev.arena.wotw.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

/**
 * The tall red vines: same spores and the same creeping growth as the blood roots, but they stand a
 * full block high, which makes a thicket look like a wall of red when it fills a ruin.
 */
public class BloodVinesBlock extends BloodRootsBlock {

    private static final VoxelShape TALL_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public BloodVinesBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return TALL_SHAPE;
    }
}
