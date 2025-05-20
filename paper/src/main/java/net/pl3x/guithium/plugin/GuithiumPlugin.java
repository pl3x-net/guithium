package net.pl3x.guithium.plugin;

import java.lang.reflect.Field;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.action.ActionRegistry;
import net.pl3x.guithium.api.gui.texture.TextureManager;
import net.pl3x.guithium.plugin.listener.PaperListener;
import net.pl3x.guithium.plugin.network.PaperNetworkHandler;
import net.pl3x.guithium.plugin.player.PaperPlayerManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class GuithiumPlugin extends JavaPlugin implements Guithium {
    private final ActionRegistry actionRegistry = new ActionRegistry();
    private final PaperNetworkHandler networkHandler = new PaperNetworkHandler();
    private final PaperPlayerManager playerManager = new PaperPlayerManager();
    private final TextureManager textureManager = new TextureManager();

    private final String version;

    private Metrics metrics;

    public GuithiumPlugin() {
        String version = getClass().getPackage().getImplementationVersion();
        this.version = version == null ? "unknown" : version;

        try {
            Field api = Provider.class.getDeclaredField("api");
            api.setAccessible(true);
            api.set(null, this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("Failed to set Guithium API", e);
        }
    }

    public void onEnable() {
        getNetworkHandler().registerListeners();

        getServer().getPluginManager().registerEvents(new PaperListener(), this);

        this.metrics = new Metrics(this, BSTATS_ID);
    }

    public void onDisable() {
        if (this.metrics != null) {
            this.metrics.shutdown();
            this.metrics = null;
        }
    }

    @Override
    @NotNull
    public String getVersion() {
        return this.version;
    }

    @Override
    @NotNull
    public ActionRegistry getActionRegistry() {
        return this.actionRegistry;
    }

    @Override
    @NotNull
    public PaperNetworkHandler getNetworkHandler() {
        return this.networkHandler;
    }

    @Override
    @NotNull
    public PaperPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    @Override
    @NotNull
    public TextureManager getTextureManager() {
        return this.textureManager;
    }
}
