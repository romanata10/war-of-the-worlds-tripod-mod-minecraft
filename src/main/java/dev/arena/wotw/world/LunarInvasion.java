package dev.arena.wotw.world;

import com.mojang.brigadier.CommandDispatcher;
import dev.arena.wotw.WotwMod;
import dev.arena.wotw.entity.LunarFlyerEntity;
import dev.arena.wotw.entity.LunarMothershipEntity;
import dev.arena.wotw.entity.LunarNomadEntity;
import dev.arena.wotw.entity.LunarWatcherEntity;
import dev.arena.wotw.registry.ModEntities;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * The fifth night.
 *
 * <p>The lunar fleet does not come in capsules. On the fifth day of a world, once the sun goes down,
 * a mothership drops out of the sky over one of the players, and flying ships come down out of it
 * with nomads and watchers aboard. The Moon is at war with everybody, so from that night on the
 * world has three sides.</p>
 */
@Mod.EventBusSubscriber(modid = WotwMod.MOD_ID)
public final class LunarInvasion {

    /** The day the fleet arrives. */
    public static final int ARRIVAL_DAY = 5;
    /** How high above the ground the mothership hangs. */
    private static final int MOTHERSHIP_HEIGHT = 220;
    /** How far from the player it appears. */
    private static final double ARRIVAL_DISTANCE = 140.0D;

    private LunarInvasion() {
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        ServerLevel world = event.getServer().getLevel(Level.OVERWORLD);
        if (world == null) {
            return;
        }
        List<ServerPlayer> players = world.getPlayers(player -> true);
        if (players.isEmpty()) {
            return;
        }
        InvasionState state = world.getDataStorage()
                .computeIfAbsent(InvasionState::fromNbt, InvasionState::new, InvasionState.KEY);
        if (state.lunarWaveDone) {
            return;
        }
        long day = world.getDayTime() / 24000L;
        if (day < ARRIVAL_DAY - 1L || !world.isNight()) {
            return;
        }
        ServerPlayer player = players.get(world.getRandom().nextInt(players.size()));
        state.lunarWaveDone = true;
        state.setDirty();
        summonFleet(world, player);
    }

    /** Brings the whole arrival down: the mothership, three ships and the first nomads. */
    public static void summonFleet(ServerLevel world, ServerPlayer player) {
        double angle = world.getRandom().nextDouble() * Math.PI * 2.0D;
        double x = player.getX() + Math.cos(angle) * ARRIVAL_DISTANCE;
        double z = player.getZ() + Math.sin(angle) * ARRIVAL_DISTANCE;

        LunarMothershipEntity mothership = ModEntities.LUNAR_MOTHERSHIP.create(world);
        if (mothership != null) {
            mothership.moveTo(x, MOTHERSHIP_HEIGHT, z, world.getRandom().nextFloat() * 360.0F, 0.0F);
            mothership.finalizeSpawn(world, world.getCurrentDifficultyAt(mothership.blockPosition()),
                    MobSpawnType.EVENT, null, null);
            world.addFreshEntity(mothership);
        }
        for (int index = 0; index < 3; index++) {
            LunarFlyerEntity flyer = ModEntities.LUNAR_FLYER.create(world);
            if (flyer == null) {
                continue;
            }
            double spread = angle + index * 1.4D;
            flyer.moveTo(x + Math.cos(spread) * 24.0D, MOTHERSHIP_HEIGHT - 26.0D,
                    z + Math.sin(spread) * 24.0D, world.getRandom().nextFloat() * 360.0F, 0.0F);
            flyer.finalizeSpawn(world, world.getCurrentDifficultyAt(flyer.blockPosition()),
                    MobSpawnType.EVENT, null, null);
            world.addFreshEntity(flyer);
        }
        for (int index = 0; index < 4; index++) {
            LunarNomadEntity nomad = ModEntities.LUNAR_NOMAD.create(world);
            if (nomad == null) {
                continue;
            }
            double spread = angle + index * 1.6D;
            double groundY = world.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING,
                    (int) Math.floor(x + Math.cos(spread) * 30.0D),
                    (int) Math.floor(z + Math.sin(spread) * 30.0D));
            nomad.moveTo(x + Math.cos(spread) * 30.0D, groundY,
                    z + Math.sin(spread) * 30.0D, world.getRandom().nextFloat() * 360.0F, 0.0F);
            nomad.finalizeSpawn(world, world.getCurrentDifficultyAt(nomad.blockPosition()),
                    MobSpawnType.EVENT, null, null);
            world.addFreshEntity(nomad);
        }
        LunarWatcherEntity watcher = ModEntities.LUNAR_WATCHER.create(world);
        if (watcher != null) {
            watcher.moveTo(x, MOTHERSHIP_HEIGHT - 40.0D, z, 0.0F, 0.0F);
            watcher.finalizeSpawn(world, world.getCurrentDifficultyAt(watcher.blockPosition()),
                    MobSpawnType.EVENT, null, null);
            world.addFreshEntity(watcher);
        }
        WotwMod.LOGGER.info("The fifth night: the lunar fleet is over {}", player.getName().getString());
    }

    /** `/wotw` — teleport aboard the mothership, or call the fleet early. */
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("wotw")
                .then(Commands.literal("mothership").executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    ServerLevel world = player.serverLevel();
                    LunarMothershipEntity ship = world.getEntitiesOfClass(LunarMothershipEntity.class,
                                    AABB.ofSize(player.position(), 4096.0D, 1024.0D, 4096.0D))
                            .stream()
                            .min(java.util.Comparator.comparingDouble(player::distanceToSqr))
                            .orElse(null);
                    if (ship == null) {
                        context.getSource().sendFailure(Component.translatable("command.wotw.mothership.none"));
                        return 0;
                    }
                    var deck = ship.getDeckCenter().above(1);
                    player.teleportTo(deck.getX() + 0.5D, deck.getY(), deck.getZ() + 0.5D);
                    context.getSource().sendSuccess(() -> Component.translatable("command.wotw.mothership.ok"), false);
                    return 1;
                }))
                .then(Commands.literal("lunar").requires(source -> source.hasPermission(2)).executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    summonFleet(player.serverLevel(), player);
                    context.getSource().sendSuccess(() -> Component.translatable("command.wotw.lunar.ok"), false);
                    return 1;
                })));
    }
}
