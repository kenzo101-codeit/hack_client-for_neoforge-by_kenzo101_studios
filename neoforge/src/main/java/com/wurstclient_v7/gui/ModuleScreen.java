package com.wurstclient_v7.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import com.wurstclient_v7.feature.KillAura;
import com.wurstclient_v7.config.ConfigManager;
import com.wurstclient_v7.input.KeybindManager;
import org.lwjgl.glfw.GLFW;

public class ModuleScreen extends Screen {
    private final int WIDTH = 120;
    private final int HEIGHT = 262; // Increased height to fit more modules
    private final int PADDING = 8;

    public ModuleScreen() {
        super(Component.literal("My hack client"));
        System.out.println("[DEBUG] ModuleScreen constructor called.");
    }
    private String listeningAction = null; // action name we're capturing, null when idle
    @Override
    protected void init() {
        super.init();
    }

    @Override
    @SuppressWarnings("null")
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        System.out.println("[DEBUG] ModuleScreen render method called.");
        // Dim background
        this.renderBackground(gfx, mouseX, mouseY, partialTick);

        int x = (this.width - WIDTH) / 2;
        int y = (this.height - HEIGHT) / 2;

        // Panel background
        gfx.fill(x, y, x + WIDTH, y + HEIGHT, 0xAA002233);

        // Title (centered)
        gfx.drawString(this.font, this.title, x + (WIDTH - this.font.width(this.title.getString())) / 2, y + PADDING, 0xFFB9D1FF, false);

        // List modules
        int lineY = y + PADDING + 16;

        // Kill-aura entry
        renderModule(gfx, x, lineY, "kill-aura", KillAura.isEnabled(), "kill_aura_toggle");
        lineY += 12;

        // Autoattack entry
        renderModule(gfx, x, lineY, "autoattack", com.wurstclient_v7.feature.AutoAttack.isEnabled(), "autoattack_toggle");
        lineY += 12;

        // Speedhack entry
        String shLabel = "speedhack";
        String shStatus = com.wurstclient_v7.feature.SpeedHack.isEnabled() ? "ON" : "OFF";
        double shMult = ConfigManager.getDouble("speed.multiplier", 1.5);
        gfx.drawString(this.font, shLabel, x + PADDING, lineY, 0xFFFFFFFF, false);
        gfx.drawString(this.font, shStatus, x + WIDTH - PADDING - this.font.width(shStatus) - 60, lineY, com.wurstclient_v7.feature.SpeedHack.isEnabled() ? 0xFF66FF66 : 0xFFFF6666, false);
        String shMultText = String.format("x%.2f", shMult);
        gfx.drawString(this.font, shMultText, x + WIDTH - PADDING - this.font.width(shMultText), lineY, 0xFFCCCCCC, false);
        String shBinding = listeningAction != null && listeningAction.equals("speedhack_toggle") ? "Press any key..." : KeybindManager.getLabel("speedhack_toggle");
        gfx.drawString(this.font, shBinding, x + WIDTH - PADDING - this.font.width(shBinding), lineY, 0xFFFFFFAA, false);
        lineY += 12;

        // MobVision entry
        renderModule(gfx, x, lineY, "mobvision", com.wurstclient_v7.feature.MobVision.isEnabled(), "mobvision_toggle");
        lineY += 12;

        // FullBright entry
        renderModule(gfx, x, lineY, "fullbright", com.wurstclient_v7.feature.FullBright.isEnabled(), "fullbright_toggle");
        lineY += 12;

        // Flight entry
        renderModule(gfx, x, lineY, "flight", com.wurstclient_v7.feature.Flight.isEnabled(), "flight_toggle");
        lineY += 12;

        // NoFall entry
        renderModule(gfx, x, lineY, "nofall", com.wurstclient_v7.feature.NoFall.isEnabled(), "nofall_toggle");
        lineY += 12;

        // XRay entry
        renderModule(gfx, x, lineY, "xray", com.wurstclient_v7.feature.XRay.isEnabled(), "xray_toggle");
        lineY += 12;

        // Jetpack entry
        renderModule(gfx, x, lineY, "jetpack", com.wurstclient_v7.feature.Jetpack.isEnabled(), "jetpack_toggle");
        lineY += 12;

        // Nuker entry
        renderModule(gfx, x, lineY, "nuker", com.wurstclient_v7.feature.Nuker.isEnabled(), "nuker_toggle");
        lineY += 12;

        // Spider entry
        renderModule(gfx, x, lineY, "spider", com.wurstclient_v7.feature.Spider.isEnabled(), "spider_toggle");
        lineY += 12;

        // ESP entry
        renderModule(gfx, x, lineY, "esp", com.wurstclient_v7.feature.ESP.isEnabled(), "esp_toggle");
        lineY += 12;

        // Tracers entry
        renderModule(gfx, x, lineY, "tracers", com.wurstclient_v7.feature.Tracers.isEnabled(), "tracers_toggle");
        lineY += 12;

        // Andromeda Bridge entry
        renderModule(gfx, x, lineY, "andromeda", com.wurstclient_v7.feature.AndromedaBridge.isEnabled(), "andromeda_toggle");
        lineY += 12;

        super.render(gfx, mouseX, mouseY, partialTick);
    }

    private void renderModule(GuiGraphics gfx, int x, int y, String label, boolean enabled, String action) {
        String status = enabled ? "ON" : "OFF";
        gfx.drawString(this.font, label, x + PADDING, y, 0xFFFFFFFF, false);
        gfx.drawString(this.font, status, x + WIDTH - PADDING - this.font.width(status) - 40, y, enabled ? 0xFF66FF66 : 0xFFFF6666, false);
        String binding = listeningAction != null && listeningAction.equals(action) ? "Press any key..." : KeybindManager.getLabel(action);
        gfx.drawString(this.font, binding, x + WIDTH - PADDING - this.font.width(binding), y, 0xFFFFFFAA, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // If we're capturing, use mouse button as the bind
        if (listeningAction != null) {
            KeybindManager.setKey(listeningAction, button, 0, true);
            listeningAction = null;
            return true;
        }
        int x = (this.width - WIDTH) / 2;
        int y = (this.height - HEIGHT) / 2;
        int lineY = y + PADDING + 16;

        // Kill-aura
        if (checkClick(mouseX, mouseY, x, lineY)) {
            KillAura.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("kill_aura_toggle", button);
             return true;
        }
        lineY += 12;

        // Autoattack
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.AutoAttack.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("autoattack_toggle", button);
             return true;
        }
        lineY += 12;

        // Speedhack
        if (checkClick(mouseX, mouseY, x, lineY)) {
            if (button == 1) {
                // cycle presets on right click
                double[] presets = {1.0, 1.25, 1.5, 2.0, 2.5};
                double cur = ConfigManager.getDouble("speed.multiplier", 1.5);
                int idx = 0;
                for (int i = 0; i < presets.length; i++) if (Math.abs(presets[i] - cur) < 0.001) { idx = i; break; }
                double next = presets[(idx + 1) % presets.length];
                ConfigManager.setDouble("speed.multiplier", next);
                com.wurstclient_v7.feature.SpeedHack.onClientTick();
            } else {
                com.wurstclient_v7.feature.SpeedHack.toggle();
            }
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("speedhack_toggle", button);
             return true;
        }
        lineY += 12;

        // MobVision
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.MobVision.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("mobvision_toggle", button);
             return true;
        }
        lineY += 12;

        // FullBright
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.FullBright.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("fullbright_toggle", button);
             return true;
        }
        lineY += 12;

        // Flight
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.Flight.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("flight_toggle", button);
             return true;
        }
        lineY += 12;

        // NoFall
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.NoFall.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("nofall_toggle", button);
             return true;
        }
        lineY += 12;

        // XRay
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.XRay.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("xray_toggle", button);
             return true;
        }
        lineY += 12;

        // Jetpack
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.Jetpack.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("jetpack_toggle", button);
             return true;
        }
        lineY += 12;

        // Nuker
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.Nuker.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("nuker_toggle", button);
             return true;
        }
        lineY += 12;

        // Spider
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.Spider.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("spider_toggle", button);
             return true;
        }
        lineY += 12;

        // ESP
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.ESP.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("esp_toggle", button);
             return true;
        }
        lineY += 12;

        // Tracers
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.Tracers.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("tracers_toggle", button);
             return true;
        }
        lineY += 12;

        // Andromeda Bridge
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.AndromedaBridge.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
             handleBindClick("andromeda_toggle", button);
             return true;
        }
        lineY += 12;

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean checkClick(double mouseX, double mouseY, int x, int lineY) {
        return mouseX >= x + PADDING && mouseX <= x + WIDTH - PADDING - 40 && mouseY >= lineY - 2 && mouseY <= lineY + 10;
    }

    private boolean checkBindClick(double mouseX, double mouseY, int x, int lineY, int button) {
        int bindStartX = x + WIDTH - PADDING - 40;
        return mouseX >= bindStartX && mouseX <= x + WIDTH - PADDING && mouseY >= lineY - 2 && mouseY <= lineY + 10;
    }

    private void handleBindClick(String action, int button) {
        if (button == 1) {
            KeybindManager.clear(action);
        } else {
            listeningAction = action;
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (listeningAction != null) {
            // capture key for the action, using modifier mask from GLFW
            int mods = 0;
            if ((modifiers & GLFW.GLFW_MOD_CONTROL) != 0) mods |= 2;
            if ((modifiers & GLFW.GLFW_MOD_SHIFT) != 0) mods |= 1;
            if ((modifiers & GLFW.GLFW_MOD_ALT) != 0) mods |= 4;
            if ((modifiers & GLFW.GLFW_MOD_SUPER) != 0) mods |= 8;
            KeybindManager.setKey(listeningAction, keyCode, mods, false);
            listeningAction = null;
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
