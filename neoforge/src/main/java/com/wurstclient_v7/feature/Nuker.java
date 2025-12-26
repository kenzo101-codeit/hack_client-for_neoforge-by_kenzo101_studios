package com.wurstclient_v7.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class Nuker {
    private static volatile boolean enabled = false;
    private static int range = 4;

    private Nuker() { }

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static void onClientTick() {
        if (!enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null || mc.level == null) return;

        BlockPos playerPos = mc.player.blockPosition();
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = playerPos.offset(x, y, z);
                    BlockState state = mc.level.getBlockState(pos);
                    if (!state.isAir() && state.getBlock() != Blocks.BEDROCK && state.getBlock() != Blocks.WATER && state.getBlock() != Blocks.LAVA) {
                        mc.gameMode.destroyBlock(pos);
                        // Only break one block per tick to avoid instant kick
                        return;
                    }
                }
            }
        }
    }
}
