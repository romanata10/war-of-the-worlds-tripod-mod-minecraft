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

    public static Block MARTIAN_CYLINDER;

    /** Worked Martian alloy: the material every capsule and structure is built from. */
    public static Block MARTIAN_ALLOY_BLOCK;

    /** The red root weed the machines sow behind themselves. */
    public static Block RED_ROOT;

    /** The pale plate the Martian complexes are clad in. */
    public static Block WHITE_MARTIAN_ALLOY;

    /** Strong Martian glass: alloy and glass, and it stops more than a pickaxe swing. */
    public static Block MARTIAN_GLASS;

    /** Blood roots: stepping on them brings the dark. */
    public static Block BLOOD_ROOTS;

    /** The blueprint: put it down and a rideable tripod is built on top of it. */
    public static Block TRIPOD_BLUEPRINT;

    /* ------------------------------------------------------------------ */
    /*  Third wave: Martian tech, crates, turrets and the infected stone   */
    /* ------------------------------------------------------------------ */

    /** Riveted hull plating: the walls of the domes, garages and workshops. */
    public static Block MARTIAN_HULL;

    /** A laboratory console: screens, dials and a green glow. */
    public static Block MARTIAN_CONSOLE;

    /** Martian lamp: cold white light on a stalk. */
    public static Block MARTIAN_LAMP;

    /** Martian supply crate. */
    public static Block MARTIAN_CRATE;

    /** Human wooden crate, left behind in the towns and the warehouses. */
    public static Block HUMAN_CRATE;

    /** Military crate: olive steel, stencilled, and usually full of nothing useful any more. */
    public static Block MILITARY_CRATE;

    /** The tall Martian turret that watches over the bases and the garages. */
    public static Block MARTIAN_TURRET;

    /** Tall blood vines: the next generation of the red weed. */
    public static Block BLOOD_VINES;

    /** Cobblestone the roots have cracked open. */
    public static Block INFESTED_COBBLESTONE;

    /** Bricks the roots have cracked open. */
    public static Block INFESTED_BRICKS;

    /*
     * The blocks are built here, not next to the fields: Forge freezes the vanilla block registry before
     * a mod is constructed and {@code Block} registers a holder for itself in its own constructor. Doing
     * the work inside the supplier keeps it in the register event, where Forge expects it, and every
     * field above is ready by the time the game asks for it.
     */
    static {
        BLOCKS.register("martian_cylinder",
                () -> MARTIAN_CYLINDER = new MartianCylinderBlock(BlockBehaviour.Properties.of()
                    .strength(6.0F, 12.0F)
                    .sound(SoundType.METAL)
                    .lightLevel(state -> 6)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("martian_alloy_block",
                () -> MARTIAN_ALLOY_BLOCK = new Block(BlockBehaviour.Properties.of()
                    .strength(5.0F, 12.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("red_root",
                () -> RED_ROOT = new RedRootBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .lightLevel(state -> 3)
                    .sound(SoundType.GRASS)
                    .randomTicks()));
        BLOCKS.register("white_martian_alloy",
                () -> WHITE_MARTIAN_ALLOY = new Block(BlockBehaviour.Properties.of()
                    .strength(6.0F, 15.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("martian_glass",
                () -> MARTIAN_GLASS = new Block(BlockBehaviour.Properties.of()
                    .strength(4.0F, 12.0F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .lightLevel(state -> 3)));
        BLOCKS.register("blood_roots",
                () -> BLOOD_ROOTS = new BloodRootsBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .lightLevel(state -> 4)
                    .sound(SoundType.GRASS)
                    .randomTicks()));
        BLOCKS.register("tripod_blueprint",
                () -> TRIPOD_BLUEPRINT = new TripodBlueprintBlock(BlockBehaviour.Properties.of()
                    .strength(1.5F, 6.0F)
                    .lightLevel(state -> 7)
                    .sound(SoundType.WOOL)));
        BLOCKS.register("martian_hull",
                () -> MARTIAN_HULL = new Block(BlockBehaviour.Properties.of()
            .strength(7.0F, 16.0F)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops()));
        BLOCKS.register("martian_console",
                () -> MARTIAN_CONSOLE = new Block(BlockBehaviour.Properties.of()
            .strength(4.0F, 10.0F)
            .sound(SoundType.METAL)
            .lightLevel(state -> 9)
            .requiresCorrectToolForDrops()));
        BLOCKS.register("martian_lamp",
                () -> MARTIAN_LAMP = new Block(BlockBehaviour.Properties.of()
            .strength(3.0F, 8.0F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> 14)));
        BLOCKS.register("martian_crate",
                () -> MARTIAN_CRATE = new Block(BlockBehaviour.Properties.of()
            .strength(2.5F, 8.0F)
            .sound(SoundType.WOOD)));
        BLOCKS.register("human_crate",
                () -> HUMAN_CRATE = new Block(BlockBehaviour.Properties.of()
            .strength(2.0F, 6.0F)
            .sound(SoundType.WOOD)));
        BLOCKS.register("military_crate",
                () -> MILITARY_CRATE = new Block(BlockBehaviour.Properties.of()
            .strength(3.0F, 9.0F)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops()));
        BLOCKS.register("martian_turret",
                () -> MARTIAN_TURRET = new MartianTurretBlock(BlockBehaviour.Properties.of()
                    .strength(6.0F, 14.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .lightLevel(state -> 5)
                    .requiresCorrectToolForDrops()));
        BLOCKS.register("blood_vines",
                () -> BLOOD_VINES = new BloodVinesBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .lightLevel(state -> 4)
                    .sound(SoundType.GRASS)
                    .randomTicks()));
        BLOCKS.register("infested_cobblestone", () -> INFESTED_COBBLESTONE = new InfestedBlock(BlockBehaviour.Properties.of().strength(2.4F, 8.0F).requiresCorrectToolForDrops().randomTicks()));
        BLOCKS.register("infested_bricks", () -> INFESTED_BRICKS = new InfestedBlock(BlockBehaviour.Properties.of().strength(2.6F, 8.0F).requiresCorrectToolForDrops().randomTicks()));
    }

    private ModBlocks() {
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        WotwMod.LOGGER.debug("Registered {} blocks", BLOCKS.getEntries().size());
    }
}
