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
