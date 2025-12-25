package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class AndromedaBridge {
    private static volatile boolean enabled = false;

    public static boolean isEnabled() { return enabled; }
    public static void toggle() { enabled = !enabled; }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.gameMode == null) return;

        ItemStack heldItem = mc.player.getMainHandItem();
        if (!(heldItem.getItem() instanceof BlockItem)) return;

        // Position directly under player feet
        BlockPos belowPos = mc.player.blockPosition().below();

        // Andromeda specific: Also target the block in front of the belowPos
        // based on where the player is looking/moving.
        placeBlockAt(mc, belowPos);

        // To get that "Andromeda" high-speed look, we often place
        // a second block slightly in front
        BlockPos frontPos = belowPos.relative(mc.player.getDirection());
        placeBlockAt(mc, frontPos);
    }

    private static void placeBlockAt(Minecraft mc, BlockPos pos) {
        if (!mc.level.getBlockState(pos).isAir()) return;

        for (Direction dir : Direction.values()) {
            BlockPos neighbor = pos.relative(dir);
            if (mc.level.getBlockState(neighbor).isAir()) continue;

            // Calculate hit vector
            Vec3 hitVec = Vec3.atCenterOf(neighbor).add(Vec3.atLowerCornerOf(dir.getOpposite().getNormal()).scale(0.5));

            BlockHitResult hitResult = new BlockHitResult(hitVec, dir.getOpposite(), neighbor, false);

            // Send placement to server
            mc.gameMode.useItemOn(mc.player, InteractionHand.MAIN_HAND, hitResult);
            mc.player.swing(InteractionHand.MAIN_HAND);
            break;
        }
    }
}