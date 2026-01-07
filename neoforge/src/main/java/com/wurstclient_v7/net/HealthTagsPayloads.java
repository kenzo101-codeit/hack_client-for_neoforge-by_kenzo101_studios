package com.wurstclient_v7.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class HealthTagsPayloads {
    public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath("wurst_client_on_neoofrge", "health_tag");

    public static final class HealthUpdate implements CustomPacketPayload {
        public static final Type<HealthUpdate> TYPE = new Type<>(CHANNEL);

        public final int entityId;
        public final float health;
        public final float maxHealth;

        public HealthUpdate(int entityId, float health, float maxHealth) {
            this.entityId = entityId;
            this.health = health;
            this.maxHealth = maxHealth;
        }

        public static final StreamCodec<FriendlyByteBuf, HealthUpdate> CODEC =
                StreamCodec.of(
                        (buf, msg) -> {
                            buf.writeVarInt(msg.entityId);
                            buf.writeFloat(msg.health);
                            buf.writeFloat(msg.maxHealth);
                        },
                        buf -> new HealthUpdate(buf.readVarInt(), buf.readFloat(), buf.readFloat())
                );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void register(RegisterPayloadHandlersEvent event, IPayloadHandler<HealthUpdate> clientHandler) {
        PayloadRegistrar registrar = event.registrar("wurst_client_on_neofprge");
        registrar.playToClient(HealthUpdate.TYPE, HealthUpdate.CODEC, clientHandler);
    }
}