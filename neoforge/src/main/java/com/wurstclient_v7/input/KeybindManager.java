package com.wurstclient_v7.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Objects;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;

/**
 * Simple keybind manager that persists binds to a properties file under ./config/
 */
public final class KeybindManager {
    private static final class Keybind {
        public final boolean isMouse;
        public final int key; // keycode or mousebutton
        public final int modifiers; // bitmask: 1=SHIFT,2=CTRL,4=ALT,8=SUPER

        public Keybind(boolean isMouse, int key, int modifiers) {
            this.isMouse = isMouse;
            this.key = key;
            this.modifiers = modifiers;
        }

        @Override
        public String toString() {
            return (isMouse ? "M" : "K") + ":" + modifiers + ":" + key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Keybind kb = (Keybind) o;
            return isMouse == kb.isMouse && key == kb.key && modifiers == kb.modifiers;
        }

        @Override
        public int hashCode() { return Objects.hash(isMouse, key, modifiers); }
    }
    private static final File CONFIG_DIR = new File("config");
    private static final File CONFIG_FILE = new File(CONFIG_DIR, "hackclient-binds.properties");
    private static final Map<String, String> BINDS = new ConcurrentHashMap<>();

    static {
        // defaults
        BINDS.put("kill_aura_toggle", new Keybind(false, GLFW.GLFW_KEY_K, 0).toString());
        // default menu key: Right Control (like Wurst client)
        BINDS.put("open_menu", new Keybind(false, GLFW.GLFW_KEY_RIGHT_CONTROL, 0).toString());
        // autoattack toggle has no default binding
        BINDS.put("autoattack_toggle", new Keybind(false, GLFW.GLFW_KEY_UNKNOWN, 0).toString());
        // other module toggles (no default binds)
        BINDS.put("speedhack_toggle", new Keybind(false, GLFW.GLFW_KEY_UNKNOWN, 0).toString());
        BINDS.put("mobvision_toggle", new Keybind(false, GLFW.GLFW_KEY_UNKNOWN, 0).toString());
        BINDS.put("fullbright_toggle", new Keybind(false, GLFW.GLFW_KEY_UNKNOWN, 0).toString());
        load();

    }

    private KeybindManager() { }

    public static Keybind getKeybind(String action) {
        String s = BINDS.get(action);
        if (s == null) return null;
        // legacy integer
        try {
            int v = Integer.parseInt(s);
            return new Keybind(false, v, 0);
        } catch (NumberFormatException ignored) { }
        // parse modern form: M:mods:key or K:mods:key
        try {
            String[] parts = s.split(":");
            boolean isMouse = "M".equals(parts[0]);
            int mods = Integer.parseInt(parts[1]);
            int key = Integer.parseInt(parts[2]);
            return new Keybind(isMouse, key, mods);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setKey(String action, int key, int modifiers, boolean isMouse) {
        BINDS.put(action, new Keybind(isMouse, key, modifiers).toString());
        save();
    }

    public static void clear(String action) {
        BINDS.remove(action);
        save();
    }

    public static String keyName(int key) {
        if (key == GLFW.GLFW_KEY_UNKNOWN) return "NONE";
        // Common modifiers may not have glfw names; map them for clarity
        switch (key) {
            case GLFW.GLFW_KEY_RIGHT_CONTROL: return "RCTRL";
            case GLFW.GLFW_KEY_LEFT_CONTROL: return "LCTRL";
            case GLFW.GLFW_KEY_RIGHT_SHIFT: return "RSHIFT";
            case GLFW.GLFW_KEY_LEFT_SHIFT: return "LSHIFT";
            case GLFW.GLFW_KEY_RIGHT_ALT: return "RALT";
            case GLFW.GLFW_KEY_LEFT_ALT: return "LALT";
            case GLFW.GLFW_KEY_RIGHT_SUPER: return "RSUPER";
            case GLFW.GLFW_KEY_LEFT_SUPER: return "LSUPER";
        }
        String name = GLFW.glfwGetKeyName(key, 0);
        if (name != null && !name.isEmpty()) return name.toUpperCase();
        // Fallback to numeric label
        return "KEY_" + key;
    }

    public static String keybindLabel(Keybind kb) {
        if (kb == null) return "NONE";
        StringBuilder sb = new StringBuilder();
        if ((kb.modifiers & 2) != 0) sb.append("CTRL+");
        if ((kb.modifiers & 1) != 0) sb.append("SHIFT+");
        if ((kb.modifiers & 4) != 0) sb.append("ALT+");
        if ((kb.modifiers & 8) != 0) sb.append("SUPER+");
        if (kb.isMouse) {
            switch (kb.key) {
                case GLFW.GLFW_MOUSE_BUTTON_LEFT: sb.append("MOUSE_L"); break;
                case GLFW.GLFW_MOUSE_BUTTON_RIGHT: sb.append("MOUSE_R"); break;
                case GLFW.GLFW_MOUSE_BUTTON_MIDDLE: sb.append("MOUSE_M"); break;
                default: sb.append("MOUSE_").append(kb.key);
            }
        } else {
            // friendly naming for modifiers
            switch (kb.key) {
                case GLFW.GLFW_KEY_RIGHT_CONTROL: sb.append("RCTRL"); break;
                case GLFW.GLFW_KEY_LEFT_CONTROL: sb.append("LCTRL"); break;
                case GLFW.GLFW_KEY_RIGHT_SHIFT: sb.append("RSHIFT"); break;
                case GLFW.GLFW_KEY_LEFT_SHIFT: sb.append("LSHIFT"); break;
                case GLFW.GLFW_KEY_RIGHT_ALT: sb.append("RALT"); break;
                case GLFW.GLFW_KEY_LEFT_ALT: sb.append("LALT"); break;
                case GLFW.GLFW_KEY_RIGHT_SUPER: sb.append("RSUPER"); break;
                case GLFW.GLFW_KEY_LEFT_SUPER: sb.append("LSUPER"); break;
                default: sb.append(keyName(kb.key));
            }
        }
        return sb.toString();
    }

    /**
     * Convenience: get display label for an action's bound key.
     */
    public static String getLabel(String action) {
        return keybindLabel(getKeybind(action));
    }

    public static boolean isPressed(long window, String action) {
        Keybind kb = getKeybind(action);
        if (kb == null) return false;
        // check modifiers
        if ((kb.modifiers & 2) != 0) { // CTRL
            boolean ctrl = InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_CONTROL) || InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_CONTROL);
            if (!ctrl) return false;
        }
        if ((kb.modifiers & 1) != 0) { // SHIFT
            boolean sh = InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT) || InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_SHIFT);
            if (!sh) return false;
        }
        if ((kb.modifiers & 4) != 0) { // ALT
            boolean alt = InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_ALT) || InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_ALT);
            if (!alt) return false;
        }
        // check primary input - prefer GLFW direct checks; compare with InputConstants for debugging
        boolean glfwDown;
        if (kb.isMouse) {
            glfwDown = GLFW.glfwGetMouseButton(window, kb.key) == GLFW.GLFW_PRESS;
        } else {
            glfwDown = GLFW.glfwGetKey(window, kb.key) == GLFW.GLFW_PRESS;
        }
        boolean icDown = InputConstants.isKeyDown(window, kb.key);
        if (glfwDown != icDown) {
            System.out.println("[KEYDEBUG] discrepancy for " + keybindLabel(kb) + ": InputConstants=" + icDown + " GLFW=" + glfwDown + " key=" + kb.key);
        }
        return glfwDown;
    }

    private static void load() {
        if (!CONFIG_FILE.exists()) return;
        Properties p = new Properties();
        try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
            p.load(in);
            for (String name : p.stringPropertyNames()) {
                BINDS.put(name, p.getProperty(name));
            }
        } catch (IOException e) {
            System.err.println("Failed to load keybinds: " + e.getMessage());
        }
    }

    private static void save() {
        CONFIG_DIR.mkdirs();
        Properties p = new Properties();
        for (Map.Entry<String,String> e : BINDS.entrySet()) p.setProperty(e.getKey(), e.getValue());
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            p.store(out, "Hack client keybinds");
        } catch (IOException e) {
            System.err.println("Failed to save keybinds: " + e.getMessage());
        }
    }
}
