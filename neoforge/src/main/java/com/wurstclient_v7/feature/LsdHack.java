package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.NeoForgeConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public final class LsdHack {
	private static final Minecraft MC = Minecraft.getInstance();
	private static final ResourceLocation LSD_SHADER =
			ResourceLocation.fromNamespaceAndPath("wurst_client_on_neoforge", "post/lsd.json");

	private static boolean enabled = false;

	public static void toggle() {
		enabled = !enabled;
		NeoForgeConfigManager.setBoolean("lsd.enabled", enabled);
	}

	public static boolean isEnabled() {
		return enabled;
	}

	public void enable() {
		if (enabled)
			return;

		if (MC.level == null || MC.player == null)
			return;

		if (MC.gameRenderer.currentEffect() != null)
			MC.gameRenderer.shutdownEffect();

		MC.gameRenderer.loadEffect(LSD_SHADER);
		enabled = true;
	}

	public void disable() {
		if (!enabled)
			return;

		if (MC.gameRenderer.currentEffect() != null)
			MC.gameRenderer.shutdownEffect();

		enabled = false;
	}
}