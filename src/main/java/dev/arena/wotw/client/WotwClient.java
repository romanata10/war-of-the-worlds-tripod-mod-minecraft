package dev.arena.wotw.client;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.client.model.BeamModel;
import dev.arena.wotw.client.model.HumanModels;
import dev.arena.wotw.client.model.MartianModels;
import dev.arena.wotw.client.model.TripodModels;
import dev.arena.wotw.client.render.BeamEntityRenderer;
import dev.arena.wotw.client.render.CapsuleRenderer;
import dev.arena.wotw.client.render.GenericHumanRenderer;
import dev.arena.wotw.client.render.GenericTripodRenderer;
import dev.arena.wotw.client.render.ShellRenderer;
import dev.arena.wotw.registry.ModBlocks;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client side of the mod: model layers, renderers and the render layers of the transparent blocks.
 *
 * <p>Forge wants the three things through three different events, so the tripod table is spelled
 * out twice — once for the model layers, once for the renderers — and both stay right next to each
 * other in this file.</p>
 */
@Mod.EventBusSubscriber(modid = WotwMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class WotwClient {

    private WotwClient() {
    }

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.BEAM, BeamModel::getTexturedModelData);
        event.registerLayerDefinition(ModModelLayers.TRIPOD_2005, TripodModels::tripod2005);
        event.registerLayerDefinition(ModModelLayers.BBC_WHITE_TRIPOD, () -> TripodModels.bbc(false));
        event.registerLayerDefinition(ModModelLayers.BBC_RED_TRIPOD, () -> TripodModels.bbc(true));
        event.registerLayerDefinition(ModModelLayers.FLYING_MANTA, TripodModels::manta);
        event.registerLayerDefinition(ModModelLayers.BOOK_TRIPOD, TripodModels::book);
        event.registerLayerDefinition(ModModelLayers.ASYLUM_WALKER, TripodModels::asylum);
        event.registerLayerDefinition(ModModelLayers.GOLIATH_TRIPOD, TripodModels::goliath);
        event.registerLayerDefinition(ModModelLayers.MARTIAN_HERON, TripodModels::heron);
        event.registerLayerDefinition(ModModelLayers.MARTIAN_SPIDER, TripodModels::spider);
        event.registerLayerDefinition(ModModelLayers.MARTIAN_LOUSE, TripodModels::louse);
        event.registerLayerDefinition(ModModelLayers.MARTIAN, MartianModels::martian);
        event.registerLayerDefinition(ModModelLayers.MARTIAN_MASTER, MartianModels::martianMaster);
        event.registerLayerDefinition(ModModelLayers.FLYING_MACHINE, TripodModels::flyingMachine);
        event.registerLayerDefinition(ModModelLayers.ELECTRIC_MACHINE, TripodModels::electric);
        event.registerLayerDefinition(ModModelLayers.HANDLING_MACHINE, TripodModels::handling);
        event.registerLayerDefinition(ModModelLayers.DIGGING_MACHINE, TripodModels::digging);
        event.registerLayerDefinition(ModModelLayers.TEMPEST, TripodModels::tempest);
        event.registerLayerDefinition(ModModelLayers.BOMBARDING_MACHINE, TripodModels::bombarding);
        event.registerLayerDefinition(ModModelLayers.DRONE, TripodModels::drone);
        event.registerLayerDefinition(ModModelLayers.PILOT_TRIPOD, TripodModels::humanTripod);
        event.registerLayerDefinition(ModModelLayers.WATER_TRIPOD, TripodModels::waterTripod);
        event.registerLayerDefinition(ModModelLayers.SCOUT_TRIPOD, TripodModels::scoutTripod);
        event.registerLayerDefinition(ModModelLayers.RAM_TRIPOD, TripodModels::ramTripod);
        event.registerLayerDefinition(ModModelLayers.UBERPOD, TripodModels::uberpod);
        event.registerLayerDefinition(ModModelLayers.BUNKER_TRIPOD, TripodModels::bunkerTripod);
        event.registerLayerDefinition(ModModelLayers.FLAMER_TRIPOD, TripodModels::flamerTripod);
        event.registerLayerDefinition(ModModelLayers.TANK_TRIPOD, TripodModels::tankTripod);
        event.registerLayerDefinition(ModModelLayers.TAXI_TRIPOD, TripodModels::taxiTripod);
        event.registerLayerDefinition(ModModelLayers.HUMAN_SOLDIER, HumanModels::soldier);
        event.registerLayerDefinition(ModModelLayers.HUMAN_TANK, HumanModels::tank);
        event.registerLayerDefinition(ModModelLayers.HUMAN_FIGHTER, HumanModels::fighter);
        event.registerLayerDefinition(ModModelLayers.HUMAN_WARSHIP, HumanModels::warship);
        event.registerLayerDefinition(ModModelLayers.CONSTRUCTION_TRIPOD, TripodModels::constructionTripod);
        event.registerLayerDefinition(ModModelLayers.SMOKE_TRIPOD, TripodModels::smokeTripod);
        event.registerLayerDefinition(ModModelLayers.BOMB_TRIPOD, TripodModels::bombTripod);
        event.registerLayerDefinition(ModModelLayers.CONSTRICTOR, TripodModels::constrictor);
        event.registerLayerDefinition(ModModelLayers.XENO_TELEPATH, TripodModels::xenoTelepath);
        event.registerLayerDefinition(ModModelLayers.CAMERON, TripodModels::cameron);
        event.registerLayerDefinition(ModModelLayers.GOREY, TripodModels::gorey);
        event.registerLayerDefinition(ModModelLayers.WAYNE, TripodModels::wayne);
        event.registerLayerDefinition(ModModelLayers.GOBLE, TripodModels::goble);
        event.registerLayerDefinition(ModModelLayers.BBC2019, TripodModels::bbc2019);
        event.registerLayerDefinition(ModModelLayers.ATTACK2021, TripodModels::attack2021);
        event.registerLayerDefinition(ModModelLayers.ATTACK, TripodModels::attack);
        event.registerLayerDefinition(ModModelLayers.VEGETABLE, TripodModels::vegetable);
        event.registerLayerDefinition(ModModelLayers.MEXICAN, TripodModels::mexican);
        event.registerLayerDefinition(ModModelLayers.FOUR_LEGGED, TripodModels::fourLegged);
        event.registerLayerDefinition(ModModelLayers.HUMAN_HELICOPTER, HumanModels::helicopter);
        event.registerLayerDefinition(ModModelLayers.HUMAN_SUBMARINE, HumanModels::submarine);
        event.registerLayerDefinition(ModModelLayers.HUMAN_BOMBER, HumanModels::bomber);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BEAM, BeamEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.CAPSULE, CapsuleRenderer::new);
        event.registerEntityRenderer(ModEntities.SHELL, ShellRenderer::new);
        event.registerEntityRenderer(ModEntities.TRIPOD_2005,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.TRIPOD_2005, "tripod_2005", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.BBC_WHITE_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.BBC_WHITE_TRIPOD, "bbc_white_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.BBC_RED_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.BBC_RED_TRIPOD, "bbc_red_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.FLYING_MANTA,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.FLYING_MANTA, "flying_manta", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.BOOK_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.BOOK_TRIPOD, "book_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.ASYLUM_WALKER,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.ASYLUM_WALKER, "asylum_walker", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.GOLIATH_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.GOLIATH_TRIPOD, "goliath_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.MARTIAN_HERON,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.MARTIAN_HERON, "martian_heron", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.MARTIAN_SPIDER,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.MARTIAN_SPIDER, "martian_spider", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.MARTIAN_LOUSE,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.MARTIAN_LOUSE, "martian_louse", 1.0F, 0.9F));
        event.registerEntityRenderer(ModEntities.MARTIAN,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.MARTIAN, "martian", 1.0F, 0.8F));
        event.registerEntityRenderer(ModEntities.MARTIAN_MASTER,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.MARTIAN_MASTER, "martian_master", 1.0F, 1.1F));
        event.registerEntityRenderer(ModEntities.FLYING_MACHINE,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.FLYING_MACHINE, "flying_machine", 1.0F, 1.4F));
        event.registerEntityRenderer(ModEntities.ELECTRIC_MACHINE,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.ELECTRIC_MACHINE, "electric_machine", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.HANDLING_MACHINE,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.HANDLING_MACHINE, "handling_machine", 1.0F, 1.3F));
        event.registerEntityRenderer(ModEntities.DIGGING_MACHINE,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.DIGGING_MACHINE, "digging_machine", 1.0F, 1.3F));
        event.registerEntityRenderer(ModEntities.TEMPEST,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.TEMPEST, "tempest", 1.0F, 1.5F));
        event.registerEntityRenderer(ModEntities.BOMBARDING_MACHINE,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.BOMBARDING_MACHINE, "bombarding_machine", 1.0F, 1.1F));
        event.registerEntityRenderer(ModEntities.DRONE,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.DRONE, "drone", 1.0F, 0.6F));
        event.registerEntityRenderer(ModEntities.PILOT_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.PILOT_TRIPOD, "human_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.WATER_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.WATER_TRIPOD, "water_tripod", 1.0F, 1.6F));
        event.registerEntityRenderer(ModEntities.SCOUT_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.SCOUT_TRIPOD, "scout_tripod", 1.0F, 0.8F));
        event.registerEntityRenderer(ModEntities.RAM_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.RAM_TRIPOD, "ram_tripod", 1.0F, 1.4F));
        event.registerEntityRenderer(ModEntities.UBERPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.UBERPOD, "uberpod", 1.0F, 1.5F));
        event.registerEntityRenderer(ModEntities.BUNKER_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.BUNKER_TRIPOD, "bunker_tripod", 1.0F, 2.2F));
        event.registerEntityRenderer(ModEntities.FLAMER_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.FLAMER_TRIPOD, "flamer_tripod", 1.0F, 1.4F));
        event.registerEntityRenderer(ModEntities.TANK_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.TANK_TRIPOD, "tank_tripod", 1.0F, 2.6F));
        event.registerEntityRenderer(ModEntities.TAXI_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.TAXI_TRIPOD, "taxi_tripod", 1.0F, 1.4F));
        event.registerEntityRenderer(ModEntities.HUMAN_SOLDIER,
                context -> new GenericHumanRenderer<>(context, ModModelLayers.HUMAN_SOLDIER, "human_soldier", 1.0F, 0.5F));
        event.registerEntityRenderer(ModEntities.HUMAN_TANK,
                context -> new GenericHumanRenderer<>(context, ModModelLayers.HUMAN_TANK, "human_tank", 1.0F, 1.5F));
        event.registerEntityRenderer(ModEntities.HUMAN_FIGHTER,
                context -> new GenericHumanRenderer<>(context, ModModelLayers.HUMAN_FIGHTER, "human_fighter", 1.0F, 2.2F));
        event.registerEntityRenderer(ModEntities.HUMAN_WARSHIP,
                context -> new GenericHumanRenderer<>(context, ModModelLayers.HUMAN_WARSHIP, "human_warship", 1.0F, 2.4F));
        event.registerEntityRenderer(ModEntities.CONSTRUCTION_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.CONSTRUCTION_TRIPOD, "construction_tripod",
                        1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.SMOKE_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.SMOKE_TRIPOD, "smoke_tripod", 1.0F, 1.1F));
        event.registerEntityRenderer(ModEntities.BOMB_TRIPOD,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.BOMB_TRIPOD, "bomb_tripod", 1.0F, 1.15F));
        event.registerEntityRenderer(ModEntities.CONSTRICTOR,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.CONSTRICTOR, "constrictor", 1.0F, 1.1F));
        event.registerEntityRenderer(ModEntities.XENO_TELEPATH,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.XENO_TELEPATH, "xeno_telepath", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.CAMERON,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.CAMERON, "cameron_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.GOREY,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.GOREY, "gorey_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.WAYNE,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.WAYNE, "wayne_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.GOBLE,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.GOBLE, "goble_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.BBC2019,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.BBC2019, "bbc2019_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.ATTACK2021,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.ATTACK2021, "attack2021_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.ATTACK,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.ATTACK, "attack_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.VEGETABLE,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.VEGETABLE, "vegetable_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.MEXICAN,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.MEXICAN, "mexican_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.FOUR_LEGGED,
                context -> new GenericTripodRenderer<>(context, ModModelLayers.FOUR_LEGGED, "four_legged_tripod", 1.0F, 1.2F));
        event.registerEntityRenderer(ModEntities.HUMAN_HELICOPTER,
                context -> new GenericHumanRenderer<>(context, ModModelLayers.HUMAN_HELICOPTER, "human_helicopter", 1.0F,
                        1.8F));
        event.registerEntityRenderer(ModEntities.HUMAN_SUBMARINE,
                context -> new GenericHumanRenderer<>(context, ModModelLayers.HUMAN_SUBMARINE, "human_submarine", 1.0F,
                        1.6F));
        event.registerEntityRenderer(ModEntities.HUMAN_BOMBER,
                context -> new GenericHumanRenderer<>(context, ModModelLayers.HUMAN_BOMBER, "human_bomber", 1.0F, 2.6F));
    }

    @SubscribeEvent
    public static void blockRenderTypes(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Roots and vines are cut out, the glass is translucent: without this the leaves and the
            // glass render as solid cubes.
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.RED_ROOT, RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLOOD_ROOTS, RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLOOD_VINES, RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.MARTIAN_TURRET, RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.MARTIAN_GLASS, RenderType.translucent());
        });
    }
}
