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

    public static final EntityModelLayer MARTIAN = layer("martian");
    public static final EntityModelLayer MARTIAN_MASTER = layer("martian_master");

    private ModModelLayers() {
    }

    private static EntityModelLayer layer(String name) {
        return new EntityModelLayer(WotwMod.id(name), "main");
    }
}
