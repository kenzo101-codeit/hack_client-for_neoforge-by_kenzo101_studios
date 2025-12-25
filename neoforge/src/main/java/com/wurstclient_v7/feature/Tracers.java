package com.wurstclient_v7.feature;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class Tracers {
    private static volatile boolean enabled = false;

    private Tracers() {
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void toggle() {
        enabled = !enabled;
    }

    public static void onRender(PoseStack poseStack, float partialTicks) {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        // 1.21 Change: begin() now takes the format and returns the BufferBuilder directly
        com.mojang.blaze3d.vertex.BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        poseStack.pushPose();
        Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        Vec3 start = mc.player.getEyePosition(partialTicks);
        Matrix4f matrix = poseStack.last().pose();

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity == mc.player) continue;
            if (!(entity instanceof Player)) continue;

            Vec3 pos = entity.getPosition(partialTicks);

            // 1.21 Change: vertex() method order matters
            buffer.addVertex(matrix, (float) start.x, (float) start.y, (float) start.z)
                    .setColor(0, 255, 0, 255);

            buffer.addVertex(matrix, (float) pos.x, (float) pos.y + entity.getEyeHeight() / 2, (float) pos.z)
                    .setColor(0, 255, 0, 255);
        }

        // 1.21 Change: BufferUploader is now often used to finish the draw
        com.mojang.blaze3d.vertex.MeshData meshData = buffer.build();
        if (meshData != null) {
            com.mojang.blaze3d.vertex.BufferUploader.drawWithShader(meshData);
        }

        poseStack.popPose();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}
