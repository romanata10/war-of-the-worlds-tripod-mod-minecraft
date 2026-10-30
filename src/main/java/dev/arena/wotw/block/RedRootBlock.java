package dev.arena.wotw.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

/**
 * The red roots a tripod leaves in its wake.
 *
 * <p>They are a creeping Martian weed: a low cross-shaped plant that glows faintly in the dark and
 * dies when the ground under it is removed. Tripods that still carry a seed bed (see
 * {@link dev.arena.wotw.entity.AbstractTripodEntity#setRootTrail}) drop them while walking.</p>
 */
public class RedRootBlock extends Block {

    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public RedRootBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isOpaque();
    }

    @Override
    public boolean canPlaceAt(ItemPlacementContext context) {
        return context.getWorld().getBlockState(context.getBlockPos().down()).isOpaque();
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighbor,
                                                net.minecraft.world.WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !this.canPlaceAt(state, world, pos)) {
            return net.minecraft.block.Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighbor, world, pos, neighborPos);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // the weed creeps to a neighbouring tile now and then, so a trail turns into a thicket
        if (random.nextFloat() > 0.08F) {
            return;
        }
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos target = pos.offset(direction);
            if (world.getBlockState(target).isAir() && world.getBlockState(target.down()).isOpaque()) {
                world.setBlockState(target, this.getDefaultState());
                return;
            }
        }
    }

    /** Small helper for callers that want to plant a root without a placement context. */
    public static boolean plant(World world, BlockPos pos) {
        if (!world.getBlockState(pos).isAir()) {
            return false;
        }
        if (!world.getBlockState(pos.down()).isOpaque()) {
            return false;
        }
        world.setBlockState(pos, dev.arena.wotw.registry.ModBlocks.RED_ROOT.getDefaultState());
        return true;
    }
}
