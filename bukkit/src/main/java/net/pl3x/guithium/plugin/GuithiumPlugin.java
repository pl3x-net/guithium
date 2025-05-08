package net.pl3x.guithium.plugin;

import net.pl3x.guithium.api.Guithium;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class GuithiumPlugin extends JavaPlugin implements Guithium {
    private static GuithiumPlugin instance;

    public static GuithiumPlugin getInstance() {
        return instance;
    }

    public GuithiumPlugin() {
        instance = this;

        try {
            Field api = Guithium.Provider.class.getDeclaredField("api");
            api.setAccessible(true);
            api.set(null, this);
        } catch (NoSuchFieldException | IllegalAccessException ignore) {
        }
    }
}
