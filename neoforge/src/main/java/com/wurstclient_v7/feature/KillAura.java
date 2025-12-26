package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public final class KillAura {
    private static volatile boolean enabled = false;
    private static int range = 8;
    private static long lastTick = 0;
    private static int delayTicks = 5; // throttle attacks

    private KillAura() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static void setRange(int r) { range = r; }

    public static void onClientTick() {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;
        Player player = mc.player;
        if (player == null) return;

        long gameTime = player.level().getGameTime();
        if (gameTime - lastTick < delayTicks) return;
        lastTick = gameTime;

        MinecraftServer server = mc.getSingleplayerServer();
        if (server == null) return; // only run actions on integrated server

        // Build a command that damages the nearest non-player entity within range
        String cmd = String.format("/damage @e[type=!player,limit=1,sort=nearest,distance=..%d,type=!minecraft:arrow,type=!minecraft:item,tag=!hack] 3 minecraft:mob_attack by @p", range);

        // Construct a CommandSourceStack similar to what server-side logic would use
        CommandSourceStack source = new CommandSourceStack(CommandSource.NULL,
            player.position(), player.getRotationVector(),
            player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null,
            4, player.getName().getString(), player.getDisplayName(), server, (Entity) player);

        server.getCommands().performPrefixedCommand(source, cmd);
    }
}
