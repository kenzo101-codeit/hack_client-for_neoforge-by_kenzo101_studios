package com.wurstclient_v7.mixin;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity, S extends EntityRenderState>
{
	@Inject(
			method = "extractRenderState(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;F)V",
			at = @At("TAIL")
	)
	private void addHealthTag(T entity, S state, float tickDelta, CallbackInfo ci)
	{
		if (state.nameTag == null)
			return;

		if (!(entity instanceof LivingEntity living))
			return;

		HealthTagsHack hack = YourMod.INSTANCE.healthTags;
		if (!hack.enabled)
			return;

		state.nameTag = hack.addHealth(living, state.nameTag);
	}
}
