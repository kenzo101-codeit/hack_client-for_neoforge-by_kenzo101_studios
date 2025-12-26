package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public final class SpeedHack {
    private static volatile boolean enabled = false;
    private static boolean wasMoving = false;

    private SpeedHack() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        System.out.println("SpeedHack toggled: " + (enabled ? "ON" : "OFF") + ", multiplier=" + ConfigManager.getDouble("speed.multiplier", 1.5));
    }

    // Called once per client tick to apply a one-shot speed boost when movement begins
    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;
        if (mc.player == null) return;
        Player player = mc.player;

        Vec3 dv = player.getDeltaMovement();
        double horiz = Math.sqrt(dv.x * dv.x + dv.z * dv.z);
        boolean movingNow = horiz > 0.01;
        if (movingNow && !wasMoving) {
            double multiplier = ConfigManager.getDouble("speed.multiplier", 1.5);
            player.setDeltaMovement(dv.x * multiplier, dv.y, dv.z * multiplier);
        }
        wasMoving = movingNow;
    }
}
