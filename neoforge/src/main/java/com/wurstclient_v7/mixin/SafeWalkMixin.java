package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.SafeWalk; // <--- 1. IMPORT YOUR FEATURE
import net.minecraft.world.entity.Entity;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

    @Mixin(Entity.class)
    public abstract class SafeWalkMixin {

        @Inject(method = "isSteppingCarefully", at = @At("HEAD"), cancellable = true)
        private void onIsSteppingCarefully(CallbackInfoReturnable<Boolean> cir) {

            // 2. LINKING THE LOGIC:
            // We check the 'isEnabled()' method from your SafeWalk.java file
            if (SafeWalk.isEnabled()) {

                // 3. ONLY APPLY TO THE PLAYER:
                // This prevents every pig and zombie from being unable to fall off ledges
                if ((Object) this instanceof LocalPlayer) {
                    cir.setReturnValue(true); // Force the "Safe" state
                }
            }
        }
    }