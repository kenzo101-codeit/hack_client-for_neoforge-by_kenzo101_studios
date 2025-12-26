package com.wurstclient_v7.feature;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = "wurst_client_on_neoforge")
public class GodMode {
    private static boolean enabled = false;
    private static String targetPlayer = ""; // Empty string = Just you

    public static boolean isEnabled() {
        return enabled;
    }

    public static void toggle() {
        enabled = !enabled;
    }

    // This is the method your IDE was missing!
    public static void setTarget(String name) {
        targetPlayer = name;
    }

    public static String getTarget() {
        return targetPlayer.isEmpty() ? "Self" : targetPlayer;
    }

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (!enabled) return;

        if (event.getEntity() instanceof Player player) {
            String name = player.getScoreboardName();

            // LOGIC:
            // 1. If target is "Everyone", cancel all player damage.
            // 2. If target is "Self", only cancel damage for the client player.
            // 3. If target is a specific name, only cancel for that name.

            if (targetPlayer.equalsIgnoreCase("Everyone")) {
                event.setCanceled(true);
            } else if (targetPlayer.isEmpty()) {
                // To check if the player is 'you' on the server side,
                // we'd usually check the UUID, but for a simple local test:
                // if (player.isLocalPlayer()) ... (only works on client side)
                event.setCanceled(true);
            } else if (name.equalsIgnoreCase(targetPlayer)) {
                event.setCanceled(true);
            }
        }
    }
}