package com.wurstclient_v7.gui;

import java.util.Map;
import com.wurstclient_v7.config.NeoForgeConfigManager;
import com.wurstclient_v7.feature.*;
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
        renderBackground(gfx, mouseX, mouseY, partialTick);

        // Calculate dynamic height based on registry size + title space
        int totalModules = ModuleRegistry.MODULES.size();
        int dynamicHeight = 32 + (totalModules * 12);

        int x = (this.width - WIDTH) / 2;
        int y = (this.height - dynamicHeight) / 2;

        // Draw Background Box
        gfx.fill(x, y, x + WIDTH, y + dynamicHeight, -1442831821);

        // Draw Title
        gfx.drawString(this.font, this.title, x + (WIDTH - this.font.width(this.title.getString())) / 2, y + 8, -4599297, false);

        int lineY = y + 24;

        // ONLY use the loop. Do not hard-code kill-aura, speedhack, etc. here.
        for (Map.Entry<String, ModuleRegistry.ModuleToggle> entry : ModuleRegistry.MODULES.entrySet()) {
            String modName = entry.getKey();
            boolean enabled = entry.getValue().isEnabled();

            // Use your existing renderModule helper
            renderModule(gfx, x, lineY, modName.toLowerCase(), enabled, modName.toLowerCase() + "_toggle");

            // Special case for speedhack multiplier display if you want it
            if (modName.equalsIgnoreCase("speedhack")) {
                double shMult = NeoForgeConfigManager.getDouble("speed.multiplier", 1.5D);
                String shMultText = String.format("x%.2f", shMult);
                gfx.drawString(this.font, shMultText, x + WIDTH - 45 - this.font.width(shMultText), lineY, -3355444, false);
            }

            lineY += 12;
        }

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

        int totalModules = ModuleRegistry.MODULES.size();
        int dynamicHeight = 32 + (totalModules * 12);
        int x = (this.width - WIDTH) / 2;
        int y = (this.height - dynamicHeight) / 2;
        int lineY = y + 24;

        for (Map.Entry<String, ModuleRegistry.ModuleToggle> entry : ModuleRegistry.MODULES.entrySet()) {
            String modName = entry.getKey();
            String action = modName.toLowerCase() + "_toggle";

            // Check if we clicked the toggle or the bind area
// Check if we clicked the toggle or the bind area
            if (checkClick(mouseX, mouseY, x, lineY)) {
                // 1. Get the toggle logic using a switch or if/else block
                toggleModuleByName(modName);
                return true;
            }

            if (checkBindClick(mouseX, mouseY, x, lineY, button)) {
                handleBindClick(action, button);
                return true;
            }

            lineY += 12;
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

    private void toggleModuleByName(String name) {
        switch (name.toLowerCase()) {
            case "killaura": KillAura.toggle(); break;
            case "autoattack": AutoAttack.toggle(); break;
            case "speedhack": SpeedHack.toggle(); break;
            case "fullbright": FullBright.toggle(); break;
            case "flight": Flight.toggle(); break;
            case "nofall": NoFall.toggle(); break;
            case "xray": XRay.toggle(); break;
            case "jetpack": Jetpack.toggle(); break;
            case "nuker": Nuker.toggle(); break;
            case "spider": Spider.toggle(); break;
            case "esp": ESP.toggle(); break;
            case "tracers": Tracers.toggle(); break;
            case "andromeda": AndromedaBridge.toggle(); break;
            case "safewalk": com.wurstclient_v7.feature.SafeWalk.toggle(); break;
            case "godmode": GodMode.toggle(); break;
            case "lsd": LsdHack.toggle(); break;
        }
    }
}
