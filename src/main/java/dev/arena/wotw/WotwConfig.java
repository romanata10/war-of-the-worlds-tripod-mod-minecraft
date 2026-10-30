package dev.arena.wotw;

/**
 * Tiny "config": system properties instead of a full config library, so the mod stays dependency
 * free. Examples: {@code -Dwotw.naturalSpawns=false -Dwotw.smallTripodsOnly=true}.
 */
public final class WotwConfig {

    /** Tripods spawning naturally in the Overworld. */
    public static final boolean NATURAL_SPAWNS = !Boolean.getBoolean("wotw.naturalSpawnsDisabled");

    /** When true only the small machines (louse, spider) spawn naturally. */
    public static final boolean SMALL_TRIPODS_ONLY = Boolean.getBoolean("wotw.smallTripodsOnly");

    private WotwConfig() {
    }
}
