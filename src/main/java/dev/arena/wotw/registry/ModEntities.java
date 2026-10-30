package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.entity.AbstractTripodEntity;
import dev.arena.wotw.entity.AsylumWalkerEntity;
import dev.arena.wotw.entity.BbcRedTripodEntity;
import dev.arena.wotw.entity.BbcWhiteTripodEntity;
import dev.arena.wotw.entity.BookTripodEntity;
import dev.arena.wotw.entity.FlyingMantaEntity;
import dev.arena.wotw.entity.GoliathTripodEntity;
import dev.arena.wotw.entity.MartianHeronEntity;
import dev.arena.wotw.entity.MartianLouseEntity;
import dev.arena.wotw.entity.MartianSpiderEntity;
import dev.arena.wotw.entity.Tripod2005Entity;
import dev.arena.wotw.entity.projectile.BeamEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

/**
 * All entity types of the mod. Registration happens in the static initialiser; {@link #register()}
 * only exists to force the class to load in a deterministic order.
 */
public final class ModEntities {

    private static final List<EntityType<?>> ALL_TRIPODS = new ArrayList<>();

    /* ------------------------------------------------------------------ */
    /*  Projectiles                                                        */
    /* ------------------------------------------------------------------ */

    public static final EntityType<BeamEntity> BEAM = register("beam",
            EntityType.Builder.<BeamEntity>create(BeamEntity::new, SpawnGroup.MISC)
                    .setDimensions(0.8F, 0.8F)
                    .maxTrackingRange(8)
                    .trackingTickInterval(1));

    /* ------------------------------------------------------------------ */
    /*  Tripods                                                            */
    /* ------------------------------------------------------------------ */

    public static final EntityType<Tripod2005Entity> TRIPOD_2005 = tripod("tripod_2005",
            EntityType.Builder.create(Tripod2005Entity::new, SpawnGroup.MONSTER).setDimensions(4.2F, 8.0F));

    public static final EntityType<BbcWhiteTripodEntity> BBC_WHITE_TRIPOD = tripod("bbc_white_tripod",
            EntityType.Builder.create(BbcWhiteTripodEntity::new, SpawnGroup.MONSTER).setDimensions(3.4F, 6.5F));

    public static final EntityType<BbcRedTripodEntity> BBC_RED_TRIPOD = tripod("bbc_red_tripod",
            EntityType.Builder.create(BbcRedTripodEntity::new, SpawnGroup.MONSTER).setDimensions(3.4F, 6.5F));

    public static final EntityType<FlyingMantaEntity> FLYING_MANTA = tripod("flying_manta",
            EntityType.Builder.create(FlyingMantaEntity::new, SpawnGroup.MONSTER).setDimensions(7.5F, 3.5F));

    public static final EntityType<BookTripodEntity> BOOK_TRIPOD = tripod("book_tripod",
            EntityType.Builder.create(BookTripodEntity::new, SpawnGroup.MONSTER).setDimensions(4.4F, 9.0F));

    public static final EntityType<AsylumWalkerEntity> ASYLUM_WALKER = tripod("asylum_walker",
            EntityType.Builder.create(AsylumWalkerEntity::new, SpawnGroup.MONSTER).setDimensions(3.4F, 4.2F));

    public static final EntityType<GoliathTripodEntity> GOLIATH_TRIPOD = tripod("goliath_tripod",
            EntityType.Builder.create(GoliathTripodEntity::new, SpawnGroup.MONSTER).setDimensions(5.2F, 10.0F));

    public static final EntityType<MartianHeronEntity> MARTIAN_HERON = tripod("martian_heron",
            EntityType.Builder.create(MartianHeronEntity::new, SpawnGroup.MONSTER).setDimensions(3.2F, 11.5F));

    public static final EntityType<MartianSpiderEntity> MARTIAN_SPIDER = tripod("martian_spider",
            EntityType.Builder.create(MartianSpiderEntity::new, SpawnGroup.MONSTER).setDimensions(3.4F, 3.1F));

    public static final EntityType<MartianLouseEntity> MARTIAN_LOUSE = tripod("martian_louse",
            EntityType.Builder.create(MartianLouseEntity::new, SpawnGroup.MONSTER).setDimensions(1.4F, 1.0F));

    private ModEntities() {
    }

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, WotwMod.id(name), builder.build(name));
    }

    private static <T extends AbstractTripodEntity> EntityType<T> tripod(String name, EntityType.Builder<T> builder) {
        EntityType<T> type = register(name, builder.maxTrackingRange(10).trackingTickInterval(2));
        ALL_TRIPODS.add(type);
        return type;
    }

    /** Entity types of every tripod, in one list (also used by the Martian cylinder). */
    public static List<EntityType<?>> tripods() {
        return List.copyOf(ALL_TRIPODS);
    }

    public static List<EntityType<?>> all() {
        return tripods();
    }

    public static EntityType<?> randomTripod(Random random) {
        List<EntityType<?>> types = tripods();
        return types.get(random.nextInt(types.size()));
    }

    public static void register() {
        WotwMod.LOGGER.debug("Registered {} entity types", 1 + ALL_TRIPODS.size());
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(TRIPOD_2005, Tripod2005Entity.createAttributes());
        FabricDefaultAttributeRegistry.register(BBC_WHITE_TRIPOD, BbcWhiteTripodEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(BBC_RED_TRIPOD, BbcRedTripodEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(FLYING_MANTA, FlyingMantaEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(BOOK_TRIPOD, BookTripodEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ASYLUM_WALKER, AsylumWalkerEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(GOLIATH_TRIPOD, GoliathTripodEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MARTIAN_HERON, MartianHeronEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MARTIAN_SPIDER, MartianSpiderEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MARTIAN_LOUSE, MartianLouseEntity.createAttributes());
    }
}
