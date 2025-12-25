package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.XRay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true)
    private static void onShouldRenderFace(BlockState state, BlockGetter level, BlockPos pos, Direction face, BlockPos otherPos, CallbackInfoReturnable<Boolean> cir) {
        if (XRay.isEnabled()) {
            Block block = state.getBlock();
            boolean isOre = block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE ||
                            block == Blocks.GOLD_ORE || block == Blocks.DEEPSLATE_GOLD_ORE ||
                            block == Blocks.IRON_ORE || block == Blocks.DEEPSLATE_IRON_ORE ||
                            block == Blocks.COAL_ORE || block == Blocks.DEEPSLATE_COAL_ORE ||
                            block == Blocks.EMERALD_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE ||
                            block == Blocks.LAPIS_ORE || block == Blocks.DEEPSLATE_LAPIS_ORE ||
                            block == Blocks.REDSTONE_ORE || block == Blocks.DEEPSLATE_REDSTONE_ORE ||
                            block == Blocks.NETHER_QUARTZ_ORE || block == Blocks.NETHER_GOLD_ORE ||
                            block == Blocks.ANCIENT_DEBRIS;
            
            if (isOre) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
        }
    }
}
