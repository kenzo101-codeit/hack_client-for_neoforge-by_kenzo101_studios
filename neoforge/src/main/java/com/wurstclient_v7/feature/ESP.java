package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

public final class ESP {
    private static volatile boolean enabled = false;

    private ESP() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static boolean shouldGlow(Entity entity) {
        if (!enabled) return false;

        // DEBUG: Prevent the player from glowing themselves
        if (entity == Minecraft.getInstance().player) return false;

        // Glow if it's a Player, Monster, or Animal
        return entity instanceof Player ||
                entity instanceof Monster ||
                entity instanceof Animal;
    }
}