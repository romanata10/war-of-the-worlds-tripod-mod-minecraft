package dev.arena.wotw.world;

import dev.arena.wotw.WotwConfig;
import dev.arena.wotw.WotwMod;
import dev.arena.wotw.entity.CapsuleEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.List;

/**
 * The invasion clock.
 *
 * <p>Six seconds after the first player joins a world a capsule falls out of the sky next to them:
 * {@link CapsuleEntity} handles the crater, the heat ray, the Martians and the three tripods. After
 * that a new capsule drops somewhere near a random player every seven minutes, so the world keeps
 * filling up with machines.</p>
 */
public final class InvasionManager {

    /** Delay before the very first capsule, in ticks. */
    private static final int FIRST_WAVE_DELAY = 120;
    /** Gap between the following capsules, in ticks. */
    private static final int WAVE_PERIOD = 20 * 60 * 7;
    /** How far from the player the capsule comes down. */
    private static final double FIRST_WAVE_DISTANCE = 22.0D;
    private static final double WAVE_DISTANCE = 36.0D;

    private InvasionManager() {
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(InvasionManager::tick);
    }

    private static void tick(MinecraftServer server) {
        if (!WotwConfig.INVASION_EVENTS) {
            return;
        }
        ServerWorld world = server.getWorld(World.OVERWORLD);
        if (world == null) {
            return;
        }
        List<ServerPlayerEntity> players = world.getPlayers();
        if (players.isEmpty()) {
            return;
        }

        InvasionState state = world.getPersistentStateManager()
                .getOrCreate(InvasionState::fromNbt, InvasionState::new, InvasionState.KEY);

        state.ticks++;
        if (!state.firstWaveDone) {
            if (state.ticks < FIRST_WAVE_DELAY) {
                return;
            }
            ServerPlayerEntity player = players.get(world.getRandom().nextInt(players.size()));
            if (dropNear(world, player, FIRST_WAVE_DISTANCE)) {
                state.firstWaveDone = true;
                state.ticks = 0;
                state.markDirty();
                WotwMod.LOGGER.info("First wave: a capsule has fallen near {}", player.getName().getString());
            }
            return;
        }

        if (state.ticks >= WAVE_PERIOD) {
            ServerPlayerEntity player = players.get(world.getRandom().nextInt(players.size()));
            if (dropNear(world, player, WAVE_DISTANCE)) {
                state.ticks = 0;
                state.markDirty();
            }
        }
    }

    private static boolean dropNear(ServerWorld world, ServerPlayerEntity player, double distance) {
        double angle = world.getRandom().nextDouble() * Math.PI * 2.0D;
        double x = player.getX() + Math.cos(angle) * distance;
        double z = player.getZ() + Math.sin(angle) * distance;
        return CapsuleEntity.drop(world, x, z, true) != null;
    }
}
