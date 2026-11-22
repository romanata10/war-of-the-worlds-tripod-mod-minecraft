package dev.arena.wotw.world;

import dev.arena.wotw.WotwMod;
import dev.arena.wotw.item.SpacesuitItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Mars has almost no air, no warmth and no water. Without the spacesuit the planet drains a player
 * two hearts a minute, which is slow enough to run back to the rocket and fast enough to matter.
 */
@Mod.EventBusSubscriber(modid = WotwMod.MOD_ID)
public final class MarsVacuum {

    /** How often the planet takes its bite, in ticks. */
    private static final int BITE_INTERVAL = 60;
    private static final float BITE = 1.0F;

    private MarsVacuum() {
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) {
            return;
        }
        if (!MarsDimension.isMars(player.level())) {
            return;
        }
        if (player.isCreative() || player.isSpectator() || !player.isAlive()) {
            return;
        }
        if (SpacesuitItem.isWorn(player)) {
            return;
        }
        if (player.tickCount % BITE_INTERVAL != 0) {
            return;
        }
        player.hurt(player.damageSources().freeze(), BITE);
    }
}
