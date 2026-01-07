package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

EventBusSubscriber
public final class Glide {

	private static boolean enabled = false;

	public static void toggle() {
		enabled = !enabled;
	}

	ConfigManager.setBoolean("glide.enabled", enabled);

	public static boolean isEnabled() {
		return enabled;
	}

	// tweak this
	private static final double FALL_SPEED = 0.08; // vanilla gravity ≈ 0.08

	@SubscribeEvent
	public static void onClientTick(net.neoforged.neoforge.client.event.ClientTickEvent.Post event) {
		if (!enabled) return;

		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		if (player == null) return;

		// Pause while sneaking (optional)
		if (player.isShiftKeyDown()) return;

		// Don't interfere with these states
		if (player.isFallFlying()
				|| player.isInWater()
				|| player.isInLava()
				|| player.onGround()) {
			return;
		}

		Vec3 v = player.getDeltaMovement();

		// Only when falling
		if (v.y < 0) {
			player.setDeltaMovement(
					v.x,
					Math.max(v.y, -FALL_SPEED),
					v.z
			);
		}
	}
}
