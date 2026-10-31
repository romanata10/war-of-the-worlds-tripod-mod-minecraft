package dev.arena.wotw.client;

import dev.arena.wotw.client.model.BeamModel;
import dev.arena.wotw.client.model.HumanModels;
import dev.arena.wotw.client.model.MartianModels;
import dev.arena.wotw.client.model.TripodModels;
import dev.arena.wotw.client.render.BeamEntityRenderer;
import dev.arena.wotw.client.render.CapsuleRenderer;
import dev.arena.wotw.client.render.ShellRenderer;
import dev.arena.wotw.client.render.GenericHumanRenderer;
import dev.arena.wotw.client.render.GenericTripodRenderer;
import dev.arena.wotw.entity.AbstractTripodEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import dev.arena.wotw.registry.ModBlocks;
import dev.arena.wotw.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;

/** Client entry point: model layers, renderers and textures. */
public class WotwClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BEAM, BeamModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.BEAM, BeamEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.CAPSULE, CapsuleRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_ROOT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLOOD_ROOTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MARTIAN_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLOOD_VINES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MARTIAN_TURRET, RenderLayer.getCutout());

        registerTripod(ModModelLayers.TRIPOD_2005, TripodModels::tripod2005,
                ModEntities.TRIPOD_2005, "tripod_2005", 1.0F, 1.2F);
        registerTripod(ModModelLayers.BBC_WHITE_TRIPOD, () -> TripodModels.bbc(false),
                ModEntities.BBC_WHITE_TRIPOD, "bbc_white_tripod", 1.0F, 1.2F);
        registerTripod(ModModelLayers.BBC_RED_TRIPOD, () -> TripodModels.bbc(true),
                ModEntities.BBC_RED_TRIPOD, "bbc_red_tripod", 1.0F, 1.2F);
        registerTripod(ModModelLayers.FLYING_MANTA, TripodModels::manta,
                ModEntities.FLYING_MANTA, "flying_manta", 1.0F, 1.2F);
        registerTripod(ModModelLayers.BOOK_TRIPOD, TripodModels::book,
                ModEntities.BOOK_TRIPOD, "book_tripod", 1.0F, 1.2F);
        registerTripod(ModModelLayers.ASYLUM_WALKER, TripodModels::asylum,
                ModEntities.ASYLUM_WALKER, "asylum_walker", 1.0F, 1.2F);
        registerTripod(ModModelLayers.GOLIATH_TRIPOD, TripodModels::goliath,
                ModEntities.GOLIATH_TRIPOD, "goliath_tripod", 1.0F, 1.2F);
        registerTripod(ModModelLayers.MARTIAN_HERON, TripodModels::heron,
                ModEntities.MARTIAN_HERON, "martian_heron", 1.0F, 1.2F);
        registerTripod(ModModelLayers.MARTIAN_SPIDER, TripodModels::spider,
                ModEntities.MARTIAN_SPIDER, "martian_spider", 1.0F, 1.2F);
        registerTripod(ModModelLayers.MARTIAN_LOUSE, TripodModels::louse,
                ModEntities.MARTIAN_LOUSE, "martian_louse", 1.0F, 0.9F);
        registerTripod(ModModelLayers.MARTIAN, MartianModels::martian,
                ModEntities.MARTIAN, "martian", 1.0F, 0.8F);
        registerTripod(ModModelLayers.MARTIAN_MASTER, MartianModels::martianMaster,
                ModEntities.MARTIAN_MASTER, "martian_master", 1.0F, 1.1F);

        // second wave
        registerTripod(ModModelLayers.FLYING_MACHINE, TripodModels::flyingMachine,
                ModEntities.FLYING_MACHINE, "flying_machine", 1.0F, 1.4F);
        registerTripod(ModModelLayers.ELECTRIC_MACHINE, TripodModels::electric,
                ModEntities.ELECTRIC_MACHINE, "electric_machine", 1.0F, 1.2F);
        registerTripod(ModModelLayers.HANDLING_MACHINE, TripodModels::handling,
                ModEntities.HANDLING_MACHINE, "handling_machine", 1.0F, 1.3F);
        registerTripod(ModModelLayers.DIGGING_MACHINE, TripodModels::digging,
                ModEntities.DIGGING_MACHINE, "digging_machine", 1.0F, 1.3F);
        registerTripod(ModModelLayers.TEMPEST, TripodModels::tempest,
                ModEntities.TEMPEST, "tempest", 1.0F, 1.5F);
        registerTripod(ModModelLayers.BOMBARDING_MACHINE, TripodModels::bombarding,
                ModEntities.BOMBARDING_MACHINE, "bombarding_machine", 1.0F, 1.1F);
        registerTripod(ModModelLayers.DRONE, TripodModels::drone,
                ModEntities.DRONE, "drone", 1.0F, 0.6F);
        registerTripod(ModModelLayers.PILOT_TRIPOD, TripodModels::humanTripod,
                ModEntities.PILOT_TRIPOD, "human_tripod", 1.0F, 1.2F);
        // third wave: new Martian walkers
        registerTripod(ModModelLayers.WATER_TRIPOD, TripodModels::waterTripod,
                ModEntities.WATER_TRIPOD, "water_tripod", 1.0F, 1.6F);
        registerTripod(ModModelLayers.SCOUT_TRIPOD, TripodModels::scoutTripod,
                ModEntities.SCOUT_TRIPOD, "scout_tripod", 1.0F, 0.8F);
        registerTripod(ModModelLayers.RAM_TRIPOD, TripodModels::ramTripod,
                ModEntities.RAM_TRIPOD, "ram_tripod", 1.0F, 1.4F);
        registerTripod(ModModelLayers.UBERPOD, TripodModels::uberpod,
                ModEntities.UBERPOD, "uberpod", 1.0F, 1.5F);
        registerTripod(ModModelLayers.BUNKER_TRIPOD, TripodModels::bunkerTripod,
                ModEntities.BUNKER_TRIPOD, "bunker_tripod", 1.0F, 2.2F);
        registerTripod(ModModelLayers.FLAMER_TRIPOD, TripodModels::flamerTripod,
                ModEntities.FLAMER_TRIPOD, "flamer_tripod", 1.0F, 1.4F);
        registerTripod(ModModelLayers.TANK_TRIPOD, TripodModels::tankTripod,
                ModEntities.TANK_TRIPOD, "tank_tripod", 1.0F, 2.6F);
        registerTripod(ModModelLayers.TAXI_TRIPOD, TripodModels::taxiTripod,
                ModEntities.TAXI_TRIPOD, "taxi_tripod", 1.0F, 1.4F);

        // the human army
        registerHuman(ModModelLayers.HUMAN_SOLDIER, HumanModels::soldier,
                ModEntities.HUMAN_SOLDIER, "human_soldier", 1.0F, 0.5F);
        registerHuman(ModModelLayers.HUMAN_TANK, HumanModels::tank,
                ModEntities.HUMAN_TANK, "human_tank", 1.0F, 1.5F);
        registerHuman(ModModelLayers.HUMAN_FIGHTER, HumanModels::fighter,
                ModEntities.HUMAN_FIGHTER, "human_fighter", 1.0F, 2.2F);
        registerHuman(ModModelLayers.HUMAN_WARSHIP, HumanModels::warship,
                ModEntities.HUMAN_WARSHIP, "human_warship", 1.0F, 2.4F);

        EntityRendererRegistry.register(ModEntities.SHELL, ShellRenderer::new);
    }

    private static <T extends PathAwareEntity> void registerHuman(
            EntityModelLayer layer,
            EntityModelLayerRegistry.TexturedModelDataProvider modelProvider,
            EntityType<T> type,
            String texture,
            float renderScale,
            float shadowRadius) {
        EntityModelLayerRegistry.registerModelLayer(layer, modelProvider);
        EntityRendererRegistry.<T>register(type,
                context -> new GenericHumanRenderer<>(context, layer, texture, renderScale, shadowRadius));
    }

    private static <T extends AbstractTripodEntity> void registerTripod(
            EntityModelLayer layer,
            EntityModelLayerRegistry.TexturedModelDataProvider modelProvider,
            EntityType<T> type,
            String texture,
            float renderScale,
            float shadowRadius) {
        EntityModelLayerRegistry.registerModelLayer(layer, modelProvider);
        EntityRendererRegistry.<T>register(type,
                context -> new GenericTripodRenderer<>(context, layer, texture, renderScale, shadowRadius));
    }
}
