package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.Tracers;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LevelRenderer.class, remap = false)
public class WorldRendererMixin {

    @Inject(method = "renderLevel", at = @At("TAIL"))
    private void onRenderLevel(
            DeltaTracker deltaTracker,
            boolean renderBlockOutline,
            Camera camera,
            GameRenderer gameRenderer,
            LightTexture lightTexture,
            Matrix4f projectionMatrix,
            Matrix4f modelViewMatrix,
            CallbackInfo ci
    ) {
        float partialTicks = deltaTracker.getGameTimeDeltaPartialTick(false);
        Tracers.render(modelViewMatrix);
    }
}