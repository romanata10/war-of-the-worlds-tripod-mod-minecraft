package dev.arena.wotw;

import dev.arena.wotw.registry.ModEntities;
import dev.arena.wotw.registry.ModSpawns;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

/**
 * The three hooks Forge only offers as events: default attributes of every living entity, the spawn
 * placement predicates and "the config file was loaded, re-read the switches".
 */
@Mod.EventBusSubscriber(modid = WotwMod.MOD_ID)
public final class ForgeEvents {

    private ForgeEvents() {
    }

    @SubscribeEvent
    public static void defaultAttributes(EntityAttributeCreationEvent event) {
        ModEntities.registerAttributes(event);
    }

    @SubscribeEvent
    public static void spawnPlacements(SpawnPlacementRegisterEvent event) {
        ModSpawns.registerPlacements(event);
    }

    @SubscribeEvent
    public static void configLoaded(ModConfigEvent event) {
        WotwConfig.reload(event);
    }
}
