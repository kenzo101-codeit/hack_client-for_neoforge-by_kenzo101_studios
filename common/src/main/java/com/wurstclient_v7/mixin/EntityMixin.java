package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.SafeWalk;
import com.wurstclient_v7.feature.ESP;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityMixin {

    @Inject(method = "isSteppingCarefully", at = @At("HEAD"), cancellable = true)
    private void onIsSteppingCarefully(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player && SafeWalk.isEnabled()) {
            cir.setReturnValue(true);
        }
    }

    // Fix 2: In 1.20.4, 'isGlowing' is often mapped as 'isCurrentlyGlowing'
    // or requires a more specific target. Try this version:
    @Inject(method = "isCurrentlyGlowing", at = @At("HEAD"), cancellable = true)
    private void onIsGlowing(CallbackInfoReturnable<Boolean> cir) {
        if (ESP.isEnabled()) {
            // Check if 'this' is NOT the local player (so you don't glow yourself)
            if (!((Object) this instanceof Player)) {
                cir.setReturnValue(true);
            }
        }
    }
}