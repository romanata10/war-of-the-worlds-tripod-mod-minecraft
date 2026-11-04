package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.block.MartianCylinderBlock;
import dev.arena.wotw.block.BloodRootsBlock;
import dev.arena.wotw.block.BloodVinesBlock;
import dev.arena.wotw.block.InfestedBlock;
import dev.arena.wotw.block.MartianTurretBlock;
import dev.arena.wotw.block.RedRootBlock;
import dev.arena.wotw.block.TripodBlueprintBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/** Blocks of the mod: the Martian cylinder, the alloy the machines are plated with and the roots. */
public final class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, WotwMod.MOD_ID);

    public static final Block MARTIAN_CYLINDER = register("martian_cylinder", new MartianCylinderBlock(BlockBehaviour.Properties.of()
                    .strength(6.0F, 12.0F)
                    .sound(SoundType.METAL)
                    .lightLevel(state -> 6)
                    .requiresCorrectToolForDrops()));

    /** Worked Martian alloy: the material every capsule and structure is built from. */
    public static final Block MARTIAN_ALLOY_BLOCK = register("martian_alloy_block", new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F, 12.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));

    /** The red root weed the machines sow behind themselves. */
    public static final Block RED_ROOT = register("red_root", new RedRootBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .lightLevel(state -> 3)
                    .sound(SoundType.GRASS)
                    .randomTicks()));

    /** The pale plate the Martian complexes are clad in. */
    public static final Block WHITE_MARTIAN_ALLOY = register("white_martian_alloy", new Block(BlockBehaviour.Properties.of()
                    .strength(6.0F, 15.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));

    /** Strong Martian glass: alloy and glass, and it stops more than a pickaxe swing. */
    public static final Block MARTIAN_GLASS = register("martian_glass", new Block(BlockBehaviour.Properties.of()
                    .strength(4.0F, 12.0F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .lightLevel(state -> 3)));

    /** Blood roots: stepping on them brings the dark. */
    public static final Block BLOOD_ROOTS = register("blood_roots", new BloodRootsBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .lightLevel(state -> 4)
                    .sound(SoundType.GRASS)
                    .randomTicks()));

    /** The blueprint: put it down and a rideable tripod is built on top of it. */
    public static final Block TRIPOD_BLUEPRINT = register("tripod_blueprint", new TripodBlueprintBlock(BlockBehaviour.Properties.of()
                    .strength(1.5F, 6.0F)
                    .lightLevel(state -> 7)
                    .sound(SoundType.WOOL)));


    /* ------------------------------------------------------------------ */
    /*  Third wave: Martian tech, crates, turrets and the infected stone   */
    /* ------------------------------------------------------------------ */

    /** Riveted hull plating: the walls of the domes, garages and workshops. */
    public static final Block MARTIAN_HULL = simple("martian_hull", BlockBehaviour.Properties.of()
            .strength(7.0F, 16.0F)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops());

    /** A laboratory console: screens, dials and a green glow. */
    public static final Block MARTIAN_CONSOLE = simple("martian_console", BlockBehaviour.Properties.of()
            .strength(4.0F, 10.0F)
            .sound(SoundType.METAL)
            .lightLevel(state -> 9)
            .requiresCorrectToolForDrops());

    /** Martian lamp: cold white light on a stalk. */
    public static final Block MARTIAN_LAMP = simple("martian_lamp", BlockBehaviour.Properties.of()
            .strength(3.0F, 8.0F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> 14));

    /** Martian supply crate. */
    public static final Block MARTIAN_CRATE = simple("martian_crate", BlockBehaviour.Properties.of()
            .strength(2.5F, 8.0F)
            .sound(SoundType.WOOD));

    /** Human wooden crate, left behind in the towns and the warehouses. */
    public static final Block HUMAN_CRATE = simple("human_crate", BlockBehaviour.Properties.of()
            .strength(2.0F, 6.0F)
            .sound(SoundType.WOOD));

    /** Military crate: olive steel, stencilled, and usually full of nothing useful any more. */
    public static final Block MILITARY_CRATE = simple("military_crate", BlockBehaviour.Properties.of()
            .strength(3.0F, 9.0F)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops());

    /** The tall Martian turret that watches over the bases and the garages. */
    public static final Block MARTIAN_TURRET = register("martian_turret", new MartianTurretBlock(BlockBehaviour.Properties.of()
                    .strength(6.0F, 14.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .lightLevel(state -> 5)
                    .requiresCorrectToolForDrops()));

    /** Tall blood vines: the next generation of the red weed. */
    public static final Block BLOOD_VINES = register("blood_vines", new BloodVinesBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .lightLevel(state -> 4)
                    .sound(SoundType.GRASS)
                    .randomTicks()));

    /** Cobblestone the roots have cracked open. */
    public static final Block INFESTED_COBBLESTONE = simple("infested_cobblestone",
            BlockBehaviour.Properties.of().strength(2.4F, 8.0F).requiresCorrectToolForDrops().randomTicks(), true);

    /** Bricks the roots have cracked open. */
    public static final Block INFESTED_BRICKS = simple("infested_bricks",
            BlockBehaviour.Properties.of().strength(2.6F, 8.0F).requiresCorrectToolForDrops().randomTicks(), true);

    private static Block simple(String name, BlockBehaviour.Properties settings) {
        return simple(name, settings, false);
    }

    private static Block simple(String name, BlockBehaviour.Properties settings, boolean infested) {
        return register(name, infested ? new InfestedBlock(settings) : new Block(settings));
    }

    /**
     * Registers a block.
     *
     * <p>The instance is built right away and the deferred register only gets a supplier that hands
     * it back later, so the rest of the mod keeps working with plain {@code Block} constants.</p>
     */
    private static Block register(String name, Block block) {
        BLOCKS.register(name, () -> block);
        return block;
    }

    private ModBlocks() {
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        WotwMod.LOGGER.debug("Registered {} blocks", BLOCKS.getEntries().size());
    }
}
