package com.wurstclient_v7.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class InGameHudMixin {
    @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V", at = @At("TAIL"))
    @SuppressWarnings("null")
    private void onRender(net.minecraft.client.gui.GuiGraphics guiGraphics, net.minecraft.client.DeltaTracker tracker, CallbackInfo ci) {
        // This is a harmless example: print to the console when feature is enabled.
        if (com.wurstclient_v7.feature.KillAura.isEnabled()) {
            System.out.println("KillAura is ON (from InGameHudMixin)");
        }
    }
}
