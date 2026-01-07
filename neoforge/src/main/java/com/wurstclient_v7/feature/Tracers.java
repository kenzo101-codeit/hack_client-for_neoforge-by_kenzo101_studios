package com.wurstclient_v7.feature;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
        System.out.println("Tracers: " + enabled);
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void render(Matrix4f matrix) {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        Vec3 camPos = mc.gameRenderer.getMainCamera().getPosition();
        Vec3 start = mc.player.getEyePosition().subtract(camPos);

        // IMPORTANT: This allows the lines to be seen through walls
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // Set the shader so Minecraft knows how to process these vertices
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        MultiBufferSource.BufferSource buffers = mc.renderBuffers().bufferSource();

        // We use a basic LineStrip or Lines format
        VertexConsumer consumer = buffers.getBuffer(RenderType.lines());

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (!(entity instanceof Player) || entity == mc.player) continue;

            Vec3 end = entity.getPosition(1.0F)
                    .add(0, entity.getBbHeight() / 2.0, 0)
                    .subtract(camPos);

            // First Vertex (Start at your eyes)
            consumer.addVertex(matrix, (float) start.x, (float) start.y, (float) start.z)
                    .setColor(0f, 1f, 0f, 1f)
                    .setNormal(0f, 1f, 0f); // If setNormal fails, try just ending the chain here

            // Second Vertex (End at the target player)
            consumer.addVertex(matrix, (float) end.x, (float) end.y, (float) end.z)
                    .setColor(0f, 1f, 0f, 1f)
                    .setNormal(0f, 1f, 0f);
        }

        // Force the buffer to draw NOW
        buffers.endBatch(RenderType.lines());

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}
