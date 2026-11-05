package dev.arena.wotw.world;

import dev.arena.wotw.WotwConfig;
import dev.arena.wotw.WotwMod;
import dev.arena.wotw.entity.CapsuleEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * The invasion clock.
 *
 * <p>Six seconds after the first player joins a world a capsule falls out of the sky next to them:
 * {@link CapsuleEntity} handles the crater, the heat ray, the Martians and the three tripods. After
 * that a new capsule drops somewhere near a random player every seven minutes, so the world keeps
 * filling up with machines.</p>
 */
@Mod.EventBusSubscriber(modid = WotwMod.MOD_ID)
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

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tick(event.getServer());
        }
    }

    private static void tick(MinecraftServer server) {
        if (!WotwConfig.INVASION_EVENTS) {
            return;
        }
        ServerLevel world = server.getLevel(Level.OVERWORLD);
        if (world == null) {
            return;
        }
        List<ServerPlayer> players = world.getPlayers(player -> true);
        if (players.isEmpty()) {
            return;
        }

        InvasionState state = world.getDataStorage()
                .computeIfAbsent(InvasionState::fromNbt, InvasionState::new, InvasionState.KEY);

        state.ticks++;
        if (!state.firstWaveDone) {
            if (state.ticks < FIRST_WAVE_DELAY) {
                return;
            }
            ServerPlayer player = players.get(world.getRandom().nextInt(players.size()));
            if (dropNear(world, player, FIRST_WAVE_DISTANCE)) {
                state.firstWaveDone = true;
                state.ticks = 0;
                state.setDirty();
                WotwMod.LOGGER.info("First wave: a capsule has fallen near {}", player.getName().getString());
            }
            return;
        }

        if (state.ticks >= WAVE_PERIOD) {
            ServerPlayer player = players.get(world.getRandom().nextInt(players.size()));
            if (dropNear(world, player, WAVE_DISTANCE)) {
                state.ticks = 0;
                state.setDirty();
            }
        }
    }

    private static boolean dropNear(ServerLevel world, ServerPlayer player, double distance) {
        double angle = world.getRandom().nextDouble() * Math.PI * 2.0D;
        double x = player.getX() + Math.cos(angle) * distance;
        double z = player.getZ() + Math.sin(angle) * distance;
        return CapsuleEntity.drop(world, x, z, true) != null;
    }
}
