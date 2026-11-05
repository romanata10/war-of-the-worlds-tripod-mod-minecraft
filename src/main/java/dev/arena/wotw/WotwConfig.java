package dev.arena.wotw;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

/**
 * The switches of the mod, as a real Forge config file ({@code config/wotw-common.toml}).
 *
 * <p>The system properties still work and act as the defaults, so
 * {@code -Dwotw.smallTripodsOnly=true} keeps doing what it always did; the config file then
 * overrides them once it is loaded.</p>
 */
public final class WotwConfig {

    public static final ForgeConfigSpec SPEC;

    private static final ForgeConfigSpec.BooleanValue NATURAL_SPAWNS_VALUE;
    private static final ForgeConfigSpec.BooleanValue INVASION_EVENTS_VALUE;
    private static final ForgeConfigSpec.BooleanValue SMALL_TRIPODS_ONLY_VALUE;

    /** Tripods spawning naturally in the Overworld. */
    public static volatile boolean NATURAL_SPAWNS = !Boolean.getBoolean("wotw.naturalSpawnsDisabled");

    /** Capsules falling out of the sky: the first wave and every wave after it. */
    public static volatile boolean INVASION_EVENTS = !Boolean.getBoolean("wotw.invasionEventsDisabled");

    /** When true only the small machines (louse, spider) spawn naturally. */
    public static volatile boolean SMALL_TRIPODS_ONLY = Boolean.getBoolean("wotw.smallTripodsOnly");

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("spawning");
        NATURAL_SPAWNS_VALUE = builder
                .comment("Tripods spawn naturally in the Overworld.")
                .define("naturalSpawns", NATURAL_SPAWNS);
        SMALL_TRIPODS_ONLY_VALUE = builder
                .comment("Only the small machines (lice and spiders) spawn; the big walkers then",
                        "come from capsules and structures alone.")
                .define("smallTripodsOnly", SMALL_TRIPODS_ONLY);
        builder.pop();
        builder.push("invasion");
        INVASION_EVENTS_VALUE = builder
                .comment("Capsules fall out of the sky: the first one shortly after a player joins,",
                        "another every seven minutes after that.")
                .define("invasionEvents", INVASION_EVENTS);
        builder.pop();
        SPEC = builder.build();
    }

    private WotwConfig() {
    }

    /** Re-reads the config file. Called from {@link ForgeEvents} when Forge loads or reloads it. */
    public static void reload(ModConfigEvent event) {
        if (!SPEC.isLoaded() || event.getConfig().getSpec() != SPEC) {
            return;
        }
        NATURAL_SPAWNS = NATURAL_SPAWNS_VALUE.get();
        INVASION_EVENTS = INVASION_EVENTS_VALUE.get();
        SMALL_TRIPODS_ONLY = SMALL_TRIPODS_ONLY_VALUE.get();
        WotwMod.LOGGER.info("Config loaded: natural spawns {}, small tripods only {}, invasion events {}",
                NATURAL_SPAWNS, SMALL_TRIPODS_ONLY, INVASION_EVENTS);
    }
}
