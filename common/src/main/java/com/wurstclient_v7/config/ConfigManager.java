package com.wurstclient_v7.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import net.minecraft.client.Minecraft;

public final class ConfigManager {
    // Standard Minecraft config directory
    private static final File CONFIG_DIR = new File(Minecraft.getInstance().gameDirectory, "config");
    private static final File CONFIG_FILE = new File(CONFIG_DIR, "hack-client-config.properties");
    private static final Properties PROPS = new Properties();

    static {
        // Set your defaults here
        PROPS.setProperty("speed.multiplier", "1.5");
        PROPS.setProperty("speed.enabled", "false");
        PROPS.setProperty("spider.enabled", "false");
        PROPS.setProperty("tracers.enabled", "false");
        PROPS.setProperty("safewalk.enabled", "false");
        PROPS.setProperty("esp.enabled", "false");
        PROPS.setProperty("flight.enabled", "false");
        PROPS.setProperty("fullbright.enabled", "false");
        PROPS.setProperty("killaura.enabled", "false");
        PROPS.setProperty("jetpack.enabled", "false");
        PROPS.setProperty("nuker.enabled", "false");
        PROPS.setProperty("nofall.enabled", "false");
        PROPS.setProperty("andromeda.enabled", "false");
        PROPS.setProperty("xray.enabled", "false");
        PROPS.setProperty("autoattack.enabled", "false");
        PROPS.setProperty("godmode.enabled", "false");
        load();
    }

    private ConfigManager() { }

    public static void load() {
        if (!CONFIG_FILE.exists()) return;
        try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
            PROPS.load(in);
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
        }
    }

    public static void save() {
        if (!CONFIG_DIR.exists()) CONFIG_DIR.mkdirs();
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            PROPS.store(out, "Hack Client 1.21.1 Config");
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }

    // --- Helper Methods ---

    public static boolean getBoolean(String key, boolean defaultVal) {
        String s = PROPS.getProperty(key);
        return s != null ? Boolean.parseBoolean(s) : defaultVal;
    }

    public static void setBoolean(String key, boolean val) {
        PROPS.setProperty(key, Boolean.toString(val));
        save();
    }

    public static double getDouble(String key, double defaultVal) {
        String s = PROPS.getProperty(key);
        if (s == null) return defaultVal;
        try { return Double.parseDouble(s); } catch (NumberFormatException e) { return defaultVal; }
    }

    public static void setDouble(String key, double val) {
        PROPS.setProperty(key, Double.toString(val));
        save();
    }
}