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
import dev.arena.wotw.entity.CameronTripodEntity;
import dev.arena.wotw.entity.GoreyTripodEntity;
import dev.arena.wotw.entity.WayneTripodEntity;
import dev.arena.wotw.entity.GobleTripodEntity;
import dev.arena.wotw.entity.Bbc2019TripodEntity;
import dev.arena.wotw.entity.Attack2021TripodEntity;
import dev.arena.wotw.entity.AttackTripodEntity;
import dev.arena.wotw.entity.VegetableTripodEntity;
import dev.arena.wotw.entity.MexicanTripodEntity;
import dev.arena.wotw.entity.FourLeggedTripodEntity;
import dev.arena.wotw.entity.AlienDawnMk2TripodEntity;
import dev.arena.wotw.entity.AlienDawnTripodEntity;
import dev.arena.wotw.entity.AnnihilationTripodEntity;
import dev.arena.wotw.entity.FrostTripodEntity;
import dev.arena.wotw.entity.StormTripodEntity;
import dev.arena.wotw.entity.WeaverTripodEntity;
import dev.arena.wotw.entity.BlinkTripodEntity;
import dev.arena.wotw.entity.HarvesterTripodEntity;
import dev.arena.wotw.entity.HumanApcEntity;
import dev.arena.wotw.entity.HumanArtilleryEntity;
import dev.arena.wotw.entity.HumanJetEntity;
import dev.arena.wotw.entity.HumanSniperEntity;
import dev.arena.wotw.entity.HumanMedicEntity;
import dev.arena.wotw.entity.MotherShipTripodEntity;
import dev.arena.wotw.entity.MiningTripodEntity;
import dev.arena.wotw.entity.RogerDeanTripodEntity;
import dev.arena.wotw.entity.CityTripodEntity;
import dev.arena.wotw.entity.FactoryTripodEntity;
import dev.arena.wotw.entity.CageTripodEntity;
import dev.arena.wotw.entity.HumanoidTripodEntity;
import dev.arena.wotw.entity.SignalScoutTripodEntity;
import dev.arena.wotw.entity.NextwaveCyborgTripodEntity;
import dev.arena.wotw.entity.PendragonTripodEntity;
import dev.arena.wotw.entity.ScaryMovieTripodEntity;
import dev.arena.wotw.entity.Series1998TripodEntity;
import dev.arena.wotw.entity.SteamTowerTripodEntity;
import dev.arena.wotw.entity.True2012TripodEntity;
import dev.arena.wotw.entity.UssrBookTripodEntity;
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
    public static EntityType<HumanApcEntity> HUMAN_APC;
    public static EntityType<HumanArtilleryEntity> HUMAN_ARTILLERY;
    public static EntityType<HumanJetEntity> HUMAN_JET;
    public static EntityType<HumanSniperEntity> HUMAN_SNIPER;
    public static EntityType<HumanMedicEntity> HUMAN_MEDIC;

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

    /* Sixth wave: ten machines straight from the artwork. */

    public static EntityType<CameronTripodEntity> CAMERON;

    public static EntityType<GoreyTripodEntity> GOREY;

    public static EntityType<WayneTripodEntity> WAYNE;

    public static EntityType<GobleTripodEntity> GOBLE;

    public static EntityType<Bbc2019TripodEntity> BBC2019;

    public static EntityType<Attack2021TripodEntity> ATTACK2021;

    public static EntityType<AttackTripodEntity> ATTACK;

    public static EntityType<VegetableTripodEntity> VEGETABLE;

    public static EntityType<MexicanTripodEntity> MEXICAN;

    public static EntityType<FourLeggedTripodEntity> FOUR_LEGGED;

    public static EntityType<Series1998TripodEntity> SERIES1998;
    public static EntityType<PendragonTripodEntity> PENDRAGON;
    public static EntityType<NextwaveCyborgTripodEntity> NEXTWAVE_CYBORG;
    public static EntityType<True2012TripodEntity> TRUE2012;
    public static EntityType<SteamTowerTripodEntity> STEAM_TOWER;
    public static EntityType<AlienDawnTripodEntity> ALIEN_DAWN;
    public static EntityType<AlienDawnMk2TripodEntity> ALIEN_DAWN_MK2;
    public static EntityType<ScaryMovieTripodEntity> SCARY_MOVIE;
    public static EntityType<UssrBookTripodEntity> USSR_BOOK;
    public static EntityType<AnnihilationTripodEntity> ANNIHILATION;
    public static EntityType<MotherShipTripodEntity> MOTHERSHIP;
    public static EntityType<MiningTripodEntity> MINING;
    public static EntityType<RogerDeanTripodEntity> ROGER_DEAN;
    public static EntityType<CityTripodEntity> CITY;
    public static EntityType<FactoryTripodEntity> FACTORY;
    public static EntityType<CageTripodEntity> CAGE;
    public static EntityType<HumanoidTripodEntity> HUMANOID;
    public static EntityType<SignalScoutTripodEntity> SIGNAL_SCOUT;
    public static EntityType<FrostTripodEntity> FROST;
    public static EntityType<StormTripodEntity> STORM;
    public static EntityType<WeaverTripodEntity> WEAVER;
    public static EntityType<BlinkTripodEntity> BLINK;
    public static EntityType<HarvesterTripodEntity> HARVESTER;


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
                        .sized(5.1F, 1.7F)
                        .clientTrackingRange(10)
                        .build("human_helicopter"));
        ENTITY_TYPES.register("human_submarine",
                () -> HUMAN_SUBMARINE = EntityType.Builder.of(HumanSubmarineEntity::new, MobCategory.CREATURE)
                        .sized(5.4F, 2.4F)
                        .clientTrackingRange(10)
                        .build("human_submarine"));
        ENTITY_TYPES.register("human_bomber",
                () -> HUMAN_BOMBER = EntityType.Builder.of(HumanBomberEntity::new, MobCategory.CREATURE)
                        .sized(7.1F, 2.1F)
                        .clientTrackingRange(12)
                        .build("human_bomber"));
        ENTITY_TYPES.register("human_apc",
                () -> HUMAN_APC = EntityType.Builder.of(HumanApcEntity::new, MobCategory.CREATURE)
                        .sized(3.2F, 2.4F)
                        .clientTrackingRange(12)
                        .build("human_apc"));
        ENTITY_TYPES.register("human_artillery",
                () -> HUMAN_ARTILLERY = EntityType.Builder.of(HumanArtilleryEntity::new, MobCategory.CREATURE)
                        .sized(2.8F, 2.4F)
                        .clientTrackingRange(12)
                        .build("human_artillery"));
        ENTITY_TYPES.register("human_jet",
                () -> HUMAN_JET = EntityType.Builder.of(HumanJetEntity::new, MobCategory.CREATURE)
                        .sized(4.4F, 2.2F)
                        .clientTrackingRange(12)
                        .build("human_jet"));
        ENTITY_TYPES.register("human_sniper",
                () -> HUMAN_SNIPER = EntityType.Builder.of(HumanSniperEntity::new, MobCategory.CREATURE)
                        .sized(1.2F, 2.5F)
                        .clientTrackingRange(12)
                        .build("human_sniper"));
        ENTITY_TYPES.register("human_medic",
                () -> HUMAN_MEDIC = EntityType.Builder.of(HumanMedicEntity::new, MobCategory.CREATURE)
                        .sized(1.2F, 2.6F)
                        .clientTrackingRange(12)
                        .build("human_medic"));
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
                        .sized(8.7F, 18.4F)
                        .clientTrackingRange(12)
                        .updateInterval(2)
                        .build("construction_tripod")));
        ENTITY_TYPES.register("smoke_tripod",
                () -> SMOKE_TRIPOD = track(EntityType.Builder.of(SmokeTripodEntity::new, MobCategory.MONSTER)
                        .sized(7.3F, 15.1F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("smoke_tripod")));
        ENTITY_TYPES.register("bomb_tripod",
                () -> BOMB_TRIPOD = track(EntityType.Builder.of(BombTripodEntity::new, MobCategory.MONSTER)
                        .sized(7.6F, 16.7F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("bomb_tripod")));
        ENTITY_TYPES.register("constrictor",
                () -> CONSTRICTOR = track(EntityType.Builder.of(ConstrictorEntity::new, MobCategory.MONSTER)
                        .sized(6.6F, 13.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("constrictor")));
        ENTITY_TYPES.register("xeno_telepath",
                () -> XENO_TELEPATH = track(EntityType.Builder.of(XenoTelepathEntity::new, MobCategory.MONSTER)
                        .sized(7.4F, 15.5F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("xeno_telepath")));
        ENTITY_TYPES.register("cameron_tripod",
                () -> CAMERON = track(EntityType.Builder.of(CameronTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.1F, 15.8F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("cameron_tripod")));
        ENTITY_TYPES.register("gorey_tripod",
                () -> GOREY = track(EntityType.Builder.of(GoreyTripodEntity::new, MobCategory.MONSTER)
                        .sized(7.5F, 15.1F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("gorey_tripod")));
        ENTITY_TYPES.register("wayne_tripod",
                () -> WAYNE = track(EntityType.Builder.of(WayneTripodEntity::new, MobCategory.MONSTER)
                        .sized(8.0F, 16.6F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("wayne_tripod")));
        ENTITY_TYPES.register("goble_tripod",
                () -> GOBLE = track(EntityType.Builder.of(GobleTripodEntity::new, MobCategory.MONSTER)
                        .sized(7.3F, 13.4F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("goble_tripod")));
        ENTITY_TYPES.register("bbc2019_tripod",
                () -> BBC2019 = track(EntityType.Builder.of(Bbc2019TripodEntity::new, MobCategory.MONSTER)
                        .sized(8.8F, 16.5F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("bbc2019_tripod")));
        ENTITY_TYPES.register("attack2021_tripod",
                () -> ATTACK2021 = track(EntityType.Builder.of(Attack2021TripodEntity::new, MobCategory.MONSTER)
                        .sized(7.1F, 17.2F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("attack2021_tripod")));
        ENTITY_TYPES.register("attack_tripod",
                () -> ATTACK = track(EntityType.Builder.of(AttackTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.5F, 17.7F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("attack_tripod")));
        ENTITY_TYPES.register("vegetable_tripod",
                () -> VEGETABLE = track(EntityType.Builder.of(VegetableTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.7F, 18.5F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("vegetable_tripod")));
        ENTITY_TYPES.register("mexican_tripod",
                () -> MEXICAN = track(EntityType.Builder.of(MexicanTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.8F, 18.4F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("mexican_tripod")));
        ENTITY_TYPES.register("four_legged_tripod",
                () -> FOUR_LEGGED = track(EntityType.Builder.of(FourLeggedTripodEntity::new, MobCategory.MONSTER)
                        .sized(7.8F, 16.9F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("four_legged_tripod")));

        ENTITY_TYPES.register("series1998_tripod",
                () -> SERIES1998 = track(EntityType.Builder.of(Series1998TripodEntity::new, MobCategory.MONSTER)
                        .sized(7.6F, 16.8F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("series1998_tripod")));
        ENTITY_TYPES.register("pendragon_tripod",
                () -> PENDRAGON = track(EntityType.Builder.of(PendragonTripodEntity::new, MobCategory.MONSTER)
                        .sized(8.2F, 15.7F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("pendragon_tripod")));
        ENTITY_TYPES.register("nextwave_cyborg_tripod",
                () -> NEXTWAVE_CYBORG = track(EntityType.Builder.of(NextwaveCyborgTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.2F, 17.8F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("nextwave_cyborg_tripod")));
        ENTITY_TYPES.register("true2012_tripod",
                () -> TRUE2012 = track(EntityType.Builder.of(True2012TripodEntity::new, MobCategory.MONSTER)
                        .sized(7.2F, 17.7F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("true2012_tripod")));
        ENTITY_TYPES.register("steam_tower_tripod",
                () -> STEAM_TOWER = track(EntityType.Builder.of(SteamTowerTripodEntity::new, MobCategory.MONSTER)
                        .sized(10.4F, 16.1F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("steam_tower_tripod")));
        ENTITY_TYPES.register("alien_dawn_tripod",
                () -> ALIEN_DAWN = track(EntityType.Builder.of(AlienDawnTripodEntity::new, MobCategory.MONSTER)
                        .sized(7.7F, 19.9F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("alien_dawn_tripod")));
        ENTITY_TYPES.register("alien_dawn_mk2_tripod",
                () -> ALIEN_DAWN_MK2 = track(EntityType.Builder.of(AlienDawnMk2TripodEntity::new, MobCategory.MONSTER)
                        .sized(7.8F, 19.5F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("alien_dawn_mk2_tripod")));
        ENTITY_TYPES.register("scary_movie_tripod",
                () -> SCARY_MOVIE = track(EntityType.Builder.of(ScaryMovieTripodEntity::new, MobCategory.MONSTER)
                        .sized(8.2F, 17.1F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("scary_movie_tripod")));
        ENTITY_TYPES.register("ussr_book_tripod",
                () -> USSR_BOOK = track(EntityType.Builder.of(UssrBookTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.2F, 20.4F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("ussr_book_tripod")));
        ENTITY_TYPES.register("annihilation_tripod",
                () -> ANNIHILATION = track(EntityType.Builder.of(AnnihilationTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.5F, 19.1F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("annihilation_tripod")));
        ENTITY_TYPES.register("mothership_tripod",
                () -> MOTHERSHIP = track(EntityType.Builder.of(MotherShipTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.5F, 18.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("mothership_tripod")));
        ENTITY_TYPES.register("mining_tripod",
                () -> MINING = track(EntityType.Builder.of(MiningTripodEntity::new, MobCategory.MONSTER)
                        .sized(7.5F, 15.8F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("mining_tripod")));
        ENTITY_TYPES.register("roger_dean_tripod",
                () -> ROGER_DEAN = track(EntityType.Builder.of(RogerDeanTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.5F, 19.6F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("roger_dean_tripod")));
        ENTITY_TYPES.register("city_tripod",
                () -> CITY = track(EntityType.Builder.of(CityTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.0F, 19.5F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("city_tripod")));
        ENTITY_TYPES.register("factory_tripod",
                () -> FACTORY = track(EntityType.Builder.of(FactoryTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.4F, 19.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("factory_tripod")));
        ENTITY_TYPES.register("cage_tripod",
                () -> CAGE = track(EntityType.Builder.of(CageTripodEntity::new, MobCategory.MONSTER)
                        .sized(7.4F, 16.7F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("cage_tripod")));
        ENTITY_TYPES.register("humanoid_tripod",
                () -> HUMANOID = track(EntityType.Builder.of(HumanoidTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.6F, 17.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("humanoid_tripod")));
        ENTITY_TYPES.register("signal_scout_tripod",
                () -> SIGNAL_SCOUT = track(EntityType.Builder.of(SignalScoutTripodEntity::new, MobCategory.MONSTER)
                        .sized(5.6F, 19.4F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("signal_scout_tripod")));
        ENTITY_TYPES.register("frost_tripod",
                () -> FROST = track(EntityType.Builder.of(FrostTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.6F, 17.0F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("frost_tripod")));
        ENTITY_TYPES.register("storm_tripod",
                () -> STORM = track(EntityType.Builder.of(StormTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.2F, 19.4F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("storm_tripod")));
        ENTITY_TYPES.register("weaver_tripod",
                () -> WEAVER = track(EntityType.Builder.of(WeaverTripodEntity::new, MobCategory.MONSTER)
                        .sized(8.6F, 13.7F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("weaver_tripod")));
        ENTITY_TYPES.register("blink_tripod",
                () -> BLINK = track(EntityType.Builder.of(BlinkTripodEntity::new, MobCategory.MONSTER)
                        .sized(6.2F, 15.5F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("blink_tripod")));
        ENTITY_TYPES.register("harvester_tripod",
                () -> HARVESTER = track(EntityType.Builder.of(HarvesterTripodEntity::new, MobCategory.MONSTER)
                        .sized(7.2F, 15.2F)
                        .clientTrackingRange(10)
                        .updateInterval(2)
                        .build("harvester_tripod")));
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
        event.put(HUMAN_APC, HumanApcEntity.createAttributes().build());
        event.put(HUMAN_ARTILLERY, HumanArtilleryEntity.createAttributes().build());
        event.put(HUMAN_JET, HumanJetEntity.createAttributes().build());
        event.put(HUMAN_SNIPER, HumanSniperEntity.createAttributes().build());
        event.put(HUMAN_MEDIC, HumanMedicEntity.createAttributes().build());
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
        event.put(CAMERON, CameronTripodEntity.createAttributes().build());
        event.put(GOREY, GoreyTripodEntity.createAttributes().build());
        event.put(WAYNE, WayneTripodEntity.createAttributes().build());
        event.put(GOBLE, GobleTripodEntity.createAttributes().build());
        event.put(BBC2019, Bbc2019TripodEntity.createAttributes().build());
        event.put(ATTACK2021, Attack2021TripodEntity.createAttributes().build());
        event.put(ATTACK, AttackTripodEntity.createAttributes().build());
        event.put(VEGETABLE, VegetableTripodEntity.createAttributes().build());
        event.put(MEXICAN, MexicanTripodEntity.createAttributes().build());
        event.put(FOUR_LEGGED, FourLeggedTripodEntity.createAttributes().build());
        event.put(SERIES1998, Series1998TripodEntity.createAttributes().build());
        event.put(PENDRAGON, PendragonTripodEntity.createAttributes().build());
        event.put(NEXTWAVE_CYBORG, NextwaveCyborgTripodEntity.createAttributes().build());
        event.put(TRUE2012, True2012TripodEntity.createAttributes().build());
        event.put(STEAM_TOWER, SteamTowerTripodEntity.createAttributes().build());
        event.put(ALIEN_DAWN, AlienDawnTripodEntity.createAttributes().build());
        event.put(ALIEN_DAWN_MK2, AlienDawnMk2TripodEntity.createAttributes().build());
        event.put(SCARY_MOVIE, ScaryMovieTripodEntity.createAttributes().build());
        event.put(USSR_BOOK, UssrBookTripodEntity.createAttributes().build());
        event.put(ANNIHILATION, AnnihilationTripodEntity.createAttributes().build());
        event.put(MOTHERSHIP, MotherShipTripodEntity.createAttributes().build());
        event.put(MINING, MiningTripodEntity.createAttributes().build());
        event.put(ROGER_DEAN, RogerDeanTripodEntity.createAttributes().build());
        event.put(CITY, CityTripodEntity.createAttributes().build());
        event.put(FACTORY, FactoryTripodEntity.createAttributes().build());
        event.put(CAGE, CageTripodEntity.createAttributes().build());
        event.put(HUMANOID, HumanoidTripodEntity.createAttributes().build());
        event.put(SIGNAL_SCOUT, SignalScoutTripodEntity.createAttributes().build());
        event.put(FROST, FrostTripodEntity.createAttributes().build());
        event.put(STORM, StormTripodEntity.createAttributes().build());
        event.put(WEAVER, WeaverTripodEntity.createAttributes().build());
        event.put(BLINK, BlinkTripodEntity.createAttributes().build());
        event.put(HARVESTER, HarvesterTripodEntity.createAttributes().build());

    }
}
