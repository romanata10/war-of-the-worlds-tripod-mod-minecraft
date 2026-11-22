package dev.arena.wotw.world;

import dev.arena.wotw.WotwMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

/**
 * Mars: the second dimension of the mod.
 *
 * <p>The planet is a real dimension ({@code wotw:mars}) declared by the JSON in
 * {@code data/wotw/dimension}, so it has its own terrain, its own sky and its own weather. The only
 * way in and out is the rocket: it is a block, and right clicking it carries the player across. The
 * place the player left from is written into their persistent data, so the return trip lands them
 * back where the rocket stood instead of at the world spawn.</p>
 */
public final class MarsDimension {

    public static final ResourceKey<Level> MARS =
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(WotwMod.MOD_ID, "mars"));

    /** Where the player stood before the trip, in the overworld. */
    private static final String RETURN = "wotw.mars.return";

    private MarsDimension() {
    }

    /** True when this level is Mars. */
    public static boolean isMars(Level level) {
        return level.dimension().equals(MARS);
    }

    /**
     * Carries the player across: to Mars from anywhere else, back to the overworld from Mars.
     *
     * @return true when the trip happened
     */
    public static boolean travel(ServerPlayer player) {
        MinecraftServer server = player.getServer();
        if (server == null) {
            return false;
        }
        if (isMars(player.level())) {
            return toOverworld(player, server);
        }
        ServerLevel mars = server.getLevel(MARS);
        if (mars == null) {
            // the dimension JSON is missing: better to say nothing than to drop the player into the void
            WotwMod.LOGGER.warn("The Mars dimension is not loaded, the rocket cannot launch");
            return false;
        }
        CompoundTag data = player.getPersistentData();
        data.putDouble(RETURN + ".x", player.getX());
        data.putDouble(RETURN + ".y", player.getY());
        data.putDouble(RETURN + ".z", player.getZ());
        data.putFloat(RETURN + ".yaw", player.getYRot());

        BlockPos landing = surface(mars, player.getX(), player.getZ());
        player.teleportTo(mars, landing.getX() + 0.5D, landing.getY(), landing.getZ() + 0.5D,
                player.getYRot(), player.getXRot());
        return true;
    }

    private static boolean toOverworld(ServerPlayer player, MinecraftServer server) {
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) {
            return false;
        }
        CompoundTag data = player.getPersistentData();
        Vec3 destination = data.contains(RETURN + ".x")
                ? new Vec3(data.getDouble(RETURN + ".x"), data.getDouble(RETURN + ".y"), data.getDouble(RETURN + ".z"))
                : Vec3.atLowerCornerOf(overworld.getSharedSpawnPos());
        float yaw = data.contains(RETURN + ".yaw") ? data.getFloat(RETURN + ".yaw") : player.getYRot();
        BlockPos spot = surface(overworld, destination.x, destination.z);
        player.teleportTo(overworld, spot.getX() + 0.5D, spot.getY(), spot.getZ() + 0.5D, yaw, player.getXRot());
        return true;
    }

    /** The first spot above the ground that a player fits in, with the chunk generated first. */
    public static BlockPos surface(ServerLevel level, double x, double z) {
        BlockPos column = BlockPos.containing(x, 0.0D, z);
        level.getChunk(column.getX() >> 4, column.getZ() >> 4);
        int height = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, column.getX(), column.getZ());
        return new BlockPos(column.getX(), Math.max(height + 1, level.getMinBuildHeight() + 1), column.getZ());
    }
}
