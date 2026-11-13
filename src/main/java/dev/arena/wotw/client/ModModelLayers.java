package dev.arena.wotw.client;

import dev.arena.wotw.WotwMod;
import net.minecraft.client.model.geom.ModelLayerLocation;

/** One model layer per machine — every tripod has its own silhouette. */
public final class ModModelLayers {

    public static final ModelLayerLocation BEAM = layer("beam");

    public static final ModelLayerLocation TRIPOD_2005 = layer("tripod_2005");
    public static final ModelLayerLocation BBC_WHITE_TRIPOD = layer("bbc_white_tripod");
    public static final ModelLayerLocation BBC_RED_TRIPOD = layer("bbc_red_tripod");
    public static final ModelLayerLocation FLYING_MANTA = layer("flying_manta");
    public static final ModelLayerLocation BOOK_TRIPOD = layer("book_tripod");
    public static final ModelLayerLocation ASYLUM_WALKER = layer("asylum_walker");
    public static final ModelLayerLocation GOLIATH_TRIPOD = layer("goliath_tripod");
    public static final ModelLayerLocation MARTIAN_HERON = layer("martian_heron");
    public static final ModelLayerLocation MARTIAN_SPIDER = layer("martian_spider");
    public static final ModelLayerLocation MARTIAN_LOUSE = layer("martian_louse");

    public static final ModelLayerLocation FLYING_MACHINE = layer("flying_machine");
    public static final ModelLayerLocation ELECTRIC_MACHINE = layer("electric_machine");
    public static final ModelLayerLocation HANDLING_MACHINE = layer("handling_machine");
    public static final ModelLayerLocation DIGGING_MACHINE = layer("digging_machine");
    public static final ModelLayerLocation TEMPEST = layer("tempest");
    public static final ModelLayerLocation BOMBARDING_MACHINE = layer("bombarding_machine");
    public static final ModelLayerLocation DRONE = layer("drone");
    public static final ModelLayerLocation PILOT_TRIPOD = layer("human_tripod");

    /* third wave: Martian walkers */
    public static final ModelLayerLocation WATER_TRIPOD = layer("water_tripod");
    public static final ModelLayerLocation SCOUT_TRIPOD = layer("scout_tripod");
    public static final ModelLayerLocation RAM_TRIPOD = layer("ram_tripod");
    public static final ModelLayerLocation UBERPOD = layer("uberpod");
    public static final ModelLayerLocation BUNKER_TRIPOD = layer("bunker_tripod");
    public static final ModelLayerLocation FLAMER_TRIPOD = layer("flamer_tripod");
    public static final ModelLayerLocation TANK_TRIPOD = layer("tank_tripod");
    public static final ModelLayerLocation TAXI_TRIPOD = layer("taxi_tripod");

    /* the human army */
    public static final ModelLayerLocation HUMAN_SOLDIER = layer("human_soldier");
    public static final ModelLayerLocation HUMAN_TANK = layer("human_tank");
    public static final ModelLayerLocation HUMAN_FIGHTER = layer("human_fighter");
    public static final ModelLayerLocation HUMAN_WARSHIP = layer("human_warship");
    public static final ModelLayerLocation CONSTRUCTION_TRIPOD = layer("construction_tripod");
    public static final ModelLayerLocation SMOKE_TRIPOD = layer("smoke_tripod");
    public static final ModelLayerLocation BOMB_TRIPOD = layer("bomb_tripod");
    public static final ModelLayerLocation CONSTRICTOR = layer("constrictor");
    public static final ModelLayerLocation XENO_TELEPATH = layer("xeno_telepath");
    public static final ModelLayerLocation CAMERON = layer("cameron_tripod");
    public static final ModelLayerLocation GOREY = layer("gorey_tripod");
    public static final ModelLayerLocation WAYNE = layer("wayne_tripod");
    public static final ModelLayerLocation GOBLE = layer("goble_tripod");
    public static final ModelLayerLocation BBC2019 = layer("bbc2019_tripod");
    public static final ModelLayerLocation ATTACK2021 = layer("attack2021_tripod");
    public static final ModelLayerLocation ATTACK = layer("attack_tripod");
    public static final ModelLayerLocation VEGETABLE = layer("vegetable_tripod");
    public static final ModelLayerLocation MEXICAN = layer("mexican_tripod");
    public static final ModelLayerLocation FOUR_LEGGED = layer("four_legged_tripod");
    public static final ModelLayerLocation SERIES1998 = layer("series1998_tripod");
    public static final ModelLayerLocation PENDRAGON = layer("pendragon_tripod");
    public static final ModelLayerLocation NEXTWAVE_CYBORG = layer("nextwave_cyborg_tripod");
    public static final ModelLayerLocation TRUE2012 = layer("true2012_tripod");
    public static final ModelLayerLocation STEAM_TOWER = layer("steam_tower_tripod");
    public static final ModelLayerLocation ALIEN_DAWN = layer("alien_dawn_tripod");
    public static final ModelLayerLocation ALIEN_DAWN_MK2 = layer("alien_dawn_mk2_tripod");
    public static final ModelLayerLocation SCARY_MOVIE = layer("scary_movie_tripod");
    public static final ModelLayerLocation USSR_BOOK = layer("ussr_book_tripod");
    public static final ModelLayerLocation ANNIHILATION = layer("annihilation_tripod");
    public static final ModelLayerLocation HUMAN_HELICOPTER = layer("human_helicopter");
    public static final ModelLayerLocation HUMAN_SUBMARINE = layer("human_submarine");
    public static final ModelLayerLocation HUMAN_BOMBER = layer("human_bomber");

    public static final ModelLayerLocation MARTIAN = layer("martian");
    public static final ModelLayerLocation MARTIAN_MASTER = layer("martian_master");

    private ModModelLayers() {
    }

    private static ModelLayerLocation layer(String name) {
        return new ModelLayerLocation(WotwMod.id(name), "main");
    }
}
