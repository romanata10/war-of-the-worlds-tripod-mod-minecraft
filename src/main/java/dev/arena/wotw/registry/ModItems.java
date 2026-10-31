package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.item.AlloyCannonItem;
import dev.arena.wotw.item.ArcRifleItem;
import dev.arena.wotw.item.HeatRayEmitterItem;
import dev.arena.wotw.item.MartianCapsuleItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

/** Items: spawn eggs, the alloy the tripods drop, the lootable heat ray and the cylinder block item. */
public final class ModItems {

    public static final Item MARTIAN_ALLOY = register("martian_alloy", new Item(new Item.Settings()));

    public static final Item TRIPOD_2005_SPAWN_EGG = spawnEgg("tripod_2005_spawn_egg", ModEntities.TRIPOD_2005, 0x3D4038, 0xC9F5C0);
    public static final Item BBC_WHITE_TRIPOD_SPAWN_EGG = spawnEgg("bbc_white_tripod_spawn_egg", ModEntities.BBC_WHITE_TRIPOD, 0xD8D5C8, 0xFFFCE8);
    public static final Item BBC_RED_TRIPOD_SPAWN_EGG = spawnEgg("bbc_red_tripod_spawn_egg", ModEntities.BBC_RED_TRIPOD, 0x8E2B22, 0xFF5A3C);
    public static final Item FLYING_MANTA_SPAWN_EGG = spawnEgg("flying_manta_spawn_egg", ModEntities.FLYING_MANTA, 0x4A4F52, 0x9CFF7A);
    public static final Item BOOK_TRIPOD_SPAWN_EGG = spawnEgg("book_tripod_spawn_egg", ModEntities.BOOK_TRIPOD, 0x5A5A4A, 0xD8C38A);
    public static final Item ASYLUM_WALKER_SPAWN_EGG = spawnEgg("asylum_walker_spawn_egg", ModEntities.ASYLUM_WALKER, 0x3B2B20, 0x6DFF4A);
    public static final Item GOLIATH_TRIPOD_SPAWN_EGG = spawnEgg("goliath_tripod_spawn_egg", ModEntities.GOLIATH_TRIPOD, 0x7A5A2E, 0xFFB347);
    public static final Item MARTIAN_HERON_SPAWN_EGG = spawnEgg("martian_heron_spawn_egg", ModEntities.MARTIAN_HERON, 0xB9B3A2, 0xFF2A1A);
    public static final Item MARTIAN_SPIDER_SPAWN_EGG = spawnEgg("martian_spider_spawn_egg", ModEntities.MARTIAN_SPIDER, 0x2C2620, 0xFF4A1F);
    public static final Item MARTIAN_LOUSE_SPAWN_EGG = spawnEgg("martian_louse_spawn_egg", ModEntities.MARTIAN_LOUSE, 0x5C3A24, 0xFFCF5A);
    public static final Item MARTIAN_SPAWN_EGG = spawnEgg("martian_spawn_egg", ModEntities.MARTIAN, 0x68604A, 0xFF764A);
    public static final Item MARTIAN_MASTER_SPAWN_EGG = spawnEgg("martian_master_spawn_egg", ModEntities.MARTIAN_MASTER, 0x4A3E3E, 0xFF4030);

    public static final Item FLYING_MACHINE_SPAWN_EGG = spawnEgg("flying_machine_spawn_egg", ModEntities.FLYING_MACHINE, 0x5A6470, 0x96FF78);
    public static final Item ELECTRIC_MACHINE_SPAWN_EGG = spawnEgg("electric_machine_spawn_egg", ModEntities.ELECTRIC_MACHINE, 0x3E4248, 0x8CF0FF);
    public static final Item HANDLING_MACHINE_SPAWN_EGG = spawnEgg("handling_machine_spawn_egg", ModEntities.HANDLING_MACHINE, 0x6B6152, 0xFFE278);
    public static final Item DIGGING_MACHINE_SPAWN_EGG = spawnEgg("digging_machine_spawn_egg", ModEntities.DIGGING_MACHINE, 0x7C6040, 0xFFAA50);
    public static final Item TEMPEST_SPAWN_EGG = spawnEgg("tempest_spawn_egg", ModEntities.TEMPEST, 0x6F6A60, 0xFF7838);
    public static final Item BOMBARDING_MACHINE_SPAWN_EGG = spawnEgg("bombarding_machine_spawn_egg", ModEntities.BOMBARDING_MACHINE, 0x585C40, 0xFFBE46);
    public static final Item DRONE_SPAWN_EGG = spawnEgg("drone_spawn_egg", ModEntities.DRONE, 0x8A8E94, 0xBE6EFF);
    public static final Item PILOT_TRIPOD_SPAWN_EGG = spawnEgg("pilot_tripod_spawn_egg", ModEntities.PILOT_TRIPOD, 0x5C685E, 0x8CFFAA);

    public static final Item HUMAN_SOLDIER_SPAWN_EGG = spawnEgg("human_soldier_spawn_egg", ModEntities.HUMAN_SOLDIER, 0x5E6B4A, 0xC9B98A);
    public static final Item HUMAN_TANK_SPAWN_EGG = spawnEgg("human_tank_spawn_egg", ModEntities.HUMAN_TANK, 0x4A5238, 0x8E9A6A);
    public static final Item HUMAN_FIGHTER_SPAWN_EGG = spawnEgg("human_fighter_spawn_egg", ModEntities.HUMAN_FIGHTER, 0x6B7078, 0xB9C2CC);
    public static final Item HUMAN_WARSHIP_SPAWN_EGG = spawnEgg("human_warship_spawn_egg", ModEntities.HUMAN_WARSHIP, 0x3E464E, 0x9AA6B0);

    public static final Item WATER_TRIPOD_SPAWN_EGG = spawnEgg("water_tripod_spawn_egg", ModEntities.WATER_TRIPOD, 0x2E4A4A, 0x8CF0D0);
    public static final Item SCOUT_TRIPOD_SPAWN_EGG = spawnEgg("scout_tripod_spawn_egg", ModEntities.SCOUT_TRIPOD, 0x4A4E44, 0xD8A85A);
    public static final Item RAM_TRIPOD_SPAWN_EGG = spawnEgg("ram_tripod_spawn_egg", ModEntities.RAM_TRIPOD, 0x5A4232, 0xFF7040);
    public static final Item UBERPOD_SPAWN_EGG = spawnEgg("uberpod_spawn_egg", ModEntities.UBERPOD, 0x585C5A, 0x9CFFB0);
    public static final Item BUNKER_TRIPOD_SPAWN_EGG = spawnEgg("bunker_tripod_spawn_egg", ModEntities.BUNKER_TRIPOD, 0x6E7076, 0xD0D6DE);
    public static final Item FLAMER_TRIPOD_SPAWN_EGG = spawnEgg("flamer_tripod_spawn_egg", ModEntities.FLAMER_TRIPOD, 0x5E4A3A, 0xFF9A30);
    public static final Item TANK_TRIPOD_SPAWN_EGG = spawnEgg("tank_tripod_spawn_egg", ModEntities.TANK_TRIPOD, 0x3E4436, 0xE0A050);
    public static final Item TAXI_TRIPOD_SPAWN_EGG = spawnEgg("taxi_tripod_spawn_egg", ModEntities.TAXI_TRIPOD, 0x6A6250, 0xC8B88C);

    public static final Item HEAT_RAY_EMITTER = register("heat_ray_emitter",
            new HeatRayEmitterItem(new Item.Settings().maxCount(1).maxDamage(384)));

    public static final Item MARTIAN_CYLINDER = register("martian_cylinder",
            new BlockItem(ModBlocks.MARTIAN_CYLINDER, new Item.Settings()));

    public static final Item MARTIAN_ALLOY_BLOCK = register("martian_alloy_block",
            new BlockItem(ModBlocks.MARTIAN_ALLOY_BLOCK, new Item.Settings()));

    public static final Item MARTIAN_CAPSULE = register("martian_capsule",
            new MartianCapsuleItem(new Item.Settings().maxCount(16)));

    public static final Item WHITE_MARTIAN_ALLOY = register("white_martian_alloy",
            new BlockItem(ModBlocks.WHITE_MARTIAN_ALLOY, new Item.Settings()));

    public static final Item MARTIAN_GLASS = register("martian_glass",
            new BlockItem(ModBlocks.MARTIAN_GLASS, new Item.Settings()));

    public static final Item BLOOD_ROOTS = register("blood_roots",
            new BlockItem(ModBlocks.BLOOD_ROOTS, new Item.Settings()));

    public static final Item TRIPOD_BLUEPRINT = register("tripod_blueprint",
            new BlockItem(ModBlocks.TRIPOD_BLUEPRINT, new Item.Settings()));

    public static final Item MARTIAN_HULL = register("martian_hull",
            new BlockItem(ModBlocks.MARTIAN_HULL, new Item.Settings()));

    public static final Item MARTIAN_CONSOLE = register("martian_console",
            new BlockItem(ModBlocks.MARTIAN_CONSOLE, new Item.Settings()));

    public static final Item MARTIAN_LAMP = register("martian_lamp",
            new BlockItem(ModBlocks.MARTIAN_LAMP, new Item.Settings()));

    public static final Item MARTIAN_CRATE = register("martian_crate",
            new BlockItem(ModBlocks.MARTIAN_CRATE, new Item.Settings()));

    public static final Item HUMAN_CRATE = register("human_crate",
            new BlockItem(ModBlocks.HUMAN_CRATE, new Item.Settings()));

    public static final Item MILITARY_CRATE = register("military_crate",
            new BlockItem(ModBlocks.MILITARY_CRATE, new Item.Settings()));

    public static final Item MARTIAN_TURRET = register("martian_turret",
            new BlockItem(ModBlocks.MARTIAN_TURRET, new Item.Settings()));

    public static final Item BLOOD_VINES = register("blood_vines",
            new BlockItem(ModBlocks.BLOOD_VINES, new Item.Settings()));

    public static final Item INFESTED_COBBLESTONE = register("infested_cobblestone",
            new BlockItem(ModBlocks.INFESTED_COBBLESTONE, new Item.Settings()));

    public static final Item INFESTED_BRICKS = register("infested_bricks",
            new BlockItem(ModBlocks.INFESTED_BRICKS, new Item.Settings()));

    public static final Item ARC_RIFLE = register("arc_rifle",
            new ArcRifleItem(new Item.Settings().maxCount(1).maxDamage(320)));

    public static final Item ALLOY_CANNON = register("alloy_cannon",
            new AlloyCannonItem(new Item.Settings().maxCount(1).maxDamage(220)));

    public static final ItemGroup ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, WotwMod.id("main"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup.wotw"))
                    .icon(() -> new ItemStack(TRIPOD_2005_SPAWN_EGG))
                    .entries((context, entries) -> {
                        entries.add(TRIPOD_2005_SPAWN_EGG);
                        entries.add(BBC_WHITE_TRIPOD_SPAWN_EGG);
                        entries.add(BBC_RED_TRIPOD_SPAWN_EGG);
                        entries.add(FLYING_MANTA_SPAWN_EGG);
                        entries.add(BOOK_TRIPOD_SPAWN_EGG);
                        entries.add(ASYLUM_WALKER_SPAWN_EGG);
                        entries.add(GOLIATH_TRIPOD_SPAWN_EGG);
                        entries.add(MARTIAN_HERON_SPAWN_EGG);
                        entries.add(MARTIAN_SPIDER_SPAWN_EGG);
                        entries.add(MARTIAN_LOUSE_SPAWN_EGG);
                        entries.add(MARTIAN_CYLINDER);
                        entries.add(MARTIAN_ALLOY_BLOCK);
                        entries.add(MARTIAN_CAPSULE);

                        entries.add(FLYING_MACHINE_SPAWN_EGG);
                        entries.add(ELECTRIC_MACHINE_SPAWN_EGG);
                        entries.add(HANDLING_MACHINE_SPAWN_EGG);
                        entries.add(DIGGING_MACHINE_SPAWN_EGG);
                        entries.add(TEMPEST_SPAWN_EGG);
                        entries.add(BOMBARDING_MACHINE_SPAWN_EGG);
                        entries.add(DRONE_SPAWN_EGG);
                        entries.add(PILOT_TRIPOD_SPAWN_EGG);
                        entries.add(WHITE_MARTIAN_ALLOY);
                        entries.add(MARTIAN_GLASS);
                        entries.add(BLOOD_ROOTS);
                        entries.add(TRIPOD_BLUEPRINT);
                        entries.add(ARC_RIFLE);
                        entries.add(ALLOY_CANNON);
                        entries.add(HUMAN_SOLDIER_SPAWN_EGG);
                        entries.add(HUMAN_TANK_SPAWN_EGG);
                        entries.add(HUMAN_FIGHTER_SPAWN_EGG);
                        entries.add(HUMAN_WARSHIP_SPAWN_EGG);
                        entries.add(WATER_TRIPOD_SPAWN_EGG);
                        entries.add(SCOUT_TRIPOD_SPAWN_EGG);
                        entries.add(RAM_TRIPOD_SPAWN_EGG);
                        entries.add(UBERPOD_SPAWN_EGG);
                        entries.add(BUNKER_TRIPOD_SPAWN_EGG);
                        entries.add(FLAMER_TRIPOD_SPAWN_EGG);
                        entries.add(TANK_TRIPOD_SPAWN_EGG);
                        entries.add(TAXI_TRIPOD_SPAWN_EGG);
                        entries.add(MARTIAN_HULL);
                        entries.add(MARTIAN_CONSOLE);
                        entries.add(MARTIAN_LAMP);
                        entries.add(MARTIAN_TURRET);
                        entries.add(MARTIAN_CRATE);
                        entries.add(HUMAN_CRATE);
                        entries.add(MILITARY_CRATE);
                        entries.add(BLOOD_VINES);
                        entries.add(INFESTED_COBBLESTONE);
                        entries.add(INFESTED_BRICKS);
                        entries.add(MARTIAN_ALLOY);
                        entries.add(HEAT_RAY_EMITTER);
                    })
                    .build());

    private ModItems() {
    }

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, WotwMod.id(name), item);
    }

    private static Item spawnEgg(String name, EntityType<? extends MobEntity> type,
                                 int primaryColor, int secondaryColor) {
        return register(name, new SpawnEggItem(type, primaryColor, secondaryColor, new Item.Settings()));
    }

    public static void register() {
        WotwMod.LOGGER.debug("Registered items, creative tab {}", Registries.ITEM_GROUP.getId(ITEM_GROUP));
    }
}
