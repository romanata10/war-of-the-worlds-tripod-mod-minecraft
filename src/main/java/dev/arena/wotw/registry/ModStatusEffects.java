package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.effect.CagedEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/** Status effects of the mod. For now just one: the tag of a caged prisoner. */
public final class ModStatusEffects {

    public static final StatusEffect CAGED = Registry.register(Registries.STATUS_EFFECT,
            WotwMod.id("caged"), new CagedEffect());

    private ModStatusEffects() {
    }

    public static void register() {
        WotwMod.LOGGER.debug("Registered status effect {}", Registries.STATUS_EFFECT.getId(CAGED));
    }
}
