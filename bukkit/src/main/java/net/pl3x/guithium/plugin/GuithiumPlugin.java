package net.pl3x.guithium.plugin;

import java.lang.reflect.Field;
import net.pl3x.guithium.api.Guithium;
import org.bukkit.plugin.java.JavaPlugin;

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
