package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public final class LsdHack
{
	private static final Minecraft MC = Minecraft.getInstance();
	private static final ResourceLocation LSD_SHADER =
			new ResourceLocation("wurst_client_on_neoforge", "post_effect/lsd.json");

	private static boolean enabled = false;

	public static void toggle() {
		enabled = !enabled;
	}

	ConfigManager.setBoolean("lsd.enabled", enabled);

	public static boolean isEnabled() {
		return enabled;
	}

	public void enable()
	{
		if (enabled)
			return;

		// Must be in-game
		if (MC.level == null || MC.player == null)
			return;

		// Remove existing shader if any
		if (MC.gameRenderer.currentEffect() != null)
			MC.gameRenderer.shutdownEffect();

		MC.gameRenderer.loadEffect(LSD_SHADER);
		enabled = true;
	}

	public void disable()
	{
		if (!enabled)
			return;

		if (MC.gameRenderer.currentEffect() != null)
			MC.gameRenderer.shutdownEffect();

		enabled = false;
	}
}
