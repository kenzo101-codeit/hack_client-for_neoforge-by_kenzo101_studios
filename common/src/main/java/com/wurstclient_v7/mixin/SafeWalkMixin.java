package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.SafeWalk;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, remap = false)
public abstract class SafeWalkMixin {
    @Inject(method = "isSteppingCarefully", at = @At("HEAD"), cancellable = true)
    private void onIsSteppingCarefully(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player) {
            if (SafeWalk.isEnabled()) {
                cir.setReturnValue(true);
            }
        }
    }
}