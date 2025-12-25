package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Animal;

public final class ESP {
    private static volatile boolean enabled = false;

    private ESP() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static boolean shouldGlow(Entity entity) {
        if (!enabled) return false;
        if (entity instanceof Player) return true;
        if (entity instanceof Monster) return true;
        if (entity instanceof Animal) return true;
        return false;
    }
}
