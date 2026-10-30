package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.block.MartianCylinderBlock;
import dev.arena.wotw.block.BloodRootsBlock;
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

    private ModBlocks() {
    }

    public static void register() {
        WotwMod.LOGGER.debug("Registered block {}", Registries.BLOCK.getId(MARTIAN_CYLINDER));
    }
}
