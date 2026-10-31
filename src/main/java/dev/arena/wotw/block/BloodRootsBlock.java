package dev.arena.wotw.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

/**
 * Blood roots: the thick, spore bearing cousin of the red roots.
 *
 * <p>Stand on it and the spores swallow the light — the player gets the Darkness effect for as long
 * as he stays on the vines, and three seconds after stepping off it fades on its own. It also
 * throws spore particles into the air and creeps to neighbouring tiles.</p>
 */
public class BloodRootsBlock extends Block {

    private static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D);

    public BloodRootsBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isOpaque();
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighbor,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !this.canPlaceAt(state, world, pos)) {
            return net.minecraft.block.Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighbor, world, pos, neighborPos);
    }

    /** The spores: darkness while the player is standing in the vines. */
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient || !(entity instanceof LivingEntity living)) {
            return;
        }
        living.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 60, 0, false, false, false));
        if (entity instanceof PlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 60, 0, false, false, false));
        }
        if (living.age % 10 == 0 && world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.ASH, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    4, 0.4D, 0.2D, 0.4D, 0.01D);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(6) != 0) {
            return;
        }
        world.addParticle(ParticleTypes.CRIMSON_SPORE,
                pos.getX() + random.nextDouble(), pos.getY() + 0.4D + random.nextDouble() * 0.4D,
                pos.getZ() + random.nextDouble(), 0.0D, 0.01D, 0.0D);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() > 0.10F) {
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

    /** Helper used by capsules and structures: plant a vine if the spot allows it. */
    public static boolean plant(World world, BlockPos pos) {
        if (!world.getBlockState(pos).isAir() || !world.getBlockState(pos.down()).isOpaque()) {
            return false;
        }
        world.setBlockState(pos, dev.arena.wotw.registry.ModBlocks.BLOOD_ROOTS.getDefaultState());
        return true;
    }
}
