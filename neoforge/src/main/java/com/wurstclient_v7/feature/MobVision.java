package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public final class MobVision {
    private static volatile boolean enabled = false;
    private static long lastTick = 0;
    private static int interval = 20;

    private MobVision() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;
        Player player = mc.player;
        if (player == null) return;

        long gameTime = player.level().getGameTime();
        interval = (int) ConfigManager.getDouble("mobvision.interval", 20);
        if (gameTime - lastTick < interval) return;
        lastTick = gameTime;

        MinecraftServer server = mc.getSingleplayerServer();
        if (server == null) return;

        int range = (int) ConfigManager.getDouble("mobvision.range", 25);
        String cmd = String.format("/effect give @e[type=!player,distance=..%d] minecraft:glowing 2 1 true", range);

        CommandSourceStack source = new CommandSourceStack(CommandSource.NULL,
            player.position(), player.getRotationVector(),
            player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null,
            4, player.getName().getString(), player.getDisplayName(), server, (Entity) player);

        server.getCommands().performPrefixedCommand(source, cmd);
    }
}
