package dev.arena.wotw.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * Shared plumbing of the five working Martian devices: a tick counter, a save/load round trip and a
 * small helper for the area of effect. Each device only has to describe what happens on its tick.
 */
public abstract class AbstractMartianDeviceBlockEntity extends BlockEntity {

    /** Ticks until the next pulse. Devices start half charged, so they act soon after placement. */
    protected final int period;

    private int timer;

    protected AbstractMartianDeviceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state,
                                               int period) {
        super(type, pos, state);
        this.period = period;
        this.timer = period / 2;
    }

    /** Called from the block's ticker once per game tick on the server. */
    public void tick() {
        Level world = this.getLevel();
        if (world == null || world.isClientSide) {
            return;
        }
        if (this.timer > 0) {
            this.timer--;
            return;
        }
        this.timer = this.period;
        this.pulse(world);
    }

    /** The actual work of one pulse. */
    protected abstract void pulse(Level world);

    /** Area of effect around the block. */
    protected AABB area(double radius) {
        return new AABB(this.worldPosition).inflate(radius);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("Timer", this.timer);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.timer = nbt.getInt("Timer");
    }
}
