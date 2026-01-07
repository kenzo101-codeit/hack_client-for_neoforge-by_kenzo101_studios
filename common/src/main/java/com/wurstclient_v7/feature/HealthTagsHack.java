package com.wurstclient_v7.feature;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public final class HealthTagsHack
{
	private static boolean enabled = false;

	public static void toggle() {
		enabled = !enabled;
	}

	ConfigManager.setBoolean("healthtags.enabled", enabled);

	public static boolean isEnabled() {
		return enabled;
	}

	public Component addHealth(LivingEntity entity, Component name)
	{
		int health = (int)Math.ceil(entity.getHealth());

		ChatFormatting color;
		if (health <= 5) color = ChatFormatting.RED;
		else if (health <= 10) color = ChatFormatting.GOLD;
		else if (health <= 15) color = ChatFormatting.YELLOW;
		else color = ChatFormatting.GREEN;

		Component healthComp = Component.literal(" " + health)
				.withStyle(color);

		return name.copy().append(healthComp);
	}
}
