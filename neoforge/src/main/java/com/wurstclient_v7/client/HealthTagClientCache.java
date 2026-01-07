package com.wurstclient_v7.client;

import com.wurstclient_v7.net.HealthTagsPayloads;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public final class HealthTagClientCache {
    private static final Int2ObjectOpenHashMap<Entry> CACHE = new Int2ObjectOpenHashMap<>();

    public static final class Entry {
        public final float health;
        public final float max;
        public Entry(float health, float max) { this.health = health; this.max = max; }
    }

    public static final IPayloadHandler<HealthTagsPayloads.HealthUpdate> CLIENT_HANDLER = (msg, ctx) -> {
        // Run on client thread
        Minecraft.getInstance().execute(() -> {
            CACHE.put(msg.entityId, new Entry(msg.health, msg.maxHealth));
        });
    };

    public static Entry get(int entityId) {
        return CACHE.get(entityId);
    }
}
