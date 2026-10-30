package dev.arena.wotw.registry;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.block.MartianCylinderBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSoundGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/** Blocks of the mod: the Martian cylinder that landed in the crater. */
public final class ModBlocks {

    public static final Block MARTIAN_CYLINDER = Registry.register(Registries.BLOCK, WotwMod.id("martian_cylinder"),
            new MartianCylinderBlock(AbstractBlock.Settings.create()
                    .strength(6.0F, 12.0F)
                    .sounds(BlockSoundGroup.METAL)
                    .luminance(state -> 6)
                    .requiresTool()));

    private ModBlocks() {
    }

    public static void register() {
        WotwMod.LOGGER.debug("Registered block {}", Registries.BLOCK.getId(MARTIAN_CYLINDER));
    }
}
