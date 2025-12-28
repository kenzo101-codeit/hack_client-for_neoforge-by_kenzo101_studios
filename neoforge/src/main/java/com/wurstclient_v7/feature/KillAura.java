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
        if (mc == null || mc.player == null || mc.level == null) return;

        // OPTIMIZATION: Only attack if the weapon is fully charged (1.0f)
        // This ensures maximum damage and knockback.
        if (mc.player.getAttackStrengthScale(0.5f) < 0.9f) return;

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity != mc.player && entity.isAlive() && mc.player.distanceTo(entity) <= range) {
                if (!(entity instanceof Player)) {
                    // Visual swing
                    mc.player.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                    // Logic attack
                    mc.gameMode.attack(mc.player, entity);
                    break;
                }
            }
        }
    }
}
