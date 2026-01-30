package com.wurstclient_v7.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wurstclient_v7.config.NeoForgeConfigManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public final class Tracers {

    private static boolean enabled = false;

    public static void toggle() {
        enabled = !enabled;
        NeoForgeConfigManager.setBoolean("tracers.enabled", enabled);
        System.out.println("Tracers: " + enabled);
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void render(PoseStack poseStack, float partialTick) {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();

        Vec3 start = mc.player.getEyePosition(partialTick).subtract(camPos);

        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        MultiBufferSource.BufferSource buffers = mc.renderBuffers().bufferSource();
        VertexConsumer consumer = buffers.getBuffer(RenderType.lines());

        Matrix4f matrix = poseStack.last().pose();

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (!(entity instanceof Player) || entity == mc.player) continue;

            Vec3 end = entity.getPosition(partialTick)
                    .add(0, entity.getBbHeight() / 2.0, 0)
                    .subtract(camPos);

            consumer.addVertex(matrix, (float) start.x, (float) start.y, (float) start.z)
                    .setColor(0f, 1f, 0f, 1f);

            consumer.addVertex(matrix, (float) end.x, (float) end.y, (float) end.z)
                    .setColor(0f, 1f, 0f, 1f);
        }

        buffers.endBatch(RenderType.lines());

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}
