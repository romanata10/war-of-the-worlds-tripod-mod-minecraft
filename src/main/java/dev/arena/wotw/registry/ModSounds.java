package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;

/**
 * Custom sounds, all synthesised by {@code tools/generate_sounds.py}:
 * the tripod siren, the heat ray zap and the machine's footstep.
 */
public final class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WotwMod.MOD_ID);

    /** The iconic wailing horn. */
    public static final SoundEvent TRIPOD_HORN = register("entity.tripod.horn", 96.0F);
    /** A very heavy mechanical footstep. */
    public static final SoundEvent TRIPOD_STEP = register("entity.tripod.step", 64.0F);
    /** Heat ray / laser discharge. */
    public static final SoundEvent HEAT_RAY = register("entity.tripod.heat_ray", 128.0F);

    private ModSounds() {
    }

    private static SoundEvent register(String id, float distanceToTravel) {
        ResourceLocation identifier = WotwMod.id(id);
        SoundEvent sound = SoundEvent.createVariableRangeEvent(identifier);
        SOUNDS.register(id, () -> sound);
        return sound;
    }

    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
        WotwMod.LOGGER.debug("Registered custom tripod sounds");
    }
}
