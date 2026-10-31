package dev.arena.wotw.block;

import dev.arena.wotw.registry.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * The tall Martian turret that stands guard around the bases.
 *
 * <p>The block itself is only the pylon; the gun lives in {@link MartianTurretBlockEntity}, which
 * fires a lance beam at any player that comes within range.</p>
 */
public class MartianTurretBlock extends Block implements BlockEntityProvider {

    public MartianTurretBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MartianTurretBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
                                                                  BlockEntityType<T> type) {
        if (world.isClient) {
            return null;
        }
        return (tickWorld, pos, tickState, blockEntity) -> {
            if (blockEntity instanceof MartianTurretBlockEntity turret) {
                turret.tick();
            }
        };
    }
}
