package dev.arena.wotw.registry;

import dev.arena.wotw.WotwConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;

import java.util.List;

/**
 * Natural spawning. Tripods are deliberately rare: their weights are tiny compared to zombies
 * and skeletons, but they are not zero, so a night walk can end very badly.
 *
 * <p>The Martian louse is the exception — it never spawns on its own. Lice come out of capsules and
 * infested structures only, which keeps caves free of the swarm.</p>
 *
 * <p>The spawn table lives here; the biome modifier in {@code data/wotw/forge/biome_modifier}
 * decides *where* it is applied, so the two can never drift apart.</p>
 */
public final class ModSpawns {

    /**
     * One line of the spawn table.
     *
     * @param clearance how many free blocks the machine needs above the spawn position
     * @param weight    the vanilla spawn weight (zombies are 100, so these are very rare)
     * @param minGroup  smallest group
     * @param maxGroup  largest group
     */
    public record Entry(EntityType<? extends Mob> type, boolean big, int clearance, int weight,
                        int minGroup, int maxGroup) {

        boolean allowed() {
            return !big || !WotwConfig.SMALL_TRIPODS_ONLY;
        }

        public MobCategory category() {
            return MobCategory.MONSTER;
        }
    }

    private static final List<Entry> TABLE = List.of(
            new Entry(ModEntities.TRIPOD_2005, true, 4, 2, 1, 1),
            new Entry(ModEntities.BBC_WHITE_TRIPOD, true, 4, 2, 1, 2),
            new Entry(ModEntities.BBC_RED_TRIPOD, true, 4, 2, 1, 2),
            new Entry(ModEntities.BOOK_TRIPOD, true, 4, 2, 1, 1),
            new Entry(ModEntities.ASYLUM_WALKER, true, 3, 3, 1, 1),
            new Entry(ModEntities.GOLIATH_TRIPOD, true, 5, 1, 1, 1),
            new Entry(ModEntities.MARTIAN_HERON, true, 5, 2, 1, 1),
            new Entry(ModEntities.FLYING_MANTA, true, 4, 1, 1, 1),
            new Entry(ModEntities.MARTIAN_SPIDER, false, 3, 3, 1, 2),
            // The louse is deliberately *not* in this list any more: lice only exist where a capsule
            // opened or a Martian structure stands, so caves and nights are quiet again.
            new Entry(ModEntities.MARTIAN, false, 2, 5, 1, 2),
            new Entry(ModEntities.MARTIAN_MASTER, false, 2, 1, 1, 1));

    private ModSpawns() {
    }

    /** The spawn table, after the config switches have been applied. */
    public static List<Entry> table() {
        return TABLE;
    }

    /**
     * Adds the table to a biome. Called by the biome modifier, which is where Forge wants the
     * "which biomes" decision to live.
     */
    public static void addSpawners(ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (!WotwConfig.NATURAL_SPAWNS) {
            return;
        }
        for (Entry entry : TABLE) {
            if (!entry.allowed()) {
                continue;
            }
            builder.getMobSpawnSettings().addSpawn(entry.category(),
                    new MobSpawnSettings.SpawnerData(entry.type(), entry.weight(),
                            entry.minGroup(), entry.maxGroup()));
        }
    }

    /** Spawn placement rules: machines need solid ground under them and head room above. */
    public static void registerPlacements(SpawnPlacementRegisterEvent event) {
        for (Entry entry : TABLE) {
            addPlacement(event, entry.type(), entry.clearance());
        }
    }

    private static <T extends Mob> void addPlacement(SpawnPlacementRegisterEvent event, EntityType<T> type,
                                                     int clearance) {
        event.register(type, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, spawnType, pos, random) -> isClear(world, pos, clearance),
                SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    private static boolean isClear(ServerLevelAccessor world, BlockPos pos, int clearance) {
        BlockPos below = pos.below();
        if (!world.getBlockState(below).canOcclude()) {
            return false;
        }
        for (int i = 0; i < clearance; i++) {
            if (!world.getBlockState(pos.above(i)).isAir()) {
                return false;
            }
        }
        return world.getBlockState(pos).getFluidState().isEmpty();
    }

    /** Forge still needs a plain hook in case somebody switches spawning off in the config. */
    public static void register() {
        if (!WotwConfig.NATURAL_SPAWNS) {
            dev.arena.wotw.WotwMod.LOGGER.info("Natural spawns are switched off in the config");
        }
    }
}
