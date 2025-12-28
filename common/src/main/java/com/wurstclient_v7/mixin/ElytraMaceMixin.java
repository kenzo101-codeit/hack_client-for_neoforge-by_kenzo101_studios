package com.wurstclient_v7.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiPlayerGameMode.class)
public class ElytraMaceMixin {
    @Shadow @Final private ClientPacketListener connection;

    @Unique private boolean isSmashing = false;

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;ensureHasSentCarriedItem()V", shift = At.Shift.AFTER))
    public void beforeMaceAttack(Player player, Entity target, CallbackInfo ci) {
        // Check if the hack is actually turned ON first
        if (!com.wurstclient_v7.feature.ElytraMace.isEnabled()) return;

        if (player.getMainHandItem().is(Items.MACE) && player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST).is(Items.ELYTRA)) {
            if (player.isFallFlying() && player.fallDistance > 1.5F) {
                this.isSmashing = true;
                this.connection.send(new ServerboundPlayerCommandPacket(player, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
            }
        }
    }

    @Inject(method = "attack", at = @At("TAIL"))
    public void afterMaceAttack(Player player, Entity target, CallbackInfo ci) {
        if (this.isSmashing) {
            this.isSmashing = false;
            // Send it again to clean up the state
            this.connection.send(new ServerboundPlayerCommandPacket(player, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
        }
    }
}