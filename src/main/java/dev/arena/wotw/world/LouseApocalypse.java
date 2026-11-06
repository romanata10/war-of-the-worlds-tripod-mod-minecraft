package dev.arena.wotw.world;

import dev.arena.wotw.WotwMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * The louse apocalypse switch — one flag per world, saved with the world itself.
 *
 * <p>Martian lice breed on their own, and if nothing stops them a single capsule ends with a field
 * covered in four thousand of them. That is funny exactly once per server, so the swarm is
 * <b>switched off</b> when a world is created and the players turn it on themselves with
 * {@code /wotw apocalypse on} when they want to watch the world end.</p>
 *
 * <p>The flag lives in the overworld's data storage: it is per world, it survives a restart and it
 * does not leak into other saved games the way a config file would.</p>
 */
public class LouseApocalypse extends SavedData {

    private static final String DATA_NAME = "wotw_louse_apocalypse";

    /** New worlds start with the swarm asleep. */
    private boolean enabled;

    public static LouseApocalypse load(CompoundTag tag) {
        LouseApocalypse data = new LouseApocalypse();
        data.enabled = tag.getBoolean("LouseApocalypse");
        return data;
    }

    public static LouseApocalypse get(ServerLevel level) {
        return level.getServer().overworld()
                .getDataStorage()
                .computeIfAbsent(LouseApocalypse::load, LouseApocalypse::new, DATA_NAME);
    }

    /** True when lice are allowed to breed; safe to call from either side. */
    public static boolean isEnabled(Level level) {
        return level instanceof ServerLevel serverLevel && get(serverLevel).enabled;
    }

    /** Turns the swarm on or off and returns the new state. */
    public static boolean set(ServerLevel level, boolean value) {
        LouseApocalypse data = get(level);
        data.enabled = value;
        data.setDirty();
        WotwMod.LOGGER.info("The louse apocalypse is now {}", value ? "on" : "off");
        return value;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putBoolean("LouseApocalypse", this.enabled);
        return tag;
    }
}
