package dev.arena.wotw.block;

import dev.arena.wotw.world.MarsDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * The rocket: a two block tall launcher that carries whoever right clicks it to Mars, and back again
 * from Mars.
 *
 * <p>Both halves are the same block, told apart by the {@code top} property, so the rocket is one
 * blockstate with two models instead of two blocks that could get out of step. Breaking either half
 * breaks the pair: a rocket standing on one block in the air is an invitation to a crash.</p>
 */
public class RocketBlock extends Block {

    public static final BooleanProperty TOP = BooleanProperty.create("top");

    private static final VoxelShape LOWER = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape UPPER = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public RocketBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TOP, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TOP);
    }

    public static boolean isTop(BlockState state) {
        return state.getValue(TOP);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return isTop(state) ? UPPER : LOWER;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moved) {
        if (level.isClientSide || isTop(state)) {
            return;
        }
        BlockPos above = pos.above();
        if (level.getBlockState(above).canBeReplaced()) {
            level.setBlock(above, this.defaultBlockState().setValue(TOP, Boolean.TRUE), 3);
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            BlockPos other = isTop(state) ? pos.below() : pos.above();
            BlockState neighbour = level.getBlockState(other);
            if (neighbour.is(this)) {
                level.removeBlock(other, false);
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (!(player instanceof ServerPlayer serverPlayer) || !(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        }
        if (player.isPassenger()) {
            player.stopRiding();
        }
        boolean returning = MarsDimension.isMars(serverLevel);
        if (!MarsDimension.travel(serverPlayer)) {
            return InteractionResult.FAIL;
        }
        serverLevel.playSound(null, pos, SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.BLOCKS, 2.0F,
                returning ? 0.8F : 1.2F);
        serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX() + 0.5D, pos.getY(),
                pos.getZ() + 0.5D, 30, 0.4D, 0.4D, 0.4D, 0.02D);
        return InteractionResult.CONSUME;
    }
}
