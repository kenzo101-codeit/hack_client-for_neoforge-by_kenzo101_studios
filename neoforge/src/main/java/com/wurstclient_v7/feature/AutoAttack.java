package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public final class AutoAttack {
    private static volatile boolean enabled = false;
    private static int range = 6;
    private static long lastTick = 0;
    private static int delayTicks = 2;

    private AutoAttack() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static void setRange(int r) { range = r; }

    // Triggered on a left click event (client-side)
    public static void onLeftClick() {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null || mc.crosshairPickEntity == null) return;

        Entity target = mc.crosshairPickEntity;

        // Check range and if it's alive
        if (target.isAlive() && mc.player.distanceTo(target) <= range) {
            // Attack the entity you are looking at
            mc.gameMode.attack(mc.player, target);
            // Note: Minecraft naturally swings the arm on left click,
            // so we don't necessarily need mc.player.swing() here.
        }
    }
}
