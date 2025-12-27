package com.wurstclient_v7.gui;

import com.wurstclient_v7.config.ConfigManager;
import com.wurstclient_v7.feature.AndromedaBridge;
import com.wurstclient_v7.feature.AutoAttack;
import com.wurstclient_v7.feature.ESP;
import com.wurstclient_v7.feature.Flight;
import com.wurstclient_v7.feature.FullBright;
import com.wurstclient_v7.feature.Jetpack;
import com.wurstclient_v7.feature.KillAura;
import com.wurstclient_v7.feature.MobVision;
import com.wurstclient_v7.feature.NoFall;
import com.wurstclient_v7.feature.Nuker;
import com.wurstclient_v7.feature.SpeedHack;
import com.wurstclient_v7.feature.Spider;
import com.wurstclient_v7.feature.Tracers;
import com.wurstclient_v7.feature.XRay;
import com.wurstclient_v7.feature.GodMode;
import com.wurstclient_v7.feature.SafeWalk;
import com.wurstclient_v7.input.KeybindManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModuleScreen extends Screen {
    private final int WIDTH = 120;

    private final int HEIGHT = 262;

    private final int PADDING = 8;

    private String listeningAction;

    public ModuleScreen() {
        super((Component)Component.literal("My hack client"));
        this.listeningAction = null;
        System.out.println("[DEBUG] ModuleScreen constructor called.");
    }

    protected void init() {
        super.init();
    }

    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        System.out.println("[DEBUG] ModuleScreen render method called.");
        renderBackground(gfx, mouseX, mouseY, partialTick);
        int x = (this.width - 120) / 2;
        int y = (this.height - 262) / 2;
        gfx.fill(x, y, x + 120, y + 262, -1442831821);
        gfx.drawString(this.font, this.title, x + (120 - this.font.width(this.title.getString())) / 2, y + 8, -4599297, false);
        int lineY = y + 8 + 16;
        renderModule(gfx, x, lineY, "kill-aura", KillAura.isEnabled(), "kill_aura_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "autoattack", AutoAttack.isEnabled(), "autoattack_toggle");
        lineY += 12;
        String shLabel = "speedhack";
        String shStatus = SpeedHack.isEnabled() ? "ON" : "OFF";
        double shMult = ConfigManager.getDouble("speed.multiplier", 1.5D);
        gfx.drawString(this.font, shLabel, x + 8, lineY, -1, false);
        gfx.drawString(this.font, shStatus, x + 120 - 8 - this.font.width(shStatus) - 60, lineY, SpeedHack.isEnabled() ? -10027162 : -39322, false);
        String shMultText = String.format("x%.2f", new Object[] { Double.valueOf(shMult) });
        gfx.drawString(this.font, shMultText, x + 120 - 8 - this.font.width(shMultText), lineY, -3355444, false);
        String shBinding = (this.listeningAction != null && this.listeningAction.equals("speedhack_toggle")) ? "Press any key..." : KeybindManager.getLabel("speedhack_toggle");
        gfx.drawString(this.font, shBinding, x + 120 - 8 - this.font.width(shBinding), lineY, -86, false);
        lineY += 12;
        renderModule(gfx, x, lineY, "mobvision", MobVision.isEnabled(), "mobvision_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "fullbright", FullBright.isEnabled(), "fullbright_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "flight", Flight.isEnabled(), "flight_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "nofall", NoFall.isEnabled(), "nofall_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "xray", XRay.isEnabled(), "xray_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "jetpack", Jetpack.isEnabled(), "jetpack_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "nuker", Nuker.isEnabled(), "nuker_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "spider", Spider.isEnabled(), "spider_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "esp", ESP.isEnabled(), "esp_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "tracers", Tracers.isEnabled(), "tracers_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "andromeda", AndromedaBridge.isEnabled(), "andromeda_toggle");
        lineY += 12;
        renderModule(gfx, x, lineY, "safewalk", com.wurstclient_v7.feature.SafeWalk.isEnabled(), "safewalk_toggle");
        lineY += 12;
        // Now render GodMode on the next line
        renderModule(gfx, x, lineY, "godmode (" + GodMode.getTarget() + ")", GodMode.isEnabled(), "godmode_toggle");
        lineY += 12;

        super.render(gfx, mouseX, mouseY, partialTick);
    }

    private void renderModule(GuiGraphics gfx, int x, int y, String label, boolean enabled, String action) {
        String status = enabled ? "ON" : "OFF";
        gfx.drawString(this.font, label, x + 8, y, -1, false);
        gfx.drawString(this.font, status, x + 120 - 8 - this.font.width(status) - 40, y, enabled ? -10027162 : -39322, false);
        String binding = (this.listeningAction != null && this.listeningAction.equals(action)) ? "Press any key..." : KeybindManager.getLabel(action);
        gfx.drawString(this.font, binding, x + 120 - 8 - this.font.width(binding), y, -86, false);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.listeningAction != null) {
            KeybindManager.setKey(this.listeningAction, button, 0, true);
            this.listeningAction = null;
            return true;
        }
        int x = (this.width - 120) / 2;
        int y = (this.height - 262) / 2;
        int lineY = y + 8 + 16;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            KillAura.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("kill_aura_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            AutoAttack.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("autoattack_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            if (button == 1) {
                double[] presets = { 1.0D, 1.25D, 1.5D, 2.0D, 2.5D };
                double cur = ConfigManager.getDouble("speed.multiplier", 1.5D);
                int idx = 0;
                for (int i = 0; i < presets.length; ) {
                    if (Math.abs(presets[i] - cur) < 0.001D) {
                        idx = i;
                        break;
                    }
                    i++;
                }
                double next = presets[(idx + 1) % presets.length];
                ConfigManager.setDouble("speed.multiplier", next);
                SpeedHack.onClientTick();
            } else {
                SpeedHack.toggle();
            }
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("speedhack_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            MobVision.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("mobvision_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            FullBright.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("fullbright_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            Flight.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("flight_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            NoFall.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("nofall_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            XRay.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("xray_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            Jetpack.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("jetpack_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            Nuker.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("nuker_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            Spider.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("spider_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            ESP.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("esp_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            Tracers.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("tracers_toggle", button);
            return true;
        }
        lineY += 12;
        if (checkClick(mouseX, mouseY, x, lineY)) {
            AndromedaBridge.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("andromeda_toggle", button);
            return true;
        }

        // 1. Add SafeWalk Click handling
        if (checkClick(mouseX, mouseY, x, lineY)) {
            com.wurstclient_v7.feature.SafeWalk.toggle();
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("safewalk_toggle", button);
            return true;
        }
        lineY += 12;

        // 2. Add GodMode Click handling (Fixing the "a" typo and adding bind support)
        if (checkClick(mouseX, mouseY, x, lineY)) {
            if (button == 1) { // Right Click
                if (GodMode.getTarget().equals("Self")) {
                    GodMode.setTarget("Everyone");
                } else {
                    GodMode.setTarget("");
                }
            } else {
                GodMode.toggle();
            }
            return true;
        }
        if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
            handleBindClick("godmode_toggle", button);
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean checkClick(double mouseX, double mouseY, int x, int lineY) {
        return (mouseX >= (x + 8) && mouseX <= (x + 120 - 8 - 40) && mouseY >= (lineY - 2) && mouseY <= (lineY + 10));
    }

    private boolean checkBindClick(double mouseX, double mouseY, int x, int lineY, int button) {
        int bindStartX = x + 120 - 8 - 40;
        return (mouseX >= bindStartX && mouseX <= (x + 120 - 8) && mouseY >= (lineY - 2) && mouseY <= (lineY + 10));
    }

    private void handleBindClick(String action, int button) {
        if (button == 1) {
            KeybindManager.clear(action);
        } else {
            this.listeningAction = action;
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.listeningAction != null) {
            int mods = 0;
            if ((modifiers & 0x2) != 0)
                mods |= 0x2;
            if ((modifiers & 0x1) != 0)
                mods |= 0x1;
            if ((modifiers & 0x4) != 0)
                mods |= 0x4;
            if ((modifiers & 0x8) != 0)
                mods |= 0x8;
            KeybindManager.setKey(this.listeningAction, keyCode, mods, false);
            this.listeningAction = null;
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
