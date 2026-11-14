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

    private static List<Entry> table;

    private static List<Entry> buildTable() {
        return List.of(
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
            new Entry(ModEntities.MARTIAN_MASTER, false, 2, 1, 1, 1),
            // the fifth wave: engineers and psychological weapons, rarer than the line machines
            new Entry(ModEntities.CONSTRUCTION_TRIPOD, true, 8, 1, 1, 1),
            new Entry(ModEntities.SMOKE_TRIPOD, true, 4, 2, 1, 1),
            new Entry(ModEntities.BOMB_TRIPOD, true, 4, 2, 1, 1),
            new Entry(ModEntities.CONSTRICTOR, true, 4, 2, 1, 1),
            new Entry(ModEntities.XENO_TELEPATH, true, 4, 1, 1, 1),
            // sixth wave: the machines from the artwork
            new Entry(ModEntities.CAMERON, true, 4, 2, 1, 1),
            new Entry(ModEntities.GOREY, true, 3, 2, 1, 1),
            new Entry(ModEntities.WAYNE, true, 4, 1, 1, 1),
            new Entry(ModEntities.GOBLE, true, 4, 2, 1, 1),
            new Entry(ModEntities.BBC2019, true, 5, 1, 1, 1),
            new Entry(ModEntities.ATTACK2021, true, 5, 1, 1, 1),
            new Entry(ModEntities.ATTACK, true, 5, 1, 1, 1),
            new Entry(ModEntities.VEGETABLE, true, 4, 2, 1, 1),
            new Entry(ModEntities.MEXICAN, true, 4, 1, 1, 1),
            new Entry(ModEntities.FOUR_LEGGED, true, 6, 1, 1, 1),
            new Entry(ModEntities.SERIES1998, true, 2, 1, 1, 1),
            new Entry(ModEntities.PENDRAGON, true, 2, 1, 1, 1),
            new Entry(ModEntities.NEXTWAVE_CYBORG, true, 1, 1, 1, 1),
            new Entry(ModEntities.TRUE2012, true, 2, 1, 1, 1),
            new Entry(ModEntities.STEAM_TOWER, true, 2, 1, 1, 1),
            new Entry(ModEntities.ALIEN_DAWN, true, 1, 1, 1, 1),
            new Entry(ModEntities.ALIEN_DAWN_MK2, true, 1, 1, 1, 1),
            new Entry(ModEntities.SCARY_MOVIE, true, 2, 1, 1, 1),
            new Entry(ModEntities.USSR_BOOK, true, 1, 1, 1, 1),
            new Entry(ModEntities.ANNIHILATION, true, 1, 1, 1, 1),
            new Entry(ModEntities.MOTHERSHIP, true, 1, 1, 1, 1),
            new Entry(ModEntities.MINING, true, 2, 1, 1, 1),
            new Entry(ModEntities.ROGER_DEAN, true, 3, 1, 1, 1),
            new Entry(ModEntities.CITY, true, 1, 1, 1, 1),
            new Entry(ModEntities.FACTORY, true, 2, 1, 1, 1),
            new Entry(ModEntities.CAGE, true, 1, 1, 1, 1),
            new Entry(ModEntities.HUMANOID, true, 3, 1, 1, 1),
            new Entry(ModEntities.SIGNAL_SCOUT, true, 2, 1, 1, 1),
            new Entry(ModEntities.FROST, true, 2, 1, 1, 1),
            new Entry(ModEntities.STORM, true, 1, 1, 1, 1),
            new Entry(ModEntities.WEAVER, true, 2, 1, 1, 1),
            new Entry(ModEntities.BLINK, true, 2, 1, 1, 1),
            new Entry(ModEntities.HARVESTER, true, 1, 1, 1, 1),
            new Entry(ModEntities.DISC, true, 2, 1, 1, 1),
            new Entry(ModEntities.CYCLOPS, true, 1, 1, 1, 1),
            new Entry(ModEntities.SKETCH, true, 2, 1, 1, 1),
            new Entry(ModEntities.JELLY, true, 2, 1, 1, 1),
            new Entry(ModEntities.SIEGE, true, 1, 1, 1, 1),
            new Entry(ModEntities.PRISM, true, 2, 1, 1, 1),
            new Entry(ModEntities.LAVA, true, 1, 1, 1, 1),
            new Entry(ModEntities.LIVING, true, 1, 1, 1, 1),
            new Entry(ModEntities.STRIDER, true, 1, 1, 1, 1),
            new Entry(ModEntities.SAUCER, true, 2, 1, 1, 1));
    }

    private ModSpawns() {
    }

    /**
     * The spawn table.
     *
     * <p>It is built on first use rather than next to the field: the entity types only exist once
     * Forge has fired the register event, and this class is loaded before that, while the mod is
     * being constructed.</p>
     */
    public static List<Entry> table() {
        if (table == null) {
            table = buildTable();
        }
        return table;
    }

    /**
     * Adds the table to a biome. Called by the biome modifier, which is where Forge wants the
     * "which biomes" decision to live.
     */
    public static void addSpawners(ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (!WotwConfig.NATURAL_SPAWNS) {
            return;
        }
        for (Entry entry : table()) {
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
        for (Entry entry : table()) {
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
