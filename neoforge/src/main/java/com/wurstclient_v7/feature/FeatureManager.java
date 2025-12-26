package com.wurstclient_v7.feature;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class FeatureManager {
    private static final List<Listener> LISTENERS = new CopyOnWriteArrayList<>();
    private static volatile boolean enabled = false;

    public static boolean isEnabled() { return enabled; }

    public static void toggle() {
        enabled = !enabled;
        notifyListeners();
    }

    public static void registerListener(Listener l) { LISTENERS.add(l); }

    private static void notifyListeners() {
        for (Listener l : LISTENERS) l.onToggled(enabled);
    }

    public interface Listener { void onToggled(boolean enabled); }
}
