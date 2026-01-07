package com.wurstclient_v7.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Gui.class, remap = false)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.player == null) return;

        int x = 5;
        int y = 5;
        int color = 0xFF00FF00;

        // Draw Watermark
        guiGraphics.drawString(mc.font, "My Hack Client For 1.21.1 NeoForge v1.0", x, y, 0xFFFFFFFF, true);
        y += 12;

        String[] modules = {
                "AndromedaBridge", "AutoAttack", "ESP", "Flight",
                "FullBright", "Jetpack", "KillAura",
                "NoFall", "Nuker", "SpeedHack", "Spider", "Tracers", "XRay", "SafeWalk", "GodMode", "Glide", "Freecam", "LSD", "Jesus"
        };

        for (String mod : modules) {
            if (isModEnabled(mod)) {
                guiGraphics.drawString(mc.font, "[+] " + mod, x, y, color, true);
                y += 10;
            }
        }
    }

    private boolean isModEnabled(String name) {
        return switch (name) {
            case "AndromedaBridge" -> com.wurstclient_v7.feature.AndromedaBridge.isEnabled();
            case "AutoAttack" -> com.wurstclient_v7.feature.AutoAttack.isEnabled();
            case "ESP" -> com.wurstclient_v7.feature.ESP.isEnabled();
            case "Flight" -> com.wurstclient_v7.feature.Flight.isEnabled();
            case "FullBright" -> com.wurstclient_v7.feature.FullBright.isEnabled();
            case "Jetpack" -> com.wurstclient_v7.feature.Jetpack.isEnabled();
            case "KillAura" -> com.wurstclient_v7.feature.KillAura.isEnabled();
            case "NoFall" -> com.wurstclient_v7.feature.NoFall.isEnabled();
            case "Nuker" -> com.wurstclient_v7.feature.Nuker.isEnabled();
            case "SpeedHack" -> com.wurstclient_v7.feature.SpeedHack.isEnabled();
            case "Spider" -> com.wurstclient_v7.feature.Spider.isEnabled();
            case "Tracers" -> com.wurstclient_v7.feature.Tracers.isEnabled();
            case "XRay" -> com.wurstclient_v7.feature.XRay.isEnabled();
            case "SafeWalk" -> com.wurstclient_v7.feature.SafeWalk.isEnabled();
            case "GodMode" -> com.wurstclient_v7.feature.GodMode.isEnabled();
            case "Glide" -> com.wurstclient_v7.feature.Glide.isEnabled();
            case "Freecam" -> com.wurstclient_v7.feature.Freecam.isEnabled();
            case "LSD" -> com.wurstclient_v7.feature.LSD.isEnabled();
            case "Jesus" -> com.wurstclient_v7.feature.Jesus.isEnabled();
            default -> false;
        };
    }
}