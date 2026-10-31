package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.block.MartianCylinderBlock;
import dev.arena.wotw.block.BloodRootsBlock;
import dev.arena.wotw.block.BloodVinesBlock;
import dev.arena.wotw.block.InfestedBlock;
import dev.arena.wotw.block.MartianTurretBlock;
import dev.arena.wotw.block.RedRootBlock;
import dev.arena.wotw.block.TripodBlueprintBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/** Blocks of the mod: the Martian cylinder, the alloy the machines are plated with and the roots. */
public final class ModBlocks {

    public static final Block MARTIAN_CYLINDER = Registry.register(Registries.BLOCK, WotwMod.id("martian_cylinder"),
            new MartianCylinderBlock(AbstractBlock.Settings.create()
                    .strength(6.0F, 12.0F)
                    .sounds(BlockSoundGroup.METAL)
                    .luminance(state -> 6)
                    .requiresTool()));

    /** Worked Martian alloy: the material every capsule and structure is built from. */
    public static final Block MARTIAN_ALLOY_BLOCK = Registry.register(Registries.BLOCK,
            WotwMod.id("martian_alloy_block"),
            new Block(AbstractBlock.Settings.create()
                    .strength(5.0F, 12.0F)
                    .sounds(BlockSoundGroup.METAL)
                    .requiresTool()));

    /** The red root weed the machines sow behind themselves. */
    public static final Block RED_ROOT = Registry.register(Registries.BLOCK, WotwMod.id("red_root"),
            new RedRootBlock(AbstractBlock.Settings.create()
                    .noCollision()
                    .breakInstantly()
                    .nonOpaque()
                    .luminance(state -> 3)
                    .sounds(BlockSoundGroup.GRASS)
                    .ticksRandomly()));

    /** The pale plate the Martian complexes are clad in. */
    public static final Block WHITE_MARTIAN_ALLOY = Registry.register(Registries.BLOCK,
            WotwMod.id("white_martian_alloy"),
            new Block(AbstractBlock.Settings.create()
                    .strength(6.0F, 15.0F)
                    .sounds(BlockSoundGroup.METAL)
                    .requiresTool()));

    /** Strong Martian glass: alloy and glass, and it stops more than a pickaxe swing. */
    public static final Block MARTIAN_GLASS = Registry.register(Registries.BLOCK,
            WotwMod.id("martian_glass"),
            new Block(AbstractBlock.Settings.create()
                    .strength(4.0F, 12.0F)
                    .sounds(BlockSoundGroup.GLASS)
                    .nonOpaque()
                    .luminance(state -> 3)));

    /** Blood roots: stepping on them brings the dark. */
    public static final Block BLOOD_ROOTS = Registry.register(Registries.BLOCK, WotwMod.id("blood_roots"),
            new BloodRootsBlock(AbstractBlock.Settings.create()
                    .noCollision()
                    .breakInstantly()
                    .nonOpaque()
                    .luminance(state -> 4)
                    .sounds(BlockSoundGroup.GRASS)
                    .ticksRandomly()));

    /** The blueprint: put it down and a rideable tripod is built on top of it. */
    public static final Block TRIPOD_BLUEPRINT = Registry.register(Registries.BLOCK,
            WotwMod.id("tripod_blueprint"),
            new TripodBlueprintBlock(AbstractBlock.Settings.create()
                    .strength(1.5F, 6.0F)
                    .luminance(state -> 7)
                    .sounds(BlockSoundGroup.WOOL)));


    /* ------------------------------------------------------------------ */
    /*  Third wave: Martian tech, crates, turrets and the infected stone   */
    /* ------------------------------------------------------------------ */

    /** Riveted hull plating: the walls of the domes, garages and workshops. */
    public static final Block MARTIAN_HULL = simple("martian_hull", AbstractBlock.Settings.create()
            .strength(7.0F, 16.0F)
            .sounds(BlockSoundGroup.METAL)
            .requiresTool());

    /** A laboratory console: screens, dials and a green glow. */
    public static final Block MARTIAN_CONSOLE = simple("martian_console", AbstractBlock.Settings.create()
            .strength(4.0F, 10.0F)
            .sounds(BlockSoundGroup.METAL)
            .luminance(state -> 9)
            .requiresTool());

    /** Martian lamp: cold white light on a stalk. */
    public static final Block MARTIAN_LAMP = simple("martian_lamp", AbstractBlock.Settings.create()
            .strength(3.0F, 8.0F)
            .sounds(BlockSoundGroup.GLASS)
            .luminance(state -> 14));

    /** Martian supply crate. */
    public static final Block MARTIAN_CRATE = simple("martian_crate", AbstractBlock.Settings.create()
            .strength(2.5F, 8.0F)
            .sounds(BlockSoundGroup.WOOD));

    /** Human wooden crate, left behind in the towns and the warehouses. */
    public static final Block HUMAN_CRATE = simple("human_crate", AbstractBlock.Settings.create()
            .strength(2.0F, 6.0F)
            .sounds(BlockSoundGroup.WOOD));

    /** Military crate: olive steel, stencilled, and usually full of nothing useful any more. */
    public static final Block MILITARY_CRATE = simple("military_crate", AbstractBlock.Settings.create()
            .strength(3.0F, 9.0F)
            .sounds(BlockSoundGroup.METAL)
            .requiresTool());

    /** The tall Martian turret that watches over the bases and the garages. */
    public static final Block MARTIAN_TURRET = Registry.register(Registries.BLOCK, WotwMod.id("martian_turret"),
            new MartianTurretBlock(AbstractBlock.Settings.create()
                    .strength(6.0F, 14.0F)
                    .sounds(BlockSoundGroup.METAL)
                    .nonOpaque()
                    .luminance(state -> 5)
                    .requiresTool()));

    /** Tall blood vines: the next generation of the red weed. */
    public static final Block BLOOD_VINES = Registry.register(Registries.BLOCK, WotwMod.id("blood_vines"),
            new BloodVinesBlock(AbstractBlock.Settings.create()
                    .noCollision()
                    .breakInstantly()
                    .nonOpaque()
                    .luminance(state -> 4)
                    .sounds(BlockSoundGroup.GRASS)
                    .ticksRandomly()));

    /** Cobblestone the roots have cracked open. */
    public static final Block INFESTED_COBBLESTONE = simple("infested_cobblestone",
            AbstractBlock.Settings.create().strength(2.4F, 8.0F).requiresTool().ticksRandomly(), true);

    /** Bricks the roots have cracked open. */
    public static final Block INFESTED_BRICKS = simple("infested_bricks",
            AbstractBlock.Settings.create().strength(2.6F, 8.0F).requiresTool().ticksRandomly(), true);

    private static Block simple(String name, AbstractBlock.Settings settings) {
        return simple(name, settings, false);
    }

    private static Block simple(String name, AbstractBlock.Settings settings, boolean infested) {
        Block block = infested ? new InfestedBlock(settings) : new Block(settings);
        return Registry.register(Registries.BLOCK, WotwMod.id(name), block);
    }

    private ModBlocks() {
    }

    public static void register() {
        WotwMod.LOGGER.debug("Registered block {}", Registries.BLOCK.getId(MARTIAN_CYLINDER));
    }
}
