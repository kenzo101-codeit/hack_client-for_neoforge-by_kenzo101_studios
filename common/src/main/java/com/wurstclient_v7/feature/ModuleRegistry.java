package com.wurstclient_v7.feature;

import com.wurstclient_v7.config.ConfigManager;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ModuleRegistry {
	public static final Map<String, ModuleToggle> MODULES = new LinkedHashMap<>();

	static {
		MODULES.put("KillAura", KillAura::isEnabled);
		MODULES.put("AutoAttack", AutoAttack::isEnabled);
		MODULES.put("BoatFly",BoatFly::isEnabled);
		MODULES.put("SpeedHack", SpeedHack::isEnabled);
		MODULES.put("FullBright", FullBright::isEnabled);
		MODULES.put("Flight", Flight::isEnabled);
		MODULES.put("NoFall", NoFall::isEnabled);
		MODULES.put("XRay", XRay::isEnabled);
		MODULES.put("Jetpack", Jetpack::isEnabled);
		MODULES.put("Nuker", Nuker::isEnabled);
		MODULES.put("Spider", Spider::isEnabled);
		MODULES.put("ESP", ESP::isEnabled);
		MODULES.put("Tracers", Tracers::isEnabled);
		MODULES.put("AndromedaBridge", AndromedaBridge::isEnabled);
		MODULES.put("SafeWalk", SafeWalk::isEnabled);
		MODULES.put("LSD", LsdHack::isEnabled);
	}

	public interface ModuleToggle {
		boolean isEnabled();
	}
}
