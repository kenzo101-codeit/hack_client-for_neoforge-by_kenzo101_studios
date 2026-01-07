package com.wurstclient_v7.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.world.entity.player.Player;

@EventBusSubscriber
public final class ClientRenderEvents {

	@SubscribeEvent
	public static void onRenderLevel(RenderLevelStageEvent event) {
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES)
			return;

		Minecraft mc = Minecraft.getInstance();
		if (mc.level == null || mc.player == null) return;

		PoseStack poseStack = event.getPoseStack();
		Vec3 camera = event.getCamera().getPosition();

		RenderSystem.disableDepthTest();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		MultiBufferSource.BufferSource buffer =
				mc.renderBuffers().bufferSource();

		for (Entity entity : mc.level.entitiesForRendering()) {
			if (entity == mc.player) continue;
			if (!(entity instanceof Player)) continue;

			drawTracer(poseStack, buffer, camera, entity);
		}

		buffer.endBatch(RenderType.lines());

		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
	}

	private static void drawTracer(
			PoseStack poseStack,
			MultiBufferSource buffer,
			Vec3 camera,
			Entity target
	) {
		VertexConsumer vc = buffer.getBuffer(RenderType.lines());
		Matrix4f matrix = poseStack.last().pose();

		float tx = (float) (target.getX() - camera.x);
		float ty = (float) (target.getY() + target.getBbHeight() * 0.5 - camera.y);
		float tz = (float) (target.getZ() - camera.z);

		vc.addVertex(matrix, 0, 0, 0)
				.setColor(0, 255, 0, 255);

		vc.addVertex(matrix, tx, ty, tz)
				.setColor(0, 255, 0, 255);
	}
}