package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.SafeWalk;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class SafeWalkMixin {

    // In 1.21.1, the method name is usually 'isSteppingCarefully'
    // but if that fails, we target the logic that checks for sneaking/ledges
    @Inject(method = "isSteppingCarefully", at = @At("HEAD"), cancellable = true)
    private void onIsSteppingCarefully(CallbackInfoReturnable<Boolean> cir) {
        // We only want this to apply to the PLAYER, not every mob in the world!
        if ((Object) this instanceof Player) {
            if (SafeWalk.isEnabled()) {
                cir.setReturnValue(true);
            }
        }
    }
}