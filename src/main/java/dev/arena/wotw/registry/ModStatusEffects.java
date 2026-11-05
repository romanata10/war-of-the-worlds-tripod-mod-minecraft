package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.effect.CagedEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/** Status effects of the mod. For now just one: the tag of a caged prisoner. */
public final class ModStatusEffects {

    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, WotwMod.MOD_ID);

    public static final MobEffect CAGED = new CagedEffect();

    static {
        EFFECTS.register("caged", () -> CAGED);
    }

    private ModStatusEffects() {
    }

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
        WotwMod.LOGGER.debug("Registered status effects");
    }
}
