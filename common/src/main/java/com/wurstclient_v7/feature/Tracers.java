package com.wurstclient_v7.feature;

// This class is a placeholder in the common module.
// The actual implementation is in the neoforge module.
public final class Tracers {
    private static boolean enabled = false;
    public static boolean isEnabled() { return enabled; }
    public static void toggle() { 
        // The toggle logic is handled in the neoforge-specific implementation
        // by checking the keybinds. This placeholder allows other common code
        // to compile, but the real toggle happens in ClientTickMixin.
    }
}
