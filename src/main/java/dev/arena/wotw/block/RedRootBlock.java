package dev.arena.wotw.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

/**
 * The red roots a tripod leaves in its wake.
 *
 * <p>They are a creeping Martian weed: a low cross-shaped plant that glows faintly in the dark and
 * dies when the ground under it is removed. Tripods that still carry a seed bed (see
 * {@link dev.arena.wotw.entity.AbstractTripodEntity#setRootTrail}) drop them while walking.</p>
 */
public class RedRootBlock extends Block {

    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public RedRootBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.down()).canOcclude();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighbor,
                                                net.minecraft.world.level.LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !this.canSurvive(state, world, pos)) {
            return net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighbor, world, pos, neighborPos);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        // the weed creeps to a neighbouring tile now and then, so a trail turns into a thicket
        if (random.nextFloat() > 0.08F) {
            return;
        }
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos target = pos.relative(direction);
            if (world.getBlockState(target).isAir() && world.getBlockState(target.down()).canOcclude()) {
                world.setBlock(target, this.defaultBlockState());
                return;
            }
        }
    }

    /** Small helper for callers that want to plant a root without a placement context. */
    public static boolean plant(Level world, BlockPos pos) {
        if (!world.getBlockState(pos).isAir()) {
            return false;
        }
        if (!world.getBlockState(pos.down()).canOcclude()) {
            return false;
        }
        world.setBlock(pos, dev.arena.wotw.registry.ModBlocks.RED_ROOT.defaultBlockState());
        return true;
    }
}
