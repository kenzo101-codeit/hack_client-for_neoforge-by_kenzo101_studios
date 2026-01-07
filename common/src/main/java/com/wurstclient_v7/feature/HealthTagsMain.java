package com.wurstclient_v7;

import com.wurstclient_v7.config.ConfigManager;
import com.wurstclient_v7.client.HealthTagClientCache;
import com.wurstclient_v7.net.HealthTagPayloads;
import com.wurstclient_v7.server.HealthTagBroadcaster;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(HealthTagsMain.MODID)
public final class HealthTagsMain {
	public static final String MODID = "wurst_client_on_neoforge";

	// Toggle state for the Click GUI
	private static boolean enabled = false;

	public HealthTagMain(IEventBus modEventBus) {
		// Register networking
		modEventBus.addListener(this::registerNetworking);

		// Register server broadcaster
		HealthTagBroadcaster.init();
	}

	private void registerNetworking(RegisterPayloadHandlersEvent event) {
		HealthTagPayloads.register(event, HealthTagClientCache.CLIENT_HANDLER);
	}

	// === Public API for Click GUI ===
	public static void toggle() {
		enabled = !enabled;
		ConfigManager.setBoolean("healthtags.enabled", enabled);
	}

	public static boolean isEnabled() {
		return enabled;
	}
}
