package net.pl3x.guithium.plugin;

import net.pl3x.guithium.api.Guithium;
import org.bukkit.plugin.java.JavaPlugin;

public class GuithiumPlugin extends JavaPlugin implements Guithium {
    public static GuithiumPlugin instance() {
        return (GuithiumPlugin) Guithium.api();
    }

    public GuithiumPlugin() {
        Guithium.Provider.set(this);
    }
}
