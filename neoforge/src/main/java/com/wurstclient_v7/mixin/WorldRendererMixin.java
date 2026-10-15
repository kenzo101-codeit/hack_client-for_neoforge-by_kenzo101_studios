package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.Tracers;
import com.mojang.blaze3d.vertex.PoseStack;
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

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {

    // 1.21 dropped the leading PoseStack parameter and added the frustum matrix,
    // so the signature is (DeltaTracker, boolean, Camera, GameRenderer, LightTexture, frustumMatrix, projectionMatrix).
    @Inject(
            method = "renderLevel(Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V",
            at = @At("TAIL"),
            remap = false
    )
    private void onRenderLevel(
            DeltaTracker deltaTracker,
            boolean renderBlockOutline,
            Camera camera,
            GameRenderer gameRenderer,
            LightTexture lightTexture,
            Matrix4f frustumMatrix,
            Matrix4f projectionMatrix,
            CallbackInfo ci
    ) {
        // Vanilla builds its world-space stack the same way (see LevelRenderer.renderLevel).
        PoseStack poseStack = new PoseStack();
        poseStack.mulPose(frustumMatrix);

        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);
        Tracers.render(poseStack, partialTick);
    }
}
