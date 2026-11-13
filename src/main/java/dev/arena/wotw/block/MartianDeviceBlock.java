package dev.arena.wotw.block;

import net.minecraft.core.BlockPos;
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
 * One block class for the five working Martian devices. The device is chosen with the constructor
 * and decides which block entity is built and which pulse it runs.
 */
public class MartianDeviceBlock extends Block implements EntityBlock {

    public enum Device {
        /** Throws arcing shells at anything that walks up to the base. */
        MORTAR,
        /** Sows red roots over the surrounding soil. */
        ROOT_SPREADER,
        /** Drags everything in the air towards itself and upwards. */
        GRAVITY_WELL,
        /** Welds Martian machines back together. */
        REPAIR_POD,
        /** Wraps nearby machines in a shimmering shield. */
        SHIELD_PYLON
    }

    private final Device device;

    public MartianDeviceBlock(BlockBehaviour.Properties settings, Device device) {
        super(settings);
        this.device = device;
    }

    public Device device() {
        return this.device;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return switch (this.device) {
            case MORTAR -> new MartianMortarBlockEntity(pos, state);
            case ROOT_SPREADER -> new RootSpreaderBlockEntity(pos, state);
            case GRAVITY_WELL -> new GravityWellBlockEntity(pos, state);
            case REPAIR_POD -> new RepairPodBlockEntity(pos, state);
            case SHIELD_PYLON -> new ShieldPylonBlockEntity(pos, state);
        };
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state,
                                                                  BlockEntityType<T> type) {
        if (world.isClientSide) {
            return null;
        }
        return (tickWorld, pos, tickState, blockEntity) -> {
            if (blockEntity instanceof AbstractMartianDeviceBlockEntity deviceEntity) {
                deviceEntity.tick();
            }
        };
    }
}
