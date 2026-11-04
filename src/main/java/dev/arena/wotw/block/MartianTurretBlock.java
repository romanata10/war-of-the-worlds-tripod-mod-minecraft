package dev.arena.wotw.block;

import dev.arena.wotw.registry.ModBlockEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * The tall Martian turret that stands guard around the bases.
 *
 * <p>The block itself is only the pylon; the gun lives in {@link MartianTurretBlockEntity}, which
 * fires a lance beam at any player that comes within range.</p>
 */
public class MartianTurretBlock extends Block implements BlockEntityProvider {

    public MartianTurretBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MartianTurretBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state,
                                                                  BlockEntityType<T> type) {
        if (world.isClientSide) {
            return null;
        }
        return (tickWorld, pos, tickState, blockEntity) -> {
            if (blockEntity instanceof MartianTurretBlockEntity turret) {
                turret.tick();
            }
        };
    }
}
