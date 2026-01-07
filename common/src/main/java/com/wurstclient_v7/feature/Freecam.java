package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public final class Freecam {

	private static boolean enabled = false;

	public static void toggle() {
		enabled = !enabled;
	}

	ConfigManager.setBoolean("freecam.enabled", enabled);

	public static boolean isEnabled() {
		return enabled;
	}

	private static Vec3 storedPos;
	private static float storedYaw;
	private static float storedPitch;

	private static final double SPEED = 0.5;

	public static void toggle() {
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		if (player == null) return;

		enabled = !enabled;

		if (enabled) {
			storedPos = player.position();
			storedYaw = player.getYRot();
			storedPitch = player.getXRot();
		} else {
			// restore player
			player.setPos(storedPos.x, storedPos.y, storedPos.z);
			player.setYRot(storedYaw);
			player.setXRot(storedPitch);
			player.setDeltaMovement(Vec3.ZERO);
		}
	}

	@SubscribeEvent
	public static void onClientTick(net.neoforged.neoforge.client.event.ClientTickEvent.Post event) {
		if (!enabled) return;

		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		if (player == null) return;

		// Freeze real player
		player.setDeltaMovement(Vec3.ZERO);

		// Camera movement
		Vec3 camMove = Vec3.ZERO;

		if (mc.options.keyUp.isDown()) camMove = camMove.add(0, SPEED, 0);
		if (mc.options.keyDown.isDown()) camMove = camMove.add(0, -SPEED, 0);
		if (mc.options.keyForward.isDown()) camMove = camMove.add(player.getLookAngle().scale(SPEED));
		if (mc.options.keyBack.isDown()) camMove = camMove.add(player.getLookAngle().scale(-SPEED));

		player.setPos(
				player.getX() + camMove.x,
				player.getY() + camMove.y,
				player.getZ() + camMove.z
		);
	}
}
