package dev.arena.wotw.world;

import dev.arena.wotw.block.BloodRootsBlock;
import dev.arena.wotw.block.RedRootBlock;
import dev.arena.wotw.registry.ModStatusEffects;
import dev.arena.wotw.WotwMod;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

/**
 * A player who dies inside the cage of a Handling Machine leaves a whole thicket of red roots
 * behind — the Martians plant their weeds in the corpse.
 */
@Mod.EventBusSubscriber(modid = WotwMod.MOD_ID)
public final class CagedDeathHandler {

    private static final int RADIUS = 3;

    private CagedDeathHandler() {
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.isCanceled()) {
            return;
        }
        onDeath(event.getEntity(), event.getSource());
    }

    private static void onDeath(LivingEntity entity, DamageSource source) {
        if (!(entity.level() instanceof ServerLevel world)) {
            return;
        }
        if (!entity.hasEffect(ModStatusEffects.CAGED)) {
            return;
        }

        BlockPos base = entity.blockPosition();
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

        world.sendParticles(ParticleTypes.CRIMSON_SPORE, base.getX() + 0.5D, base.getY() + 0.8D, base.getZ() + 0.5D,
                90, 2.4D, 0.8D, 2.4D, 0.02D);
    }
}
