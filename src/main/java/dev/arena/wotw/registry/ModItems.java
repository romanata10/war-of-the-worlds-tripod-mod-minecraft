package dev.arena.wotw.registry;
import dev.arena.wotw.WotwMod;
import dev.arena.wotw.item.AlloyCannonItem;
import dev.arena.wotw.item.ArcRifleItem;
import dev.arena.wotw.item.HeatRayEmitterItem;
import dev.arena.wotw.item.MachineGunItem;
import dev.arena.wotw.item.MartianCapsuleItem;
import dev.arena.wotw.item.NuclearBombItem;
import dev.arena.wotw.item.PistolItem;
import dev.arena.wotw.item.RocketLauncherItem;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.network.chat.Component;

/** Items: spawn eggs, the alloy the tripods drop, the lootable heat ray and the cylinder block item. */
public final class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WotwMod.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, WotwMod.MOD_ID);

    public static Item MARTIAN_ALLOY;

    public static Item TRIPOD_2005_SPAWN_EGG;
    public static Item BBC_WHITE_TRIPOD_SPAWN_EGG;
    public static Item BBC_RED_TRIPOD_SPAWN_EGG;
    public static Item FLYING_MANTA_SPAWN_EGG;
    public static Item BOOK_TRIPOD_SPAWN_EGG;
    public static Item ASYLUM_WALKER_SPAWN_EGG;
    public static Item GOLIATH_TRIPOD_SPAWN_EGG;
    public static Item MARTIAN_HERON_SPAWN_EGG;
    public static Item MARTIAN_SPIDER_SPAWN_EGG;
    public static Item MARTIAN_LOUSE_SPAWN_EGG;
    public static Item MARTIAN_SPAWN_EGG;
    public static Item MARTIAN_MASTER_SPAWN_EGG;

    public static Item FLYING_MACHINE_SPAWN_EGG;
    public static Item ELECTRIC_MACHINE_SPAWN_EGG;
    public static Item HANDLING_MACHINE_SPAWN_EGG;
    public static Item DIGGING_MACHINE_SPAWN_EGG;
    public static Item TEMPEST_SPAWN_EGG;
    public static Item BOMBARDING_MACHINE_SPAWN_EGG;
    public static Item DRONE_SPAWN_EGG;
    public static Item PILOT_TRIPOD_SPAWN_EGG;

    public static Item HUMAN_SOLDIER_SPAWN_EGG;
    public static Item HUMAN_TANK_SPAWN_EGG;
    public static Item HUMAN_FIGHTER_SPAWN_EGG;
    public static Item HUMAN_WARSHIP_SPAWN_EGG;

    public static Item CONSTRUCTION_TRIPOD_SPAWN_EGG;

    public static Item SMOKE_TRIPOD_SPAWN_EGG;

    public static Item BOMB_TRIPOD_SPAWN_EGG;

    public static Item CONSTRICTOR_SPAWN_EGG;

    public static Item XENO_TELEPATH_SPAWN_EGG;

    public static Item CAMERON_SPAWN_EGG;

    public static Item GOREY_SPAWN_EGG;

    public static Item WAYNE_SPAWN_EGG;

    public static Item GOBLE_SPAWN_EGG;

    public static Item BBC2019_SPAWN_EGG;

    public static Item ATTACK2021_SPAWN_EGG;

    public static Item ATTACK_SPAWN_EGG;

    public static Item VEGETABLE_SPAWN_EGG;

    public static Item MEXICAN_SPAWN_EGG;

    public static Item FOUR_LEGGED_SPAWN_EGG;

    public static Item HUMAN_HELICOPTER_SPAWN_EGG;

    public static Item HUMAN_SUBMARINE_SPAWN_EGG;

    public static Item HUMAN_BOMBER_SPAWN_EGG;

    public static Item WATER_TRIPOD_SPAWN_EGG;
    public static Item SCOUT_TRIPOD_SPAWN_EGG;
    public static Item RAM_TRIPOD_SPAWN_EGG;
    public static Item UBERPOD_SPAWN_EGG;
    public static Item BUNKER_TRIPOD_SPAWN_EGG;
    public static Item FLAMER_TRIPOD_SPAWN_EGG;
    public static Item TANK_TRIPOD_SPAWN_EGG;
    public static Item TAXI_TRIPOD_SPAWN_EGG;

    public static Item HEAT_RAY_EMITTER;

    public static Item MARTIAN_CYLINDER;

    public static Item MARTIAN_ALLOY_BLOCK;

    public static Item MARTIAN_CAPSULE;

    public static Item WHITE_MARTIAN_ALLOY;

    public static Item MARTIAN_GLASS;

    public static Item BLOOD_ROOTS;

    public static Item TRIPOD_BLUEPRINT;

    public static Item MARTIAN_HULL;

    public static Item MARTIAN_CONSOLE;

    public static Item MARTIAN_LAMP;

    public static Item MARTIAN_CRATE;

    public static Item HUMAN_CRATE;

    public static Item MILITARY_CRATE;

    public static Item CAMERON_STEEL_BLOCK;

    public static Item GOBLE_BRASS_BLOCK;

    public static Item GOREY_BONE_BLOCK;

    public static Item VEGETABLE_HUSK_BLOCK;

    public static Item MARTIAN_SIGNAL_BEACON;

    public static Item SPORE_SACK;

    public static Item MARTIAN_TURRET;

    public static Item BLOOD_VINES;

    public static Item INFESTED_COBBLESTONE;

    public static Item INFESTED_BRICKS;

    public static Item ARC_RIFLE;

    public static Item ALLOY_CANNON;

    /* The human arsenal: two guns, a rocket launcher and one very large bomb. */

    public static Item PISTOL;

    public static Item MACHINE_GUN;

    public static Item ROCKET_LAUNCHER;

    public static Item NUCLEAR_BOMB;

    public static final CreativeModeTab ITEM_GROUP = CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.wotw"))
            .icon(() -> new ItemStack(TRIPOD_2005_SPAWN_EGG))
            .displayItems((parameters, entries) -> {
                        entries.accept(TRIPOD_2005_SPAWN_EGG);
                        entries.accept(BBC_WHITE_TRIPOD_SPAWN_EGG);
                        entries.accept(BBC_RED_TRIPOD_SPAWN_EGG);
                        entries.accept(FLYING_MANTA_SPAWN_EGG);
                        entries.accept(BOOK_TRIPOD_SPAWN_EGG);
                        entries.accept(ASYLUM_WALKER_SPAWN_EGG);
                        entries.accept(GOLIATH_TRIPOD_SPAWN_EGG);
                        entries.accept(MARTIAN_HERON_SPAWN_EGG);
                        entries.accept(MARTIAN_SPIDER_SPAWN_EGG);
                        entries.accept(MARTIAN_LOUSE_SPAWN_EGG);
                        entries.accept(MARTIAN_CYLINDER);
                        entries.accept(MARTIAN_ALLOY_BLOCK);
                        entries.accept(MARTIAN_CAPSULE);

                        entries.accept(FLYING_MACHINE_SPAWN_EGG);
                        entries.accept(ELECTRIC_MACHINE_SPAWN_EGG);
                        entries.accept(HANDLING_MACHINE_SPAWN_EGG);
                        entries.accept(DIGGING_MACHINE_SPAWN_EGG);
                        entries.accept(TEMPEST_SPAWN_EGG);
                        entries.accept(BOMBARDING_MACHINE_SPAWN_EGG);
                        entries.accept(DRONE_SPAWN_EGG);
                        entries.accept(PILOT_TRIPOD_SPAWN_EGG);
                        entries.accept(WHITE_MARTIAN_ALLOY);
                        entries.accept(MARTIAN_GLASS);
                        entries.accept(BLOOD_ROOTS);
                        entries.accept(TRIPOD_BLUEPRINT);
                        entries.accept(ARC_RIFLE);
                        entries.accept(ALLOY_CANNON);
                        entries.accept(PISTOL);
                        entries.accept(MACHINE_GUN);
                        entries.accept(ROCKET_LAUNCHER);
                        entries.accept(NUCLEAR_BOMB);
                        entries.accept(HUMAN_SOLDIER_SPAWN_EGG);
                        entries.accept(HUMAN_TANK_SPAWN_EGG);
                        entries.accept(HUMAN_FIGHTER_SPAWN_EGG);
                        entries.accept(HUMAN_WARSHIP_SPAWN_EGG);
                        entries.accept(CONSTRUCTION_TRIPOD_SPAWN_EGG);
                        entries.accept(SMOKE_TRIPOD_SPAWN_EGG);
                        entries.accept(BOMB_TRIPOD_SPAWN_EGG);
                        entries.accept(CONSTRICTOR_SPAWN_EGG);
                        entries.accept(XENO_TELEPATH_SPAWN_EGG);
                        entries.accept(CAMERON_SPAWN_EGG);
                        entries.accept(GOREY_SPAWN_EGG);
                        entries.accept(WAYNE_SPAWN_EGG);
                        entries.accept(GOBLE_SPAWN_EGG);
                        entries.accept(BBC2019_SPAWN_EGG);
                        entries.accept(ATTACK2021_SPAWN_EGG);
                        entries.accept(ATTACK_SPAWN_EGG);
                        entries.accept(VEGETABLE_SPAWN_EGG);
                        entries.accept(MEXICAN_SPAWN_EGG);
                        entries.accept(FOUR_LEGGED_SPAWN_EGG);
                        entries.accept(HUMAN_HELICOPTER_SPAWN_EGG);
                        entries.accept(HUMAN_SUBMARINE_SPAWN_EGG);
                        entries.accept(HUMAN_BOMBER_SPAWN_EGG);
                        entries.accept(WATER_TRIPOD_SPAWN_EGG);
                        entries.accept(SCOUT_TRIPOD_SPAWN_EGG);
                        entries.accept(RAM_TRIPOD_SPAWN_EGG);
                        entries.accept(UBERPOD_SPAWN_EGG);
                        entries.accept(BUNKER_TRIPOD_SPAWN_EGG);
                        entries.accept(FLAMER_TRIPOD_SPAWN_EGG);
                        entries.accept(TANK_TRIPOD_SPAWN_EGG);
                        entries.accept(TAXI_TRIPOD_SPAWN_EGG);
                        entries.accept(MARTIAN_HULL);
                        entries.accept(MARTIAN_CONSOLE);
                        entries.accept(MARTIAN_LAMP);
                        entries.accept(CAMERON_STEEL_BLOCK);
                        entries.accept(GOBLE_BRASS_BLOCK);
                        entries.accept(GOREY_BONE_BLOCK);
                        entries.accept(VEGETABLE_HUSK_BLOCK);
                        entries.accept(MARTIAN_SIGNAL_BEACON);
                        entries.accept(SPORE_SACK);
                        entries.accept(MARTIAN_TURRET);
                        entries.accept(MARTIAN_CRATE);
                        entries.accept(HUMAN_CRATE);
                        entries.accept(MILITARY_CRATE);
                        entries.accept(BLOOD_VINES);
                        entries.accept(INFESTED_COBBLESTONE);
                        entries.accept(INFESTED_BRICKS);
                        entries.accept(MARTIAN_ALLOY);
                        entries.accept(HEAT_RAY_EMITTER);
            })
            .build();

    /*
     * The items are built here, not next to the fields: Forge freezes the vanilla item registry before
     * a mod is constructed and {@code Item} registers a holder for itself in its own constructor. Doing
     * the work inside the supplier keeps it in the register event, where Forge expects it, and every
     * field above is ready by the time the game asks for it.
     *
     * <p>The spawn eggs are Forge's own {@code ForgeSpawnEggItem}: it wants a supplier of the entity
     * type because items are registered before entity types, so the type is not known yet.</p>
     */
    static {
        TABS.register("main", () -> ITEM_GROUP);
        ITEMS.register("martian_alloy", () -> MARTIAN_ALLOY = new Item(new Item.Properties()));
        ITEMS.register("tripod_2005_spawn_egg",
                () -> TRIPOD_2005_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.TRIPOD_2005, 0x3D4038, 0xC9F5C0,
                        new Item.Properties()));
        ITEMS.register("bbc_white_tripod_spawn_egg",
                () -> BBC_WHITE_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.BBC_WHITE_TRIPOD, 0xD8D5C8, 0xFFFCE8,
                        new Item.Properties()));
        ITEMS.register("bbc_red_tripod_spawn_egg",
                () -> BBC_RED_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.BBC_RED_TRIPOD, 0x8E2B22, 0xFF5A3C,
                        new Item.Properties()));
        ITEMS.register("flying_manta_spawn_egg",
                () -> FLYING_MANTA_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.FLYING_MANTA, 0x4A4F52, 0x9CFF7A,
                        new Item.Properties()));
        ITEMS.register("book_tripod_spawn_egg",
                () -> BOOK_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.BOOK_TRIPOD, 0x5A5A4A, 0xD8C38A,
                        new Item.Properties()));
        ITEMS.register("asylum_walker_spawn_egg",
                () -> ASYLUM_WALKER_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.ASYLUM_WALKER, 0x3B2B20, 0x6DFF4A,
                        new Item.Properties()));
        ITEMS.register("goliath_tripod_spawn_egg",
                () -> GOLIATH_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.GOLIATH_TRIPOD, 0x7A5A2E, 0xFFB347,
                        new Item.Properties()));
        ITEMS.register("martian_heron_spawn_egg",
                () -> MARTIAN_HERON_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.MARTIAN_HERON, 0xB9B3A2, 0xFF2A1A,
                        new Item.Properties()));
        ITEMS.register("martian_spider_spawn_egg",
                () -> MARTIAN_SPIDER_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.MARTIAN_SPIDER, 0x2C2620, 0xFF4A1F,
                        new Item.Properties()));
        ITEMS.register("martian_louse_spawn_egg",
                () -> MARTIAN_LOUSE_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.MARTIAN_LOUSE, 0x5C3A24, 0xFFCF5A,
                        new Item.Properties()));
        ITEMS.register("martian_spawn_egg",
                () -> MARTIAN_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.MARTIAN, 0x68604A, 0xFF764A,
                        new Item.Properties()));
        ITEMS.register("martian_master_spawn_egg",
                () -> MARTIAN_MASTER_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.MARTIAN_MASTER, 0x4A3E3E, 0xFF4030,
                        new Item.Properties()));
        ITEMS.register("flying_machine_spawn_egg",
                () -> FLYING_MACHINE_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.FLYING_MACHINE, 0x5A6470, 0x96FF78,
                        new Item.Properties()));
        ITEMS.register("electric_machine_spawn_egg",
                () -> ELECTRIC_MACHINE_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.ELECTRIC_MACHINE, 0x3E4248, 0x8CF0FF,
                        new Item.Properties()));
        ITEMS.register("handling_machine_spawn_egg",
                () -> HANDLING_MACHINE_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.HANDLING_MACHINE, 0x6B6152, 0xFFE278,
                        new Item.Properties()));
        ITEMS.register("digging_machine_spawn_egg",
                () -> DIGGING_MACHINE_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.DIGGING_MACHINE, 0x7C6040, 0xFFAA50,
                        new Item.Properties()));
        ITEMS.register("tempest_spawn_egg",
                () -> TEMPEST_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.TEMPEST, 0x6F6A60, 0xFF7838,
                        new Item.Properties()));
        ITEMS.register("bombarding_machine_spawn_egg",
                () -> BOMBARDING_MACHINE_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.BOMBARDING_MACHINE, 0x585C40, 0xFFBE46,
                        new Item.Properties()));
        ITEMS.register("drone_spawn_egg",
                () -> DRONE_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.DRONE, 0x8A8E94, 0xBE6EFF,
                        new Item.Properties()));
        ITEMS.register("pilot_tripod_spawn_egg",
                () -> PILOT_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.PILOT_TRIPOD, 0x5C685E, 0x8CFFAA,
                        new Item.Properties()));
        ITEMS.register("human_soldier_spawn_egg",
                () -> HUMAN_SOLDIER_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.HUMAN_SOLDIER, 0x5E6B4A, 0xC9B98A,
                        new Item.Properties()));
        ITEMS.register("human_tank_spawn_egg",
                () -> HUMAN_TANK_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.HUMAN_TANK, 0x4A5238, 0x8E9A6A,
                        new Item.Properties()));
        ITEMS.register("human_fighter_spawn_egg",
                () -> HUMAN_FIGHTER_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.HUMAN_FIGHTER, 0x6B7078, 0xB9C2CC,
                        new Item.Properties()));
        ITEMS.register("construction_tripod_spawn_egg",
                () -> CONSTRUCTION_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.CONSTRUCTION_TRIPOD,
                        0x6A6250, 0xFFC45A, new Item.Properties()));
        ITEMS.register("smoke_tripod_spawn_egg",
                () -> SMOKE_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.SMOKE_TRIPOD,
                        0x3A3A3E, 0xFF843C, new Item.Properties()));
        ITEMS.register("bomb_tripod_spawn_egg",
                () -> BOMB_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.BOMB_TRIPOD,
                        0x54524A, 0xFF7846, new Item.Properties()));
        ITEMS.register("constrictor_spawn_egg",
                () -> CONSTRICTOR_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.CONSTRICTOR,
                        0x4C604A, 0xA8FF8C, new Item.Properties()));
        ITEMS.register("xeno_telepath_spawn_egg",
                () -> XENO_TELEPATH_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.XENO_TELEPATH,
                        0x464A68, 0xD09CFF, new Item.Properties()));
        ITEMS.register("cameron_tripod_spawn_egg",
                () -> CAMERON_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.CAMERON,
                        0x6E6250, 0xFFBC5C, new Item.Properties()));
        ITEMS.register("gorey_tripod_spawn_egg",
                () -> GOREY_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.GOREY,
                        0x2A2A2E, 0xFFF4C4, new Item.Properties()));
        ITEMS.register("wayne_tripod_spawn_egg",
                () -> WAYNE_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.WAYNE,
                        0x7A7E88, 0x6CFF8E, new Item.Properties()));
        ITEMS.register("goble_tripod_spawn_egg",
                () -> GOBLE_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.GOBLE,
                        0xAA8E56, 0xFFD682, new Item.Properties()));
        ITEMS.register("bbc2019_tripod_spawn_egg",
                () -> BBC2019_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.BBC2019,
                        0x36383C, 0x96D2FF, new Item.Properties()));
        ITEMS.register("attack2021_tripod_spawn_egg",
                () -> ATTACK2021_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.ATTACK2021,
                        0x262428, 0xFF3A3A, new Item.Properties()));
        ITEMS.register("attack_tripod_spawn_egg",
                () -> ATTACK_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.ATTACK,
                        0x2E302A, 0xFF483A, new Item.Properties()));
        ITEMS.register("vegetable_tripod_spawn_egg",
                () -> VEGETABLE_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.VEGETABLE,
                        0x98B67A, 0xCCFF98, new Item.Properties()));
        ITEMS.register("mexican_tripod_spawn_egg",
                () -> MEXICAN_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.MEXICAN,
                        0x2A302A, 0xFFF252, new Item.Properties()));
        ITEMS.register("four_legged_tripod_spawn_egg",
                () -> FOUR_LEGGED_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.FOUR_LEGGED,
                        0xB26242, 0xFFF07A, new Item.Properties()));
        ITEMS.register("human_helicopter_spawn_egg",
                () -> HUMAN_HELICOPTER_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.HUMAN_HELICOPTER, 0x55603F, 0xA9B27C,
                        new Item.Properties()));
        ITEMS.register("human_submarine_spawn_egg",
                () -> HUMAN_SUBMARINE_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.HUMAN_SUBMARINE, 0x2C3A46, 0x8FB0C4,
                        new Item.Properties()));
        ITEMS.register("human_bomber_spawn_egg",
                () -> HUMAN_BOMBER_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.HUMAN_BOMBER, 0x4A4E52, 0xC2C8CE,
                        new Item.Properties()));
        ITEMS.register("human_warship_spawn_egg",
                () -> HUMAN_WARSHIP_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.HUMAN_WARSHIP, 0x3E464E, 0x9AA6B0,
                        new Item.Properties()));
        ITEMS.register("water_tripod_spawn_egg",
                () -> WATER_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.WATER_TRIPOD, 0x2E4A4A, 0x8CF0D0,
                        new Item.Properties()));
        ITEMS.register("scout_tripod_spawn_egg",
                () -> SCOUT_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.SCOUT_TRIPOD, 0x4A4E44, 0xD8A85A,
                        new Item.Properties()));
        ITEMS.register("ram_tripod_spawn_egg",
                () -> RAM_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.RAM_TRIPOD, 0x5A4232, 0xFF7040,
                        new Item.Properties()));
        ITEMS.register("uberpod_spawn_egg",
                () -> UBERPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.UBERPOD, 0x585C5A, 0x9CFFB0,
                        new Item.Properties()));
        ITEMS.register("bunker_tripod_spawn_egg",
                () -> BUNKER_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.BUNKER_TRIPOD, 0x6E7076, 0xD0D6DE,
                        new Item.Properties()));
        ITEMS.register("flamer_tripod_spawn_egg",
                () -> FLAMER_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.FLAMER_TRIPOD, 0x5E4A3A, 0xFF9A30,
                        new Item.Properties()));
        ITEMS.register("tank_tripod_spawn_egg",
                () -> TANK_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.TANK_TRIPOD, 0x3E4436, 0xE0A050,
                        new Item.Properties()));
        ITEMS.register("taxi_tripod_spawn_egg",
                () -> TAXI_TRIPOD_SPAWN_EGG = new ForgeSpawnEggItem(() -> ModEntities.TAXI_TRIPOD, 0x6A6250, 0xC8B88C,
                        new Item.Properties()));
        ITEMS.register("heat_ray_emitter",
                () -> HEAT_RAY_EMITTER = new HeatRayEmitterItem(new Item.Properties().stacksTo(1).durability(384)));
        ITEMS.register("martian_cylinder",
                () -> MARTIAN_CYLINDER = new BlockItem(ModBlocks.MARTIAN_CYLINDER, new Item.Properties()));
        ITEMS.register("martian_alloy_block",
                () -> MARTIAN_ALLOY_BLOCK = new BlockItem(ModBlocks.MARTIAN_ALLOY_BLOCK, new Item.Properties()));
        ITEMS.register("martian_capsule",
                () -> MARTIAN_CAPSULE = new MartianCapsuleItem(new Item.Properties().stacksTo(16)));
        ITEMS.register("white_martian_alloy",
                () -> WHITE_MARTIAN_ALLOY = new BlockItem(ModBlocks.WHITE_MARTIAN_ALLOY, new Item.Properties()));
        ITEMS.register("martian_glass",
                () -> MARTIAN_GLASS = new BlockItem(ModBlocks.MARTIAN_GLASS, new Item.Properties()));
        ITEMS.register("blood_roots", () -> BLOOD_ROOTS = new BlockItem(ModBlocks.BLOOD_ROOTS, new Item.Properties()));
        ITEMS.register("tripod_blueprint",
                () -> TRIPOD_BLUEPRINT = new BlockItem(ModBlocks.TRIPOD_BLUEPRINT, new Item.Properties()));
        ITEMS.register("martian_hull",
                () -> MARTIAN_HULL = new BlockItem(ModBlocks.MARTIAN_HULL, new Item.Properties()));
        ITEMS.register("martian_console",
                () -> MARTIAN_CONSOLE = new BlockItem(ModBlocks.MARTIAN_CONSOLE, new Item.Properties()));
        ITEMS.register("martian_lamp",
                () -> MARTIAN_LAMP = new BlockItem(ModBlocks.MARTIAN_LAMP, new Item.Properties()));
        ITEMS.register("martian_crate",
                () -> MARTIAN_CRATE = new BlockItem(ModBlocks.MARTIAN_CRATE, new Item.Properties()));
        ITEMS.register("human_crate", () -> HUMAN_CRATE = new BlockItem(ModBlocks.HUMAN_CRATE, new Item.Properties()));
        ITEMS.register("military_crate",
                () -> MILITARY_CRATE = new BlockItem(ModBlocks.MILITARY_CRATE, new Item.Properties()));
        ITEMS.register("cameron_steel_block",
                () -> CAMERON_STEEL_BLOCK = new BlockItem(ModBlocks.CAMERON_STEEL_BLOCK, new Item.Properties()));
        ITEMS.register("goble_brass_block",
                () -> GOBLE_BRASS_BLOCK = new BlockItem(ModBlocks.GOBLE_BRASS_BLOCK, new Item.Properties()));
        ITEMS.register("gorey_bone_block",
                () -> GOREY_BONE_BLOCK = new BlockItem(ModBlocks.GOREY_BONE_BLOCK, new Item.Properties()));
        ITEMS.register("vegetable_husk_block",
                () -> VEGETABLE_HUSK_BLOCK = new BlockItem(ModBlocks.VEGETABLE_HUSK_BLOCK, new Item.Properties()));
        ITEMS.register("martian_signal_beacon",
                () -> MARTIAN_SIGNAL_BEACON = new BlockItem(ModBlocks.MARTIAN_SIGNAL_BEACON,
                        new Item.Properties()));
        ITEMS.register("spore_sack",
                () -> SPORE_SACK = new BlockItem(ModBlocks.SPORE_SACK, new Item.Properties()));
        ITEMS.register("martian_turret",
                () -> MARTIAN_TURRET = new BlockItem(ModBlocks.MARTIAN_TURRET, new Item.Properties()));
        ITEMS.register("blood_vines", () -> BLOOD_VINES = new BlockItem(ModBlocks.BLOOD_VINES, new Item.Properties()));
        ITEMS.register("infested_cobblestone",
                () -> INFESTED_COBBLESTONE = new BlockItem(ModBlocks.INFESTED_COBBLESTONE, new Item.Properties()));
        ITEMS.register("infested_bricks",
                () -> INFESTED_BRICKS = new BlockItem(ModBlocks.INFESTED_BRICKS, new Item.Properties()));
        ITEMS.register("arc_rifle",
                () -> ARC_RIFLE = new ArcRifleItem(new Item.Properties().stacksTo(1).durability(320)));
        ITEMS.register("alloy_cannon",
                () -> ALLOY_CANNON = new AlloyCannonItem(new Item.Properties().stacksTo(1).durability(220)));
        ITEMS.register("pistol", () -> PISTOL = new PistolItem(new Item.Properties().stacksTo(1).durability(260)));
        ITEMS.register("machine_gun",
                () -> MACHINE_GUN = new MachineGunItem(new Item.Properties().stacksTo(1).durability(640)));
        ITEMS.register("rocket_launcher",
                () -> ROCKET_LAUNCHER = new RocketLauncherItem(new Item.Properties().stacksTo(1).durability(90)));
        ITEMS.register("nuclear_bomb", () -> NUCLEAR_BOMB = new NuclearBombItem(new Item.Properties().stacksTo(16)));
    }

    private ModItems() {
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        TABS.register(bus);
        WotwMod.LOGGER.debug("Registered items and the creative tab");
    }
}
