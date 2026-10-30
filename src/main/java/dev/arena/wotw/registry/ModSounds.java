package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

/**
 * Custom sounds, all synthesised by {@code tools/generate_sounds.py}:
 * the tripod siren, the heat ray zap and the machine's footstep.
 */
public final class ModSounds {

    /** The iconic wailing horn. */
    public static final SoundEvent TRIPOD_HORN = register("entity.tripod.horn", 96.0F);
    /** A very heavy mechanical footstep. */
    public static final SoundEvent TRIPOD_STEP = register("entity.tripod.step", 64.0F);
    /** Heat ray / laser discharge. */
    public static final SoundEvent HEAT_RAY = register("entity.tripod.heat_ray", 128.0F);

    private ModSounds() {
    }

    private static SoundEvent register(String id, float distanceToTravel) {
        Identifier identifier = WotwMod.id(id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier, distanceToTravel));
    }

    public static void register() {
        WotwMod.LOGGER.debug("Registered custom tripod sounds");
    }
}
