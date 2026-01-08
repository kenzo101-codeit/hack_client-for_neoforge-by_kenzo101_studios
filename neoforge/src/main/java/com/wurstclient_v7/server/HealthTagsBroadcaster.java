package com.wurstclient_v7.server;

import com.wurstclient_v7.net.HealthTagsPayloads;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = "wurst_client_on_neoforge")
public final class HealthTagsBroadcaster {

	@SubscribeEvent
	public static void onServerTick(ServerTickEvent.Post event) {
		var server = event.getServer();
		if (server == null) return;

		for (ServerLevel level : server.getAllLevels()) {
			for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, level.getWorldBorder().getCollisionShape().bounds())) {
				float health = living.getHealth();
				float max = living.getMaxHealth();
				int id = living.getId();

				HealthTagsPayloads.HealthUpdate msg = new HealthTagsPayloads.HealthUpdate(id, health, max);
				for (ServerPlayer player : level.players()) {
					// Send to players near the entity (optional: distance check)
					player.connection.send(msg);
				}
			}
		}
	}

	public static void init() {
		NeoForge.EVENT_BUS.register(HealthTagsBroadcaster.class);
	}
}