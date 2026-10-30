package dev.arena.wotw.registry;

import dev.arena.wotw.WotwConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

/**
 * Natural spawning. Tripods are deliberately rare: their weights are tiny compared to zombies
 * and skeletons, but they are not zero, so a night walk can end very badly.
 */
public final class ModSpawns {

    private ModSpawns() {
    }

    public static void register() {
        if (!WotwConfig.NATURAL_SPAWNS) {
            return;
        }

        if (!WotwConfig.SMALL_TRIPODS_ONLY) {
            groundSpawn(ModEntities.TRIPOD_2005, 4, 2, 1, 1);
            groundSpawn(ModEntities.BBC_WHITE_TRIPOD, 4, 2, 1, 2);
            groundSpawn(ModEntities.BBC_RED_TRIPOD, 4, 2, 1, 2);
            groundSpawn(ModEntities.BOOK_TRIPOD, 4, 2, 1, 1);
            groundSpawn(ModEntities.ASYLUM_WALKER, 3, 3, 1, 1);
            groundSpawn(ModEntities.GOLIATH_TRIPOD, 5, 1, 1, 1);
            groundSpawn(ModEntities.MARTIAN_HERON, 5, 2, 1, 1);
            groundSpawn(ModEntities.FLYING_MANTA, 4, 1, 1, 1);
        }
        groundSpawn(ModEntities.MARTIAN_SPIDER, 3, 3, 1, 2);
        groundSpawn(ModEntities.MARTIAN_LOUSE, 2, 8, 2, 3);
        groundSpawn(ModEntities.MARTIAN, 2, 5, 1, 2);
        groundSpawn(ModEntities.MARTIAN_MASTER, 2, 1, 1, 1);
    }

    private static <T extends MobEntity> void groundSpawn(EntityType<T> type, int clearance, int weight,
                                                          int minGroup, int maxGroup) {
        SpawnRestriction.register(type, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, spawnReason, pos, random) -> {
                    BlockPos below = pos.down();
                    if (!world.getBlockState(below).isOpaque()) {
                        return false;
                    }
                    for (int i = 0; i < clearance; i++) {
                        if (!world.getBlockState(pos.up(i)).isAir()) {
                            return false;
                        }
                    }
                    return world.getBlockState(pos).getFluidState().isEmpty();
                });
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER, type, weight, minGroup, maxGroup);
    }
}
