package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.XRay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class XRayMixin {

    // Fix: Added BlockState as the first parameter (this refers to the state being checked)
    @Inject(method = "skipRendering", at = @At("HEAD"), cancellable = true)
    private void onSkipRendering(BlockState state, Direction face, CallbackInfoReturnable<Boolean> cir) {
        if (XRay.isEnabled()) {
            // Check if the block is an ore
            boolean isOre = state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.DEEPSLATE_DIAMOND_ORE)
                    || state.is(Blocks.IRON_ORE) || state.is(Blocks.GOLD_ORE)
                    || state.is(Blocks.ANCIENT_DEBRIS) || state.is(Blocks.COAL_ORE)
                    || state.is(Blocks.NETHER_QUARTZ_ORE) || state.is(Blocks.EMERALD_ORE);

            // If it's an ore, return false (don't skip rendering);
            // if it's NOT an ore, return true (skip rendering / make invisible)
            cir.setReturnValue(!isOre);
        }
    }

    @Inject(method = "getLightBlock", at = @At("HEAD"), cancellable = true)
    private void onGetLightBlock(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (XRay.isEnabled()) {
            cir.setReturnValue(0); // Makes blocks transparent to light
        }
    }
}