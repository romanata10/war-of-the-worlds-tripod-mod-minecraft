package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.entity.AbstractHumanCombatantEntity;
import dev.arena.wotw.entity.AbstractTripodEntity;
import dev.arena.wotw.entity.AsylumWalkerEntity;
import dev.arena.wotw.entity.BunkerTripodEntity;
import dev.arena.wotw.entity.FlamerTripodEntity;
import dev.arena.wotw.entity.HumanBomberEntity;
import dev.arena.wotw.entity.HumanFighterEntity;
import dev.arena.wotw.entity.HumanHelicopterEntity;
import dev.arena.wotw.entity.HumanSubmarineEntity;
import dev.arena.wotw.entity.HumanSoldierEntity;
import dev.arena.wotw.entity.HumanTankEntity;
import dev.arena.wotw.entity.HumanWarshipEntity;
import dev.arena.wotw.entity.RamTripodEntity;
import dev.arena.wotw.entity.ScoutTripodEntity;
import dev.arena.wotw.entity.SmokeTripodEntity;
import dev.arena.wotw.entity.TankTripodEntity;
import dev.arena.wotw.entity.TaxiTripodEntity;
import dev.arena.wotw.entity.UberpodEntity;
import dev.arena.wotw.entity.WaterTripodEntity;
import dev.arena.wotw.entity.XenoTelepathEntity;
import dev.arena.wotw.entity.CapsuleEntity;
import dev.arena.wotw.entity.BombardingMachineEntity;
import dev.arena.wotw.entity.BombTripodEntity;
import dev.arena.wotw.entity.ConstrictorEntity;
import dev.arena.wotw.entity.ConstructionTripodEntity;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;

/**
 * All entity types of the mod. Registration happens in the static initialiser; {@link #register()}
 * only exists to force the class to load in a deterministic order.
 */
public final class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WotwMod.MOD_ID);

    private static final List<EntityType<?>> ALL_TRIPODS = new ArrayList<>();

    /* ------------------------------------------------------------------ */
    /*  Projectiles                                                        */
    /* ------------------------------------------------------------------ */

    /** The giant falling capsule used by the invasion events and by the capsule item. */
    public static EntityType<CapsuleEntity> CAPSULE;

    public static EntityType<BeamEntity> BEAM;

    /* ------------------------------------------------------------------ */
    /*  Tripods                                                            */
    /* ------------------------------------------------------------------ */

    public static EntityType<Tripod2005Entity> TRIPOD_2005;

    public static EntityType<BbcWhiteTripodEntity> BBC_WHITE_TRIPOD;

    public static EntityType<BbcRedTripodEntity> BBC_RED_TRIPOD;

    public static EntityType<FlyingMantaEntity> FLYING_MANTA;

    public static EntityType<BookTripodEntity> BOOK_TRIPOD;

    public static EntityType<AsylumWalkerEntity> ASYLUM_WALKER;

    public static EntityType<GoliathTripodEntity> GOLIATH_TRIPOD;

    public static EntityType<MartianHeronEntity> MARTIAN_HERON;

    public static EntityType<MartianSpiderEntity> MARTIAN_SPIDER;

    public static EntityType<MartianLouseEntity> MARTIAN_LOUSE;

    /* The creatures themselves, not their machines. */

    public static EntityType<MartianEntity> MARTIAN;

    public static EntityType<MartianMasterEntity> MARTIAN_MASTER;

    /* ------------------------------------------------------------------ */
    /*  Second wave: Martian machines that are not tripods                 */
    /* ------------------------------------------------------------------ */

    public static EntityType<FlyingMachineEntity> FLYING_MACHINE;

    public static EntityType<ElectricMachineEntity> ELECTRIC_MACHINE;

    public static EntityType<HandlingMachineEntity> HANDLING_MACHINE;

    public static EntityType<DiggingMachineEntity> DIGGING_MACHINE;

    public static EntityType<TempestEntity> TEMPEST;

    public static EntityType<BombardingMachineEntity> BOMBARDING_MACHINE;

    public static EntityType<DroneEntity> DRONE;

    /** The human built tripod from the blueprint: rideable, not part of the Martian waves. */
    public static EntityType<PilotTripodEntity> PILOT_TRIPOD;

    /** Shells of the Bombarding Machine and the charges of the Electric Machine. */
    public static EntityType<ShellEntity> SHELL;

    /* ------------------------------------------------------------------ */
    /*  Third wave: human machines and the new Martian walkers             */
    /* ------------------------------------------------------------------ */

    public static EntityType<HumanSoldierEntity> HUMAN_SOLDIER;

    public static EntityType<HumanTankEntity> HUMAN_TANK;

    public static EntityType<HumanFighterEntity> HUMAN_FIGHTER;

    public static EntityType<HumanWarshipEntity> HUMAN_WARSHIP;

    /* The air force and the navy, fourth wave. */

    /** Gunship: hovers low over the base and hoses the walkers with its door guns. */
    public static EntityType<HumanHelicopterEntity> HUMAN_HELICOPTER;

    /** Submarine: runs under the surface near the coast and launches torpedoes. */
    public static EntityType<HumanSubmarineEntity> HUMAN_SUBMARINE;

    /** Heavy bomber: flies a high orbit and drops a stick of bombs on whatever walks below. */
    public static EntityType<HumanBomberEntity> HUMAN_BOMBER;

    /** Not part of the random waves: it only climbs out of a capsule that fell into the sea. */
    public static EntityType<WaterTripodEntity> WATER_TRIPOD;

    public static EntityType<ScoutTripodEntity> SCOUT_TRIPOD;

    public static EntityType<RamTripodEntity> RAM_TRIPOD;

    public static EntityType<UberpodEntity> UBERPOD;

    public static EntityType<BunkerTripodEntity> BUNKER_TRIPOD;

    public static EntityType<FlamerTripodEntity> FLAMER_TRIPOD;

    public static EntityType<TankTripodEntity> TANK_TRIPOD;

    public static EntityType<TaxiTripodEntity> TAXI_TRIPOD;

    /* The Martian engineers and the psychological weapons, fifth wave. */

    /** Colossal builder: a crane claw that loads rubble into its holds and raises bunkers with it. */
    public static EntityType<ConstructionTripodEntity> CONSTRUCTION_TRIPOD;

    /** Smoke machine: a permanent fog bank and the killing black smoke. */
    public static EntityType<SmokeTripodEntity> SMOKE_TRIPOD;

    /** Mortar machine: lobs bombs of black smoke instead of firing a ray. */
    public static EntityType<BombTripodEntity> BOMB_TRIPOD;

    /** Constrictor: chemical foam that glues a squad down, then the heat lens. */
    public static EntityType<ConstrictorEntity> CONSTRICTOR;

    /** Xeno-Telepath: a Martian in a glass dome that turns human crews on each other. */
    public static EntityType<XenoTelepathEntity> XENO_TELEPATH;

    /** True for every Martian war machine, including the creatures. Used by the human army AI. */
    public static boolean isMartianInvader(net.minecraft.world.entity.Entity entity) {
        return entity instanceof AbstractTripodEntity && !(entity instanceof PilotTripodEntity);
    }

    /*
     * The types are built here, not next to the fields: Forge freezes the vanilla entity registry before
     * a mod is constructed and {@code EntityType} registers a holder for itself in its own constructor.
     * Doing the work inside the supplier keeps it in the register event, where Forge expects it, and
     * every field above is ready by the time the game spawns something.
     */
    static {
        ENTITY_TYPES.register("capsule",
                () -> CAPSULE = EntityType.Builder.<CapsuleEntity>of(CapsuleEntity::new, MobCategory.MISC)
                        .sized(2.4F, 3.2F)
                        .clientTrackingRange(12)
                        .updateInterval(2)
                        .build("capsule"));
        ENTITY_TYPES.register("beam",
                () -> BEAM = EntityType.Builder.<BeamEntity>of(BeamEntity::new, MobCategory.MISC)
                        .sized(0.8F, 0.8F)
                        .clientTrackingRange(8)
                        .updateInterval(1)
                        .build("beam"));
        ENTITY_TYPES.register("tripod_2005",
                () -> TRIPOD_2005 = track(EntityType.Builder.of(Tripod2005Entity::new, MobCategory.MONSTER)
                        .sized(5.0F, 11.3F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("tripod_2005")));
        ENTITY_TYPES.register("bbc_white_tripod",
                () -> BBC_WHITE_TRIPOD = track(EntityType.Builder.of(BbcWhiteTripodEntity::new, MobCategory.MONSTER)
                        .sized(2.9F, 9.3F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("bbc_white_tripod")));
        ENTITY_TYPES.register("bbc_red_tripod",
                () -> BBC_RED_TRIPOD = track(EntityType.Builder.of(BbcRedTripodEntity::new, MobCategory.MONSTER)
                        .sized(4.4F, 9.3F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("bbc_red_tripod")));
        ENTITY_TYPES.register("flying_manta",
                () -> FLYING_MANTA = track(EntityType.Builder.of(FlyingMantaEntity::new, MobCategory.MONSTER)
                        .sized(9.0F, 3.2F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("flying_manta")));
        ENTITY_TYPES.register("book_tripod",
                () -> BOOK_TRIPOD = track(EntityType.Builder.of(BookTripodEntity::new, MobCategory.MONSTER)
                        .sized(5.5F, 12.4F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("book_tripod")));
        ENTITY_TYPES.register("asylum_walker",
                () -> ASYLUM_WALKER = track(EntityType.Builder.of(AsylumWalkerEntity::new, MobCategory.MONSTER)
                        .sized(5.0F, 6.7F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("asylum_walker")));
        ENTITY_TYPES.register("goliath_tripod",
                () -> GOLIATH_TRIPOD = track(EntityType.Builder.of(GoliathTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.7F, 14.1F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("goliath_tripod")));
        ENTITY_TYPES.register("martian_heron",
                () -> MARTIAN_HERON = track(EntityType.Builder.of(MartianHeronEntity::new, MobCategory.MONSTER)
                        .sized(4.5F, 14.2F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("martian_heron")));
        ENTITY_TYPES.register("martian_spider",
                () -> MARTIAN_SPIDER = track(EntityType.Builder.of(MartianSpiderEntity::new, MobCategory.MONSTER)
                        .sized(5.9F, 4.8F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("martian_spider")));
        ENTITY_TYPES.register("martian_louse",
                () -> MARTIAN_LOUSE = track(EntityType.Builder.of(MartianLouseEntity::new, MobCategory.MONSTER)
                        .sized(1.7F, 2.2F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("martian_louse")));
        ENTITY_TYPES.register("martian",
                () -> MARTIAN = track(EntityType.Builder.of(MartianEntity::new, MobCategory.MONSTER)
                        .sized(2.6F, 2.3F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("martian")));
        ENTITY_TYPES.register("martian_master",
                () -> MARTIAN_MASTER = track(EntityType.Builder.of(MartianMasterEntity::new, MobCategory.MONSTER)
                        .sized(3.6F, 3.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("martian_master")));
        ENTITY_TYPES.register("flying_machine",
                () -> FLYING_MACHINE = track(EntityType.Builder.of(FlyingMachineEntity::new, MobCategory.MONSTER)
                        .sized(4.8F, 3.8F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("flying_machine")));
        ENTITY_TYPES.register("electric_machine",
                () -> ELECTRIC_MACHINE = track(EntityType.Builder.of(ElectricMachineEntity::new, MobCategory.MONSTER)
                        .sized(4.2F, 9.4F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("electric_machine")));
        ENTITY_TYPES.register("handling_machine",
                () -> HANDLING_MACHINE = track(EntityType.Builder.of(HandlingMachineEntity::new, MobCategory.MONSTER)
                        .sized(4.9F, 6.5F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("handling_machine")));
        ENTITY_TYPES.register("digging_machine",
                () -> DIGGING_MACHINE = track(EntityType.Builder.of(DiggingMachineEntity::new, MobCategory.MONSTER)
                        .sized(5.0F, 3.2F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("digging_machine")));
        ENTITY_TYPES.register("tempest",
                () -> TEMPEST = track(EntityType.Builder.of(TempestEntity::new, MobCategory.MONSTER)
                        .sized(7.0F, 15.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("tempest")));
        ENTITY_TYPES.register("bombarding_machine",
                () -> BOMBARDING_MACHINE = track(EntityType.Builder.of(BombardingMachineEntity::new, MobCategory.MONSTER)
                        .sized(3.8F, 8.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("bombarding_machine")));
        ENTITY_TYPES.register("drone",
                () -> DRONE = track(EntityType.Builder.of(DroneEntity::new, MobCategory.MONSTER)
                        .sized(1.9F, 2.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("drone")));
        ENTITY_TYPES.register("pilot_tripod",
                () -> PILOT_TRIPOD = EntityType.Builder.of(PilotTripodEntity::new, MobCategory.MONSTER)
                        .sized(4.5F, 8.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("pilot_tripod"));
        ENTITY_TYPES.register("shell",
                () -> SHELL = EntityType.Builder.<ShellEntity>of(ShellEntity::new, MobCategory.MISC)
                        .sized(0.6F, 0.6F)
                        .clientTrackingRange(6)
                        .updateInterval(1)
                        .build("shell"));
        ENTITY_TYPES.register("human_soldier",
                () -> HUMAN_SOLDIER = EntityType.Builder.of(HumanSoldierEntity::new, MobCategory.CREATURE)
                        .sized(1.0F, 2.6F)
                        .build("human_soldier"));
        ENTITY_TYPES.register("human_tank",
                () -> HUMAN_TANK = EntityType.Builder.of(HumanTankEntity::new, MobCategory.CREATURE)
                        .sized(4.1F, 2.6F)
                        .build("human_tank"));
        ENTITY_TYPES.register("human_fighter",
                () -> HUMAN_FIGHTER = EntityType.Builder.of(HumanFighterEntity::new, MobCategory.CREATURE)
                        .sized(4.9F, 2.0F)
                        .build("human_fighter"));
        ENTITY_TYPES.register("human_warship",
                () -> HUMAN_WARSHIP = EntityType.Builder.of(HumanWarshipEntity::new, MobCategory.CREATURE)
                        .sized(7.0F, 3.3F)
                        .build("human_warship"));
        ENTITY_TYPES.register("human_helicopter",
                () -> HUMAN_HELICOPTER = EntityType.Builder.of(HumanHelicopterEntity::new, MobCategory.CREATURE)
                        .sized(7.0F, 3.4F)
                        .clientTrackingRange(10)
                        .build("human_helicopter"));
        ENTITY_TYPES.register("human_submarine",
                () -> HUMAN_SUBMARINE = EntityType.Builder.of(HumanSubmarineEntity::new, MobCategory.CREATURE)
                        .sized(6.5F, 3.0F)
                        .clientTrackingRange(10)
                        .build("human_submarine"));
        ENTITY_TYPES.register("human_bomber",
                () -> HUMAN_BOMBER = EntityType.Builder.of(HumanBomberEntity::new, MobCategory.CREATURE)
                        .sized(12.0F, 3.6F)
                        .clientTrackingRange(12)
                        .build("human_bomber"));
        ENTITY_TYPES.register("water_tripod",
                () -> WATER_TRIPOD = EntityType.Builder.of(WaterTripodEntity::new, MobCategory.MONSTER)
                        .sized(8.8F, 16.6F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("water_tripod"));
        ENTITY_TYPES.register("scout_tripod",
                () -> SCOUT_TRIPOD = track(EntityType.Builder.of(ScoutTripodEntity::new, MobCategory.MONSTER)
                        .sized(2.9F, 5.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("scout_tripod")));
        ENTITY_TYPES.register("ram_tripod",
                () -> RAM_TRIPOD = track(EntityType.Builder.of(RamTripodEntity::new, MobCategory.MONSTER)
                        .sized(3.8F, 3.1F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("ram_tripod")));
        ENTITY_TYPES.register("uberpod",
                () -> UBERPOD = track(EntityType.Builder.of(UberpodEntity::new, MobCategory.MONSTER)
                        .sized(5.4F, 10.1F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("uberpod")));
        ENTITY_TYPES.register("bunker_tripod",
                () -> BUNKER_TRIPOD = track(EntityType.Builder.of(BunkerTripodEntity::new, MobCategory.MONSTER)
                        .sized(7.0F, 7.1F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("bunker_tripod")));
        ENTITY_TYPES.register("flamer_tripod",
                () -> FLAMER_TRIPOD = track(EntityType.Builder.of(FlamerTripodEntity::new, MobCategory.MONSTER)
                        .sized(5.0F, 6.9F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("flamer_tripod")));
        ENTITY_TYPES.register("tank_tripod",
                () -> TANK_TRIPOD = track(EntityType.Builder.of(TankTripodEntity::new, MobCategory.MONSTER)
                        .sized(11.3F, 16.6F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("tank_tripod")));
        ENTITY_TYPES.register("taxi_tripod",
                () -> TAXI_TRIPOD = track(EntityType.Builder.of(TaxiTripodEntity::new, MobCategory.MONSTER)
                        .sized(4.1F, 4.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("taxi_tripod")));
        ENTITY_TYPES.register("construction_tripod",
                () -> CONSTRUCTION_TRIPOD = track(EntityType.Builder.of(ConstructionTripodEntity::new, MobCategory.MONSTER)
                        .sized(13.0F, 22.0F)
                        .clientTrackingRange(12)
                        .updateInterval(2)
                        .build("construction_tripod")));
        ENTITY_TYPES.register("smoke_tripod",
                () -> SMOKE_TRIPOD = track(EntityType.Builder.of(SmokeTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.0F, 11.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("smoke_tripod")));
        ENTITY_TYPES.register("bomb_tripod",
                () -> BOMB_TRIPOD = track(EntityType.Builder.of(BombTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.5F, 12.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("bomb_tripod")));
        ENTITY_TYPES.register("constrictor",
                () -> CONSTRICTOR = track(EntityType.Builder.of(ConstrictorEntity::new, MobCategory.MONSTER)
                        .sized(7.0F, 9.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("constrictor")));
        ENTITY_TYPES.register("xeno_telepath",
                () -> XENO_TELEPATH = track(EntityType.Builder.of(XenoTelepathEntity::new, MobCategory.MONSTER)
                        .sized(5.5F, 13.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("xeno_telepath")));
    }

    private ModEntities() {
    }

    /**
     * Remembers a war machine in {@link #ALL_TRIPODS}, which the invasion waves and the cylinder use.
     *
     * <p>It has to happen inside the register event: the types only exist once Forge has built them,
     * and {@code EntityType} cannot be constructed any earlier (see the comment next to the static
     * block).</p>
     */
    private static <T extends AbstractTripodEntity> EntityType<T> track(EntityType<T> type) {
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

    public static EntityType<?> randomTripod(RandomSource random) {
        List<EntityType<?>> types = tripods();
        return types.get(random.nextInt(types.size()));
    }

    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
        WotwMod.LOGGER.debug("Registered the entity types of the invasion");
    }

    /** Forge wants the default attributes of every living entity through its own event. */
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(TRIPOD_2005, Tripod2005Entity.createAttributes().build());
        event.put(BBC_WHITE_TRIPOD, BbcWhiteTripodEntity.createAttributes().build());
        event.put(BBC_RED_TRIPOD, BbcRedTripodEntity.createAttributes().build());
        event.put(FLYING_MANTA, FlyingMantaEntity.createAttributes().build());
        event.put(BOOK_TRIPOD, BookTripodEntity.createAttributes().build());
        event.put(ASYLUM_WALKER, AsylumWalkerEntity.createAttributes().build());
        event.put(GOLIATH_TRIPOD, GoliathTripodEntity.createAttributes().build());
        event.put(MARTIAN_HERON, MartianHeronEntity.createAttributes().build());
        event.put(MARTIAN_SPIDER, MartianSpiderEntity.createAttributes().build());
        event.put(MARTIAN_LOUSE, MartianLouseEntity.createAttributes().build());
        event.put(MARTIAN, MartianEntity.createAttributes().build());
        event.put(MARTIAN_MASTER, MartianMasterEntity.createAttributes().build());
        event.put(FLYING_MACHINE, FlyingMachineEntity.createAttributes().build());
        event.put(ELECTRIC_MACHINE, ElectricMachineEntity.createAttributes().build());
        event.put(HANDLING_MACHINE, HandlingMachineEntity.createAttributes().build());
        event.put(DIGGING_MACHINE, DiggingMachineEntity.createAttributes().build());
        event.put(TEMPEST, TempestEntity.createAttributes().build());
        event.put(BOMBARDING_MACHINE, BombardingMachineEntity.createAttributes().build());
        event.put(DRONE, DroneEntity.createAttributes().build());
        event.put(PILOT_TRIPOD, PilotTripodEntity.createAttributes().build());
        event.put(HUMAN_SOLDIER, HumanSoldierEntity.createAttributes().build());
        event.put(HUMAN_TANK, HumanTankEntity.createAttributes().build());
        event.put(HUMAN_FIGHTER, HumanFighterEntity.createAttributes().build());
        event.put(HUMAN_WARSHIP, HumanWarshipEntity.createAttributes().build());
        event.put(HUMAN_HELICOPTER, HumanHelicopterEntity.createAttributes().build());
        event.put(HUMAN_SUBMARINE, HumanSubmarineEntity.createAttributes().build());
        event.put(HUMAN_BOMBER, HumanBomberEntity.createAttributes().build());
        event.put(WATER_TRIPOD, WaterTripodEntity.createAttributes().build());
        event.put(SCOUT_TRIPOD, ScoutTripodEntity.createAttributes().build());
        event.put(RAM_TRIPOD, RamTripodEntity.createAttributes().build());
        event.put(UBERPOD, UberpodEntity.createAttributes().build());
        event.put(BUNKER_TRIPOD, BunkerTripodEntity.createAttributes().build());
        event.put(FLAMER_TRIPOD, FlamerTripodEntity.createAttributes().build());
        event.put(TANK_TRIPOD, TankTripodEntity.createAttributes().build());
        event.put(TAXI_TRIPOD, TaxiTripodEntity.createAttributes().build());
        event.put(CONSTRUCTION_TRIPOD, ConstructionTripodEntity.createAttributes().build());
        event.put(SMOKE_TRIPOD, SmokeTripodEntity.createAttributes().build());
        event.put(BOMB_TRIPOD, BombTripodEntity.createAttributes().build());
        event.put(CONSTRICTOR, ConstrictorEntity.createAttributes().build());
        event.put(XENO_TELEPATH, XenoTelepathEntity.createAttributes().build());
    }
}
