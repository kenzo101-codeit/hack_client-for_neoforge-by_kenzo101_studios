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
        if (mc.player == null || mc.getConnection() == null) return;

        if (mc.player.fallDistance > 2.0f) {
            // In 1.21.1, getNetHandler() is now getConnection()
            mc.getConnection().send(new ServerboundMovePlayerPacket.PosRot(
                    mc.player.getX(),
                    mc.player.getY(),
                    mc.player.getZ(),
                    mc.player.getYRot(),
                    mc.player.getXRot(),
                    true // Tells the server we are 'on ground' to reset fall damage
            ));
            mc.player.fallDistance = 0;
        }
    }
}
