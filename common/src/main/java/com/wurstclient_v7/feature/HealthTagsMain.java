package com.wurstclient_v7;

import com.wurstclient_v7.config.NeoForgeConfigManager;
import com.wurstclient_v7.client.HealthTagClientCache;
import com.wurstclient_v7.net.HealthTagsPayloads;
import com.wurstclient_v7.server.HealthTagsBroadcaster;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(HealthTagsMain.MODID)
public final class HealthTagsMain {
	public static final String MODID = "wurst_client_on_neoforge";

	private static boolean enabled = false;

	public HealthTagsMain(IEventBus modEventBus) {
		modEventBus.addListener(this::registerNetworking);
		HealthTagsBroadcaster.init();
	}

	private void registerNetworking(RegisterPayloadHandlersEvent event) {
		HealthTagsPayloads.register(event, HealthTagClientCache.CLIENT_HANDLER);
	}

	public static void toggle() {
		enabled = !enabled;
		NeoForgeConfigManager.setBoolean("healthtags.enabled", enabled);
	}

	public static boolean isEnabled() {
		return enabled;
	}
}