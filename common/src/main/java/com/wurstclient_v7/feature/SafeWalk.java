package com.wurstclient_v7.feature;

public final class SafeWalk {
    private static boolean enabled = false;

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        System.out.println("SafeWalk: " + (enabled ? "ON" : "OFF"));
    }
}