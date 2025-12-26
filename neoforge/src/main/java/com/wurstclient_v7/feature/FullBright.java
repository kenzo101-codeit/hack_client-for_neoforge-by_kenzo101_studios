package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public final class FullBright {
    private static volatile boolean enabled = false;
    private static long lastTick = 0;

    private FullBright() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;
        Player player = mc.player;
        if (player == null) return;

        long gameTime = player.level().getGameTime();
        int duration = (int) ConfigManager.getDouble("fullbright.duration", 260);
        if (gameTime - lastTick < duration / 2) return; // refresh occasionally
        lastTick = gameTime;

        MinecraftServer server = mc.getSingleplayerServer();
        if (server == null) return;

        String cmd = String.format("/effect give @s minecraft:night_vision %d 1 true", duration);

        CommandSourceStack source = new CommandSourceStack(CommandSource.NULL,
            player.position(), player.getRotationVector(),
            player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null,
            4, player.getName().getString(), player.getDisplayName(), server, (Entity) player);

        server.getCommands().performPrefixedCommand(source, cmd);
    }
}
