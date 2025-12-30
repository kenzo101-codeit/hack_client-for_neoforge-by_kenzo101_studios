package com.wurstclient_v7.feature;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public final class AndromedaBridge {
    private static boolean enabled = false;
    private static float startYaw;
    private static float startPitch;

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        Minecraft mc = Minecraft.getInstance();
        if (enabled && mc.player != null) {
            // Lock the angles when we start
            startYaw = mc.player.getYRot();
            startPitch = 80.0f; // Standard Andromeda pitch
        } else {
            stopInput(mc);
        }
    }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        // 1. Lock Rotation (Pitch 90 or close to it helps with specific reach)
        mc.player.setYRot(startYaw);
        mc.player.setXRot(startPitch);

        // 2. Movement Inputs
        KeyMapping.set(InputConstants.getKey(GLFW.GLFW_KEY_W, -1), true);
        KeyMapping.set(InputConstants.getKey(GLFW.GLFW_KEY_LEFT_CONTROL, -1), true);

        // 3. Jumping
        if (mc.player.onGround()) {
            mc.player.jumpFromGround();
        }

        // 4. Placement Logic
        BlockPos playerPos = mc.player.blockPosition();
        BlockPos underPlayer = playerPos.below();
        BlockPos aboveHead = playerPos.above(2);

        // Place Floor
        if (mc.level.getBlockState(underPlayer).isAir()) {
            placeBlock(mc, underPlayer, Direction.UP);
        }

        // Place Ceiling (The "Andromeda" part)
        // We check if it's air to avoid double-placing and wasting blocks
        if (mc.level.getBlockState(aboveHead).isAir()) {
            // We place against the BOTTOM face of the imaginary ceiling
            placeBlock(mc, aboveHead, Direction.DOWN);
        }
    }

    private static void placeBlock(Minecraft mc, BlockPos pos, Direction side) {
        // Determine the vector based on which side we are clicking
        Vec3 hitVec = new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

        // In a real Andromeda bridge, you're usually clicking the side of an existing block.
        // For this automation, we simulate clicking the target position directly.
        BlockHitResult hit = new BlockHitResult(hitVec, side, pos, false);

        mc.gameMode.useItemOn(mc.player, InteractionHand.MAIN_HAND, hit);
        mc.player.swing(InteractionHand.MAIN_HAND);
    }


    private static void stopInput(Minecraft mc) {
        if (mc.options == null) return;
        KeyMapping.set(InputConstants.getKey(GLFW.GLFW_KEY_W, -1), false);
        KeyMapping.set(InputConstants.getKey(GLFW.GLFW_KEY_LEFT_CONTROL, -1), false);
        KeyMapping.set(InputConstants.getKey(GLFW.GLFW_KEY_SPACE, -1), false);
    }
}