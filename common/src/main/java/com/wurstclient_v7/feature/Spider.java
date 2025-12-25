package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public final class Spider {
    private static volatile boolean enabled = false;

    private Spider() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null) return;

        if (mc.player.horizontalCollision) {
            Vec3 motion = mc.player.getDeltaMovement();
            if (motion.y < 0.2) {
                // Slightly faster climb
                mc.player.setDeltaMovement(motion.x, 0.25, motion.z);
            }
        }
    }
}
