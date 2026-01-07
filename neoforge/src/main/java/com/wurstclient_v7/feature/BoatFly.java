package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public final class BoatFly {

	private static boolean enabled = false;

	// --- CONFIG VALUES (replace later with sliders) ---
	private static final double UPWARD_SPEED = 0.3;
	private static final double FORWARD_SPEED = 1.2;
	private static final boolean CHANGE_FORWARD_SPEED = true;

	// --------------------------------------------------

	public static void toggle() {
		enabled = !enabled;
		ConfigManager.setBoolean("boatfly.enabled", enabled);
	}

	public static boolean isEnabled() {
		return enabled;
	}

	// Call this from ClientTickEvent
	public static void onUpdate() {
		if (!enabled)
			return;

		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null || !mc.player.isPassenger())
			return;

		Entity vehicle = mc.player.getVehicle();
		if (vehicle == null)
			return;

		Vec3 velocity = vehicle.getDeltaMovement();

		double motionX = velocity.x;
		double motionY = velocity.y;
		double motionZ = velocity.z;

		// Up
		if (mc.options.keyJump.isDown()) {
			motionY = UPWARD_SPEED;
		}
		// Down (SPRINT, not sneak)
		else if (mc.options.keySprint.isDown()) {
			motionY = -UPWARD_SPEED;
		}

		// Forward
		if (mc.options.keyUp.isDown() && CHANGE_FORWARD_SPEED) {
			float yawRad = vehicle.getYRot() * Mth.DEG_TO_RAD;
			motionX = -Mth.sin(yawRad) * FORWARD_SPEED;
			motionZ =  Mth.cos(yawRad) * FORWARD_SPEED;
		}

		vehicle.setDeltaMovement(
				velocity.x + (motionX - velocity.x) * 0.3,
				velocity.y + (motionY - velocity.y) * 0.3,
				velocity.z + (motionZ - velocity.z) * 0.3
		);
	}
}