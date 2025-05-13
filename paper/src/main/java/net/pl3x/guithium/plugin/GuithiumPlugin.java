package net.pl3x.guithium.plugin;

import java.lang.reflect.Field;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.player.PlayerManager;
import net.pl3x.guithium.plugin.listener.PaperListener;
import net.pl3x.guithium.plugin.network.PaperNetworkHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class GuithiumPlugin extends JavaPlugin implements Guithium {
    private final PaperNetworkHandler networkHandler;
    private final PlayerManager playerManager;

    public static GuithiumPlugin instance() {
        return (GuithiumPlugin) Guithium.api();
    }

    public GuithiumPlugin() {
        this.networkHandler = new PaperNetworkHandler(this);
        this.playerManager = new PlayerManager();

        try {
            Field api = Guithium.Provider.class.getDeclaredField("api");
            api.setAccessible(true);
            api.set(null, this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("Failed to set Guithium API", e);
        }
    }

    public void onEnable() {
        getNetworkHandler().registerListeners();

        getServer().getPluginManager().registerEvents(new PaperListener(this), this);
    }

    @Override
    @NotNull
    public PaperNetworkHandler getNetworkHandler() {
        return this.networkHandler;
    }

    @Override
    @NotNull
    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }
}
