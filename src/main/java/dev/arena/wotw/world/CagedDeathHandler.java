package dev.arena.wotw.world;

import dev.arena.wotw.block.BloodRootsBlock;
import dev.arena.wotw.block.RedRootBlock;
import dev.arena.wotw.registry.ModStatusEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

/**
 * A player who dies inside the cage of a Handling Machine leaves a whole thicket of red roots
 * behind — the Martians plant their weeds in the corpse.
 */
public final class CagedDeathHandler {

    private static final int RADIUS = 3;

    private CagedDeathHandler() {
    }

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register(CagedDeathHandler::onDeath);
    }

    private static void onDeath(LivingEntity entity, net.minecraft.entity.damage.DamageSource source) {
        if (!(entity.getWorld() instanceof ServerWorld world)) {
            return;
        }
        if (!entity.hasStatusEffect(ModStatusEffects.CAGED)) {
            return;
        }

        BlockPos base = entity.getBlockPos();
        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dz = -RADIUS; dz <= RADIUS; dz++) {
                if (world.getRandom().nextFloat() > 0.55F) {
                    continue;
                }
                BlockPos column = base.add(dx, 0, dz);
                for (int dy = 0; dy <= 2; dy++) {
                    BlockPos pos = column.up(dy);
                    boolean planted = world.getRandom().nextFloat() < 0.35F
                            ? BloodRootsBlock.plant(world, pos)
                            : RedRootBlock.plant(world, pos);
                    if (planted) {
                        break;
                    }
                }
            }
        }

        world.spawnParticles(ParticleTypes.CRIMSON_SPORE, base.getX() + 0.5D, base.getY() + 0.8D, base.getZ() + 0.5D,
                90, 2.4D, 0.8D, 2.4D, 0.02D);
    }
}
