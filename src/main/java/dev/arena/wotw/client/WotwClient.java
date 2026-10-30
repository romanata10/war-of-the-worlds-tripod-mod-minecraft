package dev.arena.wotw.client;

import dev.arena.wotw.client.model.BeamModel;
import dev.arena.wotw.client.model.TripodModels;
import dev.arena.wotw.client.render.BeamEntityRenderer;
import dev.arena.wotw.client.render.GenericTripodRenderer;
import dev.arena.wotw.entity.AbstractTripodEntity;
import dev.arena.wotw.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;

/** Client entry point: model layers, renderers and textures. */
public class WotwClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BEAM, BeamModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.BEAM, BeamEntityRenderer::new);

        registerTripod(ModModelLayers.TRIPOD_2005, TripodModels::tripod2005,
                ModEntities.TRIPOD_2005, "tripod_2005", 3.9F, 2.6F);
        registerTripod(ModModelLayers.BBC_WHITE_TRIPOD, () -> TripodModels.bbc(false),
                ModEntities.BBC_WHITE_TRIPOD, "bbc_white_tripod", 4.0F, 2.1F);
        registerTripod(ModModelLayers.BBC_RED_TRIPOD, () -> TripodModels.bbc(true),
                ModEntities.BBC_RED_TRIPOD, "bbc_red_tripod", 4.0F, 2.1F);
        registerTripod(ModModelLayers.FLYING_MANTA, TripodModels::manta,
                ModEntities.FLYING_MANTA, "flying_manta", 3.0F, 3.0F);
        registerTripod(ModModelLayers.BOOK_TRIPOD, TripodModels::book,
                ModEntities.BOOK_TRIPOD, "book_tripod", 4.5F, 2.8F);
        registerTripod(ModModelLayers.ASYLUM_WALKER, TripodModels::asylum,
                ModEntities.ASYLUM_WALKER, "asylum_walker", 2.5F, 2.0F);
        registerTripod(ModModelLayers.GOLIATH_TRIPOD, TripodModels::goliath,
                ModEntities.GOLIATH_TRIPOD, "goliath_tripod", 4.55F, 3.1F);
        registerTripod(ModModelLayers.MARTIAN_HERON, TripodModels::heron,
                ModEntities.MARTIAN_HERON, "martian_heron", 4.8F, 1.8F);
        registerTripod(ModModelLayers.MARTIAN_SPIDER, TripodModels::spider,
                ModEntities.MARTIAN_SPIDER, "martian_spider", 2.5F, 1.8F);
        registerTripod(ModModelLayers.MARTIAN_LOUSE, TripodModels::louse,
                ModEntities.MARTIAN_LOUSE, "martian_louse", 1.7F, 0.7F);
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
