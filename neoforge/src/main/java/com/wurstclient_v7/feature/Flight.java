package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;

public final class Flight {
    private static volatile boolean enabled = false;

    private Flight() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        Minecraft mc = Minecraft.getInstance();
        if (mc != null && mc.player != null) {
            if (enabled) {
                mc.player.getAbilities().mayfly = true;
                mc.player.getAbilities().flying = true;
            } else {
                mc.player.getAbilities().flying = false;
                if (!mc.player.isCreative() && !mc.player.isSpectator()) {
                    mc.player.getAbilities().mayfly = false;
                }
            }
            mc.player.onUpdateAbilities();
        }
    }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null) return;
        
        if (!mc.player.getAbilities().mayfly) {
             mc.player.getAbilities().mayfly = true;
             mc.player.onUpdateAbilities();
        }
        // Ensure flying is active if enabled
        if (!mc.player.getAbilities().flying) {
            mc.player.getAbilities().flying = true;
            mc.player.onUpdateAbilities();
        }
    }
}
