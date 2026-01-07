package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.NeoForgeConfigManager;
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
		NeoForgeConfigManager.setBoolean("freecam.enabled", enabled);
	}

	public static boolean isEnabled() {
		return enabled;
	}

	private static Vec3 storedPos;
	private static float storedYaw;
	private static float storedPitch;

	private static final double SPEED = 0.5;

	public static void disable() {
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		if (player == null) return;

		enabled = !enabled;

		if (enabled) {
			storedPos = player.position();
			storedYaw = player.getYRot();
			storedPitch = player.getXRot();
		} else {
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

		player.setDeltaMovement(Vec3.ZERO);

		Vec3 camMove = Vec3.ZERO;

		// Vertical movement
		if (mc.options.keyJump.isDown()) camMove = camMove.add(0, SPEED, 0);
		if (mc.options.keyShift.isDown()) camMove = camMove.add(0, -SPEED, 0);

		// Horizontal movement
		Vec3 look = player.getLookAngle();
		Vec3 horizontalForward = new Vec3(look.x, 0, look.z).normalize();
		Vec3 horizontalRight = horizontalForward.cross(new Vec3(0, 1, 0)).normalize();

		if (mc.options.keyUp.isDown()) camMove = camMove.add(horizontalForward.scale(SPEED));
		if (mc.options.keyDown.isDown()) camMove = camMove.add(horizontalForward.scale(-SPEED));
		if (mc.options.keyLeft.isDown()) camMove = camMove.add(horizontalRight.scale(-SPEED));
		if (mc.options.keyRight.isDown()) camMove = camMove.add(horizontalRight.scale(SPEED));

		player.setPos(
				player.getX() + camMove.x,
				player.getY() + camMove.y,
				player.getZ() + camMove.z
		);
	}
}