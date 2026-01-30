package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import com.wurstclient_v7.mixin.GameRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public final class LsdHack {
	private static final Minecraft MC = Minecraft.getInstance();
	private static final ResourceLocation LSD_SHADER =
			ResourceLocation.fromNamespaceAndPath("wurst_client_on_neoforge", "assets/post/lsd.json");

	private static boolean enabled = false;

	public static void toggle() {
		if (isEnabled()) {
			disable();
		} else {
			enable();
		}
	}

	public static boolean isEnabled() {
		return enabled;
	}

	public static void enable() {
		if (enabled) return;

		MC.execute(() -> {
			if (MC.level == null || MC.player == null) return;

			// Use a local cast to avoid instance variable headaches
			GameRendererAccessor accessor = (GameRendererAccessor) MC.gameRenderer;

			if (MC.gameRenderer.currentEffect() != null)
				MC.gameRenderer.shutdownEffect();

			try {
				accessor.wurst$loadEffectPublic(LSD_SHADER);
				enabled = true;
				ConfigManager.setBoolean("lsd.enabled", true);
			} catch (Exception e) {
				System.err.println("Failed to load LSD Shader: " + e.getMessage());
			}
		});
	}

	public static void disable() {
		if (!enabled) return;

		MC.execute(() -> {
			if (MC.gameRenderer.currentEffect() != null)
				MC.gameRenderer.shutdownEffect();

			enabled = false;
			ConfigManager.setBoolean("lsd.enabled", false);
		});
	}
}