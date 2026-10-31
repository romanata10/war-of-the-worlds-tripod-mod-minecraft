package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.entity.AbstractHumanCombatantEntity;
import dev.arena.wotw.entity.AbstractTripodEntity;
import dev.arena.wotw.entity.AsylumWalkerEntity;
import dev.arena.wotw.entity.BunkerTripodEntity;
import dev.arena.wotw.entity.FlamerTripodEntity;
import dev.arena.wotw.entity.HumanFighterEntity;
import dev.arena.wotw.entity.HumanSoldierEntity;
import dev.arena.wotw.entity.HumanTankEntity;
import dev.arena.wotw.entity.HumanWarshipEntity;
import dev.arena.wotw.entity.RamTripodEntity;
import dev.arena.wotw.entity.ScoutTripodEntity;
import dev.arena.wotw.entity.TankTripodEntity;
import dev.arena.wotw.entity.TaxiTripodEntity;
import dev.arena.wotw.entity.UberpodEntity;
import dev.arena.wotw.entity.WaterTripodEntity;
import dev.arena.wotw.entity.CapsuleEntity;
import dev.arena.wotw.entity.BombardingMachineEntity;
import dev.arena.wotw.entity.DiggingMachineEntity;
import dev.arena.wotw.entity.DroneEntity;
import dev.arena.wotw.entity.ElectricMachineEntity;
import dev.arena.wotw.entity.FlyingMachineEntity;
import dev.arena.wotw.entity.HandlingMachineEntity;
import dev.arena.wotw.entity.PilotTripodEntity;
import dev.arena.wotw.entity.TempestEntity;
import dev.arena.wotw.entity.BbcRedTripodEntity;
import dev.arena.wotw.entity.BbcWhiteTripodEntity;
import dev.arena.wotw.entity.BookTripodEntity;
import dev.arena.wotw.entity.FlyingMantaEntity;
import dev.arena.wotw.entity.GoliathTripodEntity;
import dev.arena.wotw.entity.MartianHeronEntity;
import dev.arena.wotw.entity.MartianEntity;
import dev.arena.wotw.entity.MartianLouseEntity;
import dev.arena.wotw.entity.MartianMasterEntity;
import dev.arena.wotw.entity.MartianSpiderEntity;
import dev.arena.wotw.entity.Tripod2005Entity;
import dev.arena.wotw.entity.projectile.BeamEntity;
import dev.arena.wotw.entity.projectile.ShellEntity;
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

    /** The giant falling capsule used by the invasion events and by the capsule item. */
    public static final EntityType<CapsuleEntity> CAPSULE = register("capsule",
            EntityType.Builder.<CapsuleEntity>create(CapsuleEntity::new, SpawnGroup.MISC)
                    .setDimensions(2.4F, 3.2F)
                    .maxTrackingRange(12)
                    .trackingTickInterval(2));

    public static final EntityType<BeamEntity> BEAM = register("beam",
            EntityType.Builder.<BeamEntity>create(BeamEntity::new, SpawnGroup.MISC)
                    .setDimensions(0.8F, 0.8F)
                    .maxTrackingRange(8)
                    .trackingTickInterval(1));

    /* ------------------------------------------------------------------ */
    /*  Tripods                                                            */
    /* ------------------------------------------------------------------ */

    public static final EntityType<Tripod2005Entity> TRIPOD_2005 = tripod("tripod_2005",
            EntityType.Builder.create(Tripod2005Entity::new, SpawnGroup.MONSTER).setDimensions(5.0F, 11.3F));

    public static final EntityType<BbcWhiteTripodEntity> BBC_WHITE_TRIPOD = tripod("bbc_white_tripod",
            EntityType.Builder.create(BbcWhiteTripodEntity::new, SpawnGroup.MONSTER).setDimensions(2.9F, 9.3F));

    public static final EntityType<BbcRedTripodEntity> BBC_RED_TRIPOD = tripod("bbc_red_tripod",
            EntityType.Builder.create(BbcRedTripodEntity::new, SpawnGroup.MONSTER).setDimensions(4.4F, 9.3F));

    public static final EntityType<FlyingMantaEntity> FLYING_MANTA = tripod("flying_manta",
            EntityType.Builder.create(FlyingMantaEntity::new, SpawnGroup.MONSTER).setDimensions(9.0F, 3.2F));

    public static final EntityType<BookTripodEntity> BOOK_TRIPOD = tripod("book_tripod",
            EntityType.Builder.create(BookTripodEntity::new, SpawnGroup.MONSTER).setDimensions(5.5F, 12.4F));

    public static final EntityType<AsylumWalkerEntity> ASYLUM_WALKER = tripod("asylum_walker",
            EntityType.Builder.create(AsylumWalkerEntity::new, SpawnGroup.MONSTER).setDimensions(5.0F, 6.7F));

    public static final EntityType<GoliathTripodEntity> GOLIATH_TRIPOD = tripod("goliath_tripod",
            EntityType.Builder.create(GoliathTripodEntity::new, SpawnGroup.MONSTER).setDimensions(6.7F, 14.1F));

    public static final EntityType<MartianHeronEntity> MARTIAN_HERON = tripod("martian_heron",
            EntityType.Builder.create(MartianHeronEntity::new, SpawnGroup.MONSTER).setDimensions(4.5F, 14.2F));

    public static final EntityType<MartianSpiderEntity> MARTIAN_SPIDER = tripod("martian_spider",
            EntityType.Builder.create(MartianSpiderEntity::new, SpawnGroup.MONSTER).setDimensions(5.9F, 4.8F));

    public static final EntityType<MartianLouseEntity> MARTIAN_LOUSE = tripod("martian_louse",
            EntityType.Builder.create(MartianLouseEntity::new, SpawnGroup.MONSTER).setDimensions(1.7F, 2.2F));

    /* The creatures themselves, not their machines. */

    public static final EntityType<MartianEntity> MARTIAN = tripod("martian",
            EntityType.Builder.create(MartianEntity::new, SpawnGroup.MONSTER).setDimensions(2.6F, 2.3F));

    public static final EntityType<MartianMasterEntity> MARTIAN_MASTER = tripod("martian_master",
            EntityType.Builder.create(MartianMasterEntity::new, SpawnGroup.MONSTER).setDimensions(3.6F, 3.0F));

    /* ------------------------------------------------------------------ */
    /*  Second wave: Martian machines that are not tripods                 */
    /* ------------------------------------------------------------------ */

    public static final EntityType<FlyingMachineEntity> FLYING_MACHINE = tripod("flying_machine",
            EntityType.Builder.create(FlyingMachineEntity::new, SpawnGroup.MONSTER).setDimensions(4.8F, 3.8F));

    public static final EntityType<ElectricMachineEntity> ELECTRIC_MACHINE = tripod("electric_machine",
            EntityType.Builder.create(ElectricMachineEntity::new, SpawnGroup.MONSTER).setDimensions(4.2F, 9.4F));

    public static final EntityType<HandlingMachineEntity> HANDLING_MACHINE = tripod("handling_machine",
            EntityType.Builder.create(HandlingMachineEntity::new, SpawnGroup.MONSTER).setDimensions(4.9F, 6.5F));

    public static final EntityType<DiggingMachineEntity> DIGGING_MACHINE = tripod("digging_machine",
            EntityType.Builder.create(DiggingMachineEntity::new, SpawnGroup.MONSTER).setDimensions(5.0F, 3.2F));

    public static final EntityType<TempestEntity> TEMPEST = tripod("tempest",
            EntityType.Builder.create(TempestEntity::new, SpawnGroup.MONSTER).setDimensions(7.0F, 15.0F));

    public static final EntityType<BombardingMachineEntity> BOMBARDING_MACHINE = tripod("bombarding_machine",
            EntityType.Builder.create(BombardingMachineEntity::new, SpawnGroup.MONSTER).setDimensions(3.8F, 8.0F));

    public static final EntityType<DroneEntity> DRONE = tripod("drone",
            EntityType.Builder.create(DroneEntity::new, SpawnGroup.MONSTER).setDimensions(1.9F, 2.0F));

    /** The human built tripod from the blueprint: rideable, not part of the Martian waves. */
    public static final EntityType<PilotTripodEntity> PILOT_TRIPOD = register("pilot_tripod",
            EntityType.Builder.create(PilotTripodEntity::new, SpawnGroup.MONSTER)
                    .setDimensions(4.5F, 8.0F)
                    .maxTrackingRange(10)
                    .trackingTickInterval(2));

    /** Shells of the Bombarding Machine and the charges of the Electric Machine. */
    public static final EntityType<ShellEntity> SHELL = register("shell",
            EntityType.Builder.<ShellEntity>create(ShellEntity::new, SpawnGroup.MISC)
                    .setDimensions(0.6F, 0.6F)
                    .maxTrackingRange(6)
                    .trackingTickInterval(1));


    /* ------------------------------------------------------------------ */
    /*  Third wave: human machines and the new Martian walkers             */
    /* ------------------------------------------------------------------ */

    public static final EntityType<HumanSoldierEntity> HUMAN_SOLDIER = register("human_soldier",
            EntityType.Builder.create(HumanSoldierEntity::new, SpawnGroup.CREATURE).setDimensions(1.0F, 2.6F));

    public static final EntityType<HumanTankEntity> HUMAN_TANK = register("human_tank",
            EntityType.Builder.create(HumanTankEntity::new, SpawnGroup.CREATURE).setDimensions(4.1F, 2.6F));

    public static final EntityType<HumanFighterEntity> HUMAN_FIGHTER = register("human_fighter",
            EntityType.Builder.create(HumanFighterEntity::new, SpawnGroup.CREATURE).setDimensions(4.9F, 2.0F));

    public static final EntityType<HumanWarshipEntity> HUMAN_WARSHIP = register("human_warship",
            EntityType.Builder.create(HumanWarshipEntity::new, SpawnGroup.CREATURE).setDimensions(7.0F, 3.3F));

    /** Not part of the random waves: it only climbs out of a capsule that fell into the sea. */
    public static final EntityType<WaterTripodEntity> WATER_TRIPOD = register("water_tripod",
            EntityType.Builder.create(WaterTripodEntity::new, SpawnGroup.MONSTER)
                    .setDimensions(8.8F, 16.6F)
                    .maxTrackingRange(10)
                    .trackingTickInterval(2));

    public static final EntityType<ScoutTripodEntity> SCOUT_TRIPOD = tripod("scout_tripod",
            EntityType.Builder.create(ScoutTripodEntity::new, SpawnGroup.MONSTER).setDimensions(2.9F, 5.0F));

    public static final EntityType<RamTripodEntity> RAM_TRIPOD = tripod("ram_tripod",
            EntityType.Builder.create(RamTripodEntity::new, SpawnGroup.MONSTER).setDimensions(3.8F, 3.1F));

    public static final EntityType<UberpodEntity> UBERPOD = tripod("uberpod",
            EntityType.Builder.create(UberpodEntity::new, SpawnGroup.MONSTER).setDimensions(5.4F, 10.1F));

    public static final EntityType<BunkerTripodEntity> BUNKER_TRIPOD = tripod("bunker_tripod",
            EntityType.Builder.create(BunkerTripodEntity::new, SpawnGroup.MONSTER).setDimensions(7.0F, 7.1F));

    public static final EntityType<FlamerTripodEntity> FLAMER_TRIPOD = tripod("flamer_tripod",
            EntityType.Builder.create(FlamerTripodEntity::new, SpawnGroup.MONSTER).setDimensions(5.0F, 6.9F));

    public static final EntityType<TankTripodEntity> TANK_TRIPOD = tripod("tank_tripod",
            EntityType.Builder.create(TankTripodEntity::new, SpawnGroup.MONSTER).setDimensions(11.3F, 16.6F));

    public static final EntityType<TaxiTripodEntity> TAXI_TRIPOD = tripod("taxi_tripod",
            EntityType.Builder.create(TaxiTripodEntity::new, SpawnGroup.MONSTER).setDimensions(4.1F, 4.0F));

    /** True for every Martian war machine, including the creatures. Used by the human army AI. */
    public static boolean isMartianInvader(net.minecraft.entity.Entity entity) {
        return entity instanceof AbstractTripodEntity && !(entity instanceof PilotTripodEntity);
    }

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
        FabricDefaultAttributeRegistry.register(MARTIAN, MartianEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MARTIAN_MASTER, MartianMasterEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(FLYING_MACHINE, FlyingMachineEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ELECTRIC_MACHINE, ElectricMachineEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(HANDLING_MACHINE, HandlingMachineEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(DIGGING_MACHINE, DiggingMachineEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TEMPEST, TempestEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(BOMBARDING_MACHINE, BombardingMachineEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(DRONE, DroneEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(PILOT_TRIPOD, PilotTripodEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(HUMAN_SOLDIER, HumanSoldierEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(HUMAN_TANK, HumanTankEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(HUMAN_FIGHTER, HumanFighterEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(HUMAN_WARSHIP, HumanWarshipEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WATER_TRIPOD, WaterTripodEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(SCOUT_TRIPOD, ScoutTripodEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(RAM_TRIPOD, RamTripodEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(UBERPOD, UberpodEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(BUNKER_TRIPOD, BunkerTripodEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(FLAMER_TRIPOD, FlamerTripodEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TANK_TRIPOD, TankTripodEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TAXI_TRIPOD, TaxiTripodEntity.createAttributes());
    }
}
