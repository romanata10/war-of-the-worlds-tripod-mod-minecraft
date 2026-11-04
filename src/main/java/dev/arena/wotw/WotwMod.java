package dev.arena.wotw;

import dev.arena.wotw.registry.ModBlockEntities;
import dev.arena.wotw.registry.ModBlocks;
import dev.arena.wotw.registry.ModEntities;
import dev.arena.wotw.registry.ModItems;
import dev.arena.wotw.registry.ModSounds;
import dev.arena.wotw.registry.ModSpawns;
import dev.arena.wotw.registry.ModStatusEffects;
import dev.arena.wotw.world.NaturalSpawnsBiomeModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point of the mod.
 *
 * <p>Registration order matters: the entity types come first (the spawn eggs point at them), then
 * the blocks (the block items are built in {@link ModItems}), then the items and the effects. The
 * event driven half of the mod — attributes, spawn rules, the invasion clock and the caged death —
 * lives in {@link ForgeEvents} and in the two world classes, which are event subscribers
 * themselves.</p>
 */
@Mod(WotwMod.MOD_ID)
public class WotwMod {

    public static final String MOD_ID = "wotw";
    public static final Logger LOGGER = LoggerFactory.getLogger("War of the Worlds");

    public WotwMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ForgeConfigSpec spec = WotwConfig.SPEC;
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec);

        ModSounds.register(bus);
        ModBlockEntities.register(bus);
        ModEntities.register(bus);
        ModBlocks.register(bus);
        ModItems.register(bus);
        ModStatusEffects.register(bus);
        NaturalSpawnsBiomeModifier.register(bus);
        ModSpawns.register();

        bus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("The Martians have landed. {} tripod types are ready to walk the Overworld.",
                ModEntities.all().size());
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
