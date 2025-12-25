package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

public final class NoFall {
    private static volatile boolean enabled = false;

    private NoFall() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null) return;

        if (mc.player.fallDistance > 2.5f) {
            // Send a packet saying we are on the ground
            mc.player.connection.send(new ServerboundMovePlayerPacket.StatusOnly(true));
            // Reset fall distance client-side
            mc.player.fallDistance = 0;
        }
    }
}
