package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public final class Jetpack {
    private static volatile boolean enabled = false;

    private Jetpack() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null) return;

        if (mc.options.keyJump.isDown()) {
            Vec3 motion = mc.player.getDeltaMovement();
            // A stronger upward force to ensure it's noticeable
            double y = 0.6;
            mc.player.setDeltaMovement(motion.x, y, motion.z);
        }
    }
}
