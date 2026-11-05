package dev.arena.wotw.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * One saved flag and one counter: has the first capsule already fallen in this world, and how long
 * ago the last wave started. Stored in the Overworld so it survives restarts.
 */
public class InvasionState extends SavedData {

    /** Key under which the state is stored in the world save. */
    public static final String KEY = "wotw_invasion";

    int ticks;
    boolean firstWaveDone;

    public static InvasionState fromNbt(CompoundTag nbt) {
        InvasionState state = new InvasionState();
        state.ticks = nbt.getInt("Ticks");
        state.firstWaveDone = nbt.getBoolean("FirstWave");
        return state;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putInt("Ticks", this.ticks);
        nbt.putBoolean("FirstWave", this.firstWaveDone);
        return nbt;
    }

    public boolean isFirstWaveDone() {
        return this.firstWaveDone;
    }
}
