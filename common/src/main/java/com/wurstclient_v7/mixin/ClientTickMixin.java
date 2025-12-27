package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.KillAura;
import com.wurstclient_v7.input.KeybindManager;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.InputConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.Minecraft")
public class ClientTickMixin {
    private static boolean prevTogglePressed = false;
    private static boolean prevMenuPressed = false;
    private static int traceCounter = 0;
    private static boolean prevLeftPressed = false;
    private static boolean prevAutoPressed = false;
    private static boolean prevSpeedPressed = false;
    private static boolean prevMobPressed = false;
    private static boolean prevFBPressed = false;
    private static boolean prevFlightPressed = false;
    private static boolean prevNoFallPressed = false;
    private static boolean prevXRayPressed = false;
    private static boolean prevJetpackPressed = false;
    private static boolean prevNukerPressed = false;
    private static boolean prevSpiderPressed = false;
    private static boolean prevESPPressed = false;
    private static boolean prevTracersPressed = false;
    private static boolean prevAndromedaPressed = false;
    private static boolean prevSafeWalkPressed = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;

        // periodic trace to help detect missing key events (every ~20 ticks)
        traceCounter++;
        if (traceCounter % 20 == 0) {
            long win = mc.getWindow().getWindow();
            int rc = org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
            boolean glfwRc = org.lwjgl.glfw.GLFW.glfwGetKey(win, rc) == org.lwjgl.glfw.GLFW.GLFW_PRESS;
            boolean icRc = com.mojang.blaze3d.platform.InputConstants.isKeyDown(win, rc);
            System.out.println("[KEYTRACE] RCTRL GLFW=" + glfwRc + " IC=" + icRc + " bind=" + com.wurstclient_v7.input.KeybindManager.getLabel("open_menu"));
        }
        long window = mc.getWindow().getWindow();
        boolean pressed = KeybindManager.isPressed(window, "kill_aura_toggle");
        if (pressed && !prevTogglePressed) {
            KillAura.toggle();
            System.out.println("KillAura toggled: " + (KillAura.isEnabled() ? "ON" : "OFF"));
        }
        prevTogglePressed = pressed;

        // Menu key handling
        boolean menuPressed = KeybindManager.isPressed(window, "open_menu");
        // Debug: print label and pressed state for troubleshooting
        if (menuPressed && !prevMenuPressed) {
            System.out.println("[KEYDEBUG] open_menu pressed (label=" + KeybindManager.getLabel("open_menu") + ")");
            Minecraft.getInstance().setScreen(new com.wurstclient_v7.gui.ModuleScreen());
        } else if (menuPressed) {
            // occasional debug when continuously pressed (helps when user reports no effect)
            System.out.println("[KEYDEBUG] open_menu held (label=" + KeybindManager.getLabel("open_menu") + ")");
        }
        prevMenuPressed = menuPressed;

        // Autoattack toggle key
        boolean aaPressed = KeybindManager.isPressed(window, "autoattack_toggle");
        if (aaPressed && !prevAutoPressed) {
            com.wurstclient_v7.feature.AutoAttack.toggle();
            System.out.println("AutoAttack toggled: " + (com.wurstclient_v7.feature.AutoAttack.isEnabled() ? "ON" : "OFF"));
        }
        prevAutoPressed = aaPressed;

        // Speedhack toggle key
        boolean shPressed = KeybindManager.isPressed(window, "speedhack_toggle");
        if (shPressed && !prevSpeedPressed) {
            com.wurstclient_v7.feature.SpeedHack.toggle();
        }
        prevSpeedPressed = shPressed;

        // MobVision toggle key
        boolean mvPressed = KeybindManager.isPressed(window, "mobvision_toggle");
        if (mvPressed && !prevMobPressed) {
            com.wurstclient_v7.feature.MobVision.toggle();
            System.out.println("MobVision toggled: " + (com.wurstclient_v7.feature.MobVision.isEnabled() ? "ON" : "OFF"));
        }
        prevMobPressed = mvPressed;

        // FullBright toggle key
        boolean fbPressed = KeybindManager.isPressed(window, "fullbright_toggle");
        if (fbPressed && !prevFBPressed) {
            com.wurstclient_v7.feature.FullBright.toggle();
            System.out.println("FullBright toggled: " + (com.wurstclient_v7.feature.FullBright.isEnabled() ? "ON" : "OFF"));
        }
        prevFBPressed = fbPressed;

        // Flight toggle key
        boolean flightPressed = KeybindManager.isPressed(window, "flight_toggle");
        if (flightPressed && !prevFlightPressed) {
            com.wurstclient_v7.feature.Flight.toggle();
            System.out.println("Flight toggled: " + (com.wurstclient_v7.feature.Flight.isEnabled() ? "ON" : "OFF"));
        }
        prevFlightPressed = flightPressed;

        // NoFall toggle key
        boolean noFallPressed = KeybindManager.isPressed(window, "nofall_toggle");
        if (noFallPressed && !prevNoFallPressed) {
            com.wurstclient_v7.feature.NoFall.toggle();
            System.out.println("NoFall toggled: " + (com.wurstclient_v7.feature.NoFall.isEnabled() ? "ON" : "OFF"));
        }
        prevNoFallPressed = noFallPressed;

        // XRay toggle key
        boolean xrayPressed = KeybindManager.isPressed(window, "xray_toggle");
        if (xrayPressed && !prevXRayPressed) {
            com.wurstclient_v7.feature.XRay.toggle();
            System.out.println("XRay toggled: " + (com.wurstclient_v7.feature.XRay.isEnabled() ? "ON" : "OFF"));
        }
        prevXRayPressed = xrayPressed;

        // Jetpack toggle key
        boolean jetpackPressed = KeybindManager.isPressed(window, "jetpack_toggle");
        if (jetpackPressed && !prevJetpackPressed) {
            com.wurstclient_v7.feature.Jetpack.toggle();
            System.out.println("Jetpack toggled: " + (com.wurstclient_v7.feature.Jetpack.isEnabled() ? "ON" : "OFF"));
        }
        prevJetpackPressed = jetpackPressed;

        // Nuker toggle key
        boolean nukerPressed = KeybindManager.isPressed(window, "nuker_toggle");
        if (nukerPressed && !prevNukerPressed) {
            com.wurstclient_v7.feature.Nuker.toggle();
            System.out.println("Nuker toggled: " + (com.wurstclient_v7.feature.Nuker.isEnabled() ? "ON" : "OFF"));
        }
        prevNukerPressed = nukerPressed;

        // Spider toggle key
        boolean spiderPressed = KeybindManager.isPressed(window, "spider_toggle");
        if (spiderPressed && !prevSpiderPressed) {
            com.wurstclient_v7.feature.Spider.toggle();
            System.out.println("Spider toggled: " + (com.wurstclient_v7.feature.Spider.isEnabled() ? "ON" : "OFF"));
        }
        prevSpiderPressed = spiderPressed;

        // ESP toggle key
        boolean espPressed = KeybindManager.isPressed(window, "esp_toggle");
        if (espPressed && !prevESPPressed) {
            com.wurstclient_v7.feature.ESP.toggle();
            System.out.println("ESP toggled: " + (com.wurstclient_v7.feature.ESP.isEnabled() ? "ON" : "OFF"));
        }
        prevESPPressed = espPressed;

        // Tracers toggle key
        boolean tracersPressed = KeybindManager.isPressed(window, "tracers_toggle");
        if (tracersPressed && !prevTracersPressed) {
            com.wurstclient_v7.feature.Tracers.toggle();
            System.out.println("Tracers toggled: " + (com.wurstclient_v7.feature.Tracers.isEnabled() ? "ON" : "OFF"));
        }
        prevTracersPressed = tracersPressed;

        // Andromeda Bridge toggle key
        boolean andromedaPressed = KeybindManager.isPressed(window, "andromeda_toggle");
        if (andromedaPressed && !prevAndromedaPressed) {
            com.wurstclient_v7.feature.AndromedaBridge.toggle();
            System.out.println("Andromeda Bridge toggled: " + (com.wurstclient_v7.feature.AndromedaBridge.isEnabled() ? "ON" : "OFF"));
        }
        prevAndromedaPressed = andromedaPressed;

        boolean swPressed = KeybindManager.isPressed(window, "safewalk_toggle");
        if (swPressed && !prevSafeWalkPressed) {
            com.wurstclient_v7.feature.SafeWalk.toggle();
        }
        prevSafeWalkPressed = swPressed;

        // Mouse left click handling (for autoattack)
        boolean leftPressed = InputConstants.isKeyDown(window, org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT);
        if (leftPressed && !prevLeftPressed) {
            com.wurstclient_v7.feature.AutoAttack.onLeftClick();
        }
        prevLeftPressed = leftPressed;

        // Call feature tick handler
        KillAura.onClientTick();
        // SpeedHack tick handler (applies boosts when movement begins)
        com.wurstclient_v7.feature.SpeedHack.onClientTick();
        // MobVision and FullBright tick handlers for effects
        com.wurstclient_v7.feature.MobVision.onClientTick();
        com.wurstclient_v7.feature.FullBright.onClientTick();
        com.wurstclient_v7.feature.Flight.onClientTick();
        com.wurstclient_v7.feature.NoFall.onClientTick();
        com.wurstclient_v7.feature.Jetpack.onClientTick();
        com.wurstclient_v7.feature.Nuker.onClientTick();
        com.wurstclient_v7.feature.Spider.onClientTick();
        com.wurstclient_v7.feature.AndromedaBridge.onClientTick();
    }
}
