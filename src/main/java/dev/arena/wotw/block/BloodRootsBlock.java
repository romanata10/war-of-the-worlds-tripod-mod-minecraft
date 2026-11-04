package dev.arena.wotw.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

/**
 * Blood roots: the thick, spore bearing cousin of the red roots.
 *
 * <p>Stand on it and the spores swallow the light — the player gets the Darkness effect for as long
 * as he stays on the vines, and three seconds after stepping off it fades on its own. It also
 * throws spore particles into the air and creeps to neighbouring tiles.</p>
 */
public class BloodRootsBlock extends Block {

    private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D);

    public BloodRootsBlock(BlockBehaviour.Properties settings) {
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
                                                LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !this.canSurvive(state, world, pos)) {
            return net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighbor, world, pos, neighborPos);
    }

    /** The spores: darkness while the player is standing in the vines. */
    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (world.isClientSide || !(entity instanceof LivingEntity living)) {
            return;
        }
        living.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 0, false, false, false));
        if (entity instanceof Player player) {
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 0, false, false, false));
        }
        if (living.age % 10 == 0 && world instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.ASH, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    4, 0.4D, 0.2D, 0.4D, 0.01D);
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(6) != 0) {
            return;
        }
        world.addParticle(ParticleTypes.CRIMSON_SPORE,
                pos.getX() + random.nextDouble(), pos.getY() + 0.4D + random.nextDouble() * 0.4D,
                pos.getZ() + random.nextDouble(), 0.0D, 0.01D, 0.0D);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() > 0.10F) {
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

    /** Helper used by capsules and structures: plant a vine if the spot allows it. */
    public static boolean plant(Level world, BlockPos pos) {
        if (!world.getBlockState(pos).isAir() || !world.getBlockState(pos.down()).canOcclude()) {
            return false;
        }
        world.setBlock(pos, dev.arena.wotw.registry.ModBlocks.BLOOD_ROOTS.defaultBlockState());
        return true;
    }
}
