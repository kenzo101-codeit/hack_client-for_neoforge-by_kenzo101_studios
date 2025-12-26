package com.wurstclient_v7.mixin;

import com.wurstclient_v7.feature.SafeWalk;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.wurstclient_v7.feature.ESP;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "isSteppingCarefully", at = @At("HEAD"), cancellable = true)
    private void onIsSteppingCarefully(CallbackInfoReturnable<Boolean> cir) {
        // We cast 'this' to an Entity to check if it's the player
        if ((Object) this instanceof Player && SafeWalk.isEnabled()) {
            // Force the method to return 'true', which enables the "edge-stop" logic
            cir.setReturnValue(true);
        }
    }
}