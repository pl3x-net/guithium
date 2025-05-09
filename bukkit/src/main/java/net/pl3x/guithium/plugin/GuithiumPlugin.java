package net.pl3x.guithium.plugin;

import net.pl3x.guithium.api.Guithium;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuithiumPlugin extends JavaPlugin implements Guithium {
    private final Logger logger;

    public static GuithiumPlugin instance() {
        return (GuithiumPlugin) Guithium.api();
    }

    public GuithiumPlugin() {
        this.logger = LoggerFactory.getLogger(Guithium.MOD_ID);
        Guithium.Provider.set(this);
    }

    @Override
    @NotNull
    public Logger logger() {
        return this.logger;
    }
}
