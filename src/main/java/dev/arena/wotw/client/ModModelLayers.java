package dev.arena.wotw.client;

import dev.arena.wotw.WotwMod;
import net.minecraft.client.render.entity.model.EntityModelLayer;

/** One model layer per machine — every tripod has its own silhouette. */
public final class ModModelLayers {

    public static final EntityModelLayer BEAM = layer("beam");

    public static final EntityModelLayer TRIPOD_2005 = layer("tripod_2005");
    public static final EntityModelLayer BBC_WHITE_TRIPOD = layer("bbc_white_tripod");
    public static final EntityModelLayer BBC_RED_TRIPOD = layer("bbc_red_tripod");
    public static final EntityModelLayer FLYING_MANTA = layer("flying_manta");
    public static final EntityModelLayer BOOK_TRIPOD = layer("book_tripod");
    public static final EntityModelLayer ASYLUM_WALKER = layer("asylum_walker");
    public static final EntityModelLayer GOLIATH_TRIPOD = layer("goliath_tripod");
    public static final EntityModelLayer MARTIAN_HERON = layer("martian_heron");
    public static final EntityModelLayer MARTIAN_SPIDER = layer("martian_spider");
    public static final EntityModelLayer MARTIAN_LOUSE = layer("martian_louse");

    public static final EntityModelLayer FLYING_MACHINE = layer("flying_machine");
    public static final EntityModelLayer ELECTRIC_MACHINE = layer("electric_machine");
    public static final EntityModelLayer HANDLING_MACHINE = layer("handling_machine");
    public static final EntityModelLayer DIGGING_MACHINE = layer("digging_machine");
    public static final EntityModelLayer TEMPEST = layer("tempest");
    public static final EntityModelLayer BOMBARDING_MACHINE = layer("bombarding_machine");
    public static final EntityModelLayer DRONE = layer("drone");
    public static final EntityModelLayer PILOT_TRIPOD = layer("human_tripod");

    /* third wave: Martian walkers */
    public static final EntityModelLayer WATER_TRIPOD = layer("water_tripod");
    public static final EntityModelLayer SCOUT_TRIPOD = layer("scout_tripod");
    public static final EntityModelLayer RAM_TRIPOD = layer("ram_tripod");
    public static final EntityModelLayer UBERPOD = layer("uberpod");
    public static final EntityModelLayer BUNKER_TRIPOD = layer("bunker_tripod");
    public static final EntityModelLayer FLAMER_TRIPOD = layer("flamer_tripod");
    public static final EntityModelLayer TANK_TRIPOD = layer("tank_tripod");
    public static final EntityModelLayer TAXI_TRIPOD = layer("taxi_tripod");

    /* the human army */
    public static final EntityModelLayer HUMAN_SOLDIER = layer("human_soldier");
    public static final EntityModelLayer HUMAN_TANK = layer("human_tank");
    public static final EntityModelLayer HUMAN_FIGHTER = layer("human_fighter");
    public static final EntityModelLayer HUMAN_WARSHIP = layer("human_warship");

    public static final EntityModelLayer MARTIAN = layer("martian");
    public static final EntityModelLayer MARTIAN_MASTER = layer("martian_master");

    private ModModelLayers() {
    }

    private static EntityModelLayer layer(String name) {
        return new EntityModelLayer(WotwMod.id(name), "main");
    }
}
