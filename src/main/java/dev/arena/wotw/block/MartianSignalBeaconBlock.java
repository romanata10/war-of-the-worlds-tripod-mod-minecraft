package dev.arena.wotw.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * The Martian signal beacon: a pylon that calls the invasion in.
 *
 * <p>While it stands it pulses a beacon of light into the sky, keeps every Martian machine around it
 * patched up with regeneration, and — the part that matters — every half minute it <b>summons a
 * machine</b> out of thin air if a player is nearby. Break the pylon or the hill it stands on never
 * gets quiet.</p>
 */
public class MartianSignalBeaconBlock extends Block implements EntityBlock {

    public MartianSignalBeaconBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MartianSignalBeaconBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state,
                                                                  BlockEntityType<T> type) {
        if (world.isClientSide) {
            return null;
        }
        return (tickWorld, pos, tickState, blockEntity) -> {
            if (blockEntity instanceof MartianSignalBeaconBlockEntity beacon) {
                beacon.tick();
            }
        };
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        for (int i = 0; i < 2; i++) {
            world.addParticle(ParticleTypes.END_ROD,
                    pos.getX() + random.nextDouble(),
                    pos.getY() + 1.0D + random.nextDouble() * 2.0D,
                    pos.getZ() + random.nextDouble(), 0.0D, 0.04D, 0.0D);
        }
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moved) {
        super.onPlace(state, world, pos, oldState, moved);
        if (!world.isClientSide) {
            world.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.4F, 1.2F);
        }
    }

    /** Called by the block entity when it brings another machine through. */
    static void flash(ServerLevel world, BlockPos pos) {
        world.sendParticles(ParticleTypes.PORTAL, pos.getX() + 0.5D, pos.getY() + 1.2D, pos.getZ() + 0.5D,
                60, 1.4D, 1.6D, 1.4D, 0.4D);
        world.playSound(null, pos, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 1.6F, 0.8F);
    }
}
