package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.item.HeatRayEmitterItem;
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

    public static final Item HEAT_RAY_EMITTER = register("heat_ray_emitter",
            new HeatRayEmitterItem(new Item.Settings().maxCount(1).maxDamage(384)));

    public static final Item MARTIAN_CYLINDER = register("martian_cylinder",
            new BlockItem(ModBlocks.MARTIAN_CYLINDER, new Item.Settings()));

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
