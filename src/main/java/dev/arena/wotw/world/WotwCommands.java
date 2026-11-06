package dev.arena.wotw.world;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.arena.wotw.WotwMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * The {@code /wotw} command: today only the louse apocalypse switch.
 *
 * <p>The swarm is off in every new world, because a bug that fills a whole field with four thousand
 * lice is only amusing when you asked for it. When the players do ask for it, this is where they
 * ask:</p>
 *
 * <pre>
 * /wotw apocalypse on      the lice start breeding and do not stop
 * /wotw apocalypse off     back to a quiet night
 * /wotw apocalypse         report the current state
 * </pre>
 */
@Mod.EventBusSubscriber(modid = WotwMod.MOD_ID)
public final class WotwCommands {

    private WotwCommands() {
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("wotw")
                .then(Commands.literal("apocalypse")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> report(context.getSource()))
                        .then(Commands.literal("on")
                                .executes(context -> set(context, true)))
                        .then(Commands.literal("off")
                                .executes(context -> set(context, false)))
                        .then(Commands.literal("status")
                                .executes(context -> report(context.getSource())))
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> set(context,
                                        BoolArgumentType.getBool(context, "enabled"))))));
    }

    private static int set(CommandContext<CommandSourceStack> context, boolean value) {
        ServerLevel level = context.getSource().getLevel();
        LouseApocalypse.set(level, value);
        context.getSource().sendSuccess(() -> Component.translatable(
                value ? "wotw.command.apocalypse.on" : "wotw.command.apocalypse.off"), true);
        return value ? 1 : 0;
    }

    private static int report(CommandSourceStack source) {
        boolean enabled = LouseApocalypse.get(source.getLevel()).isEnabled();
        source.sendSuccess(() -> Component.translatable(enabled
                ? "wotw.command.apocalypse.state.on"
                : "wotw.command.apocalypse.state.off"), false);
        return enabled ? 1 : 0;
    }
}
