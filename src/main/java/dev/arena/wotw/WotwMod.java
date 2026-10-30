package dev.arena.wotw;

import dev.arena.wotw.registry.ModBlocks;
import dev.arena.wotw.registry.ModEntities;
import dev.arena.wotw.registry.ModItems;
import dev.arena.wotw.registry.ModSounds;
import dev.arena.wotw.registry.ModSpawns;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point of the mod.
 *
 * <p>Registration order matters: entity types first, then items (spawn eggs reference entity
 * types), then blocks (the block item is created in {@link ModItems}), then attributes and
 * biome spawns.</p>
 */
public class WotwMod implements ModInitializer {
    public static final String MOD_ID = "wotw";
    public static final Logger LOGGER = LoggerFactory.getLogger("War of the Worlds");

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModSounds.register();
        ModEntities.register();
        ModBlocks.register();
        ModItems.register();
        ModEntities.registerAttributes();
        ModSpawns.register();

        LOGGER.info("The Martians have landed. {} tripod types are ready to walk the Overworld.",
                ModEntities.all().size());
    }
}
