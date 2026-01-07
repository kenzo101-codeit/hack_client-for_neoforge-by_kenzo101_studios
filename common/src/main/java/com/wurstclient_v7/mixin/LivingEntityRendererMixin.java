package com.wurstclient_v7.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wurstclient_v7.client.HealthTagClientCache;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

	@Inject(
			method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At("TAIL")
	)
	private void addHealthTag(T entity, float yaw, float partialTicks,
	                          PoseStack poseStack, MultiBufferSource buffer, int packedLight,
	                          CallbackInfo ci) {
		var entry = HealthTagClientCache.get(entity.getId());
		if (entry == null) return;

		// Format with one decimal (e.g., 12.3 / 20.0)
		String text = String.format("%.1f / %.1f", entry.health, entry.max);

		// Position slightly above the entity
		poseStack.pushPose();
		poseStack.translate(0.0, entity.getBbHeight() + 0.5, 0.0);
		poseStack.scale(0.01f, 0.01f, 0.01f); // scale down for world space

		Font font = Minecraft.getInstance().font;
		int width = font.width(text);
		font.drawInBatch(text, -width / 2f, 0, 0xFFFFFF, false, poseStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, packedLight);

		poseStack.popPose();
	}
}
