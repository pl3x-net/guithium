package net.pl3x.guithium.plugin;

import net.pl3x.guithium.api.action.ActionRegistry;
import net.pl3x.guithium.api.gui.texture.TextureManager;
import net.pl3x.guithium.api.network.NetworkHandler;
import net.pl3x.guithium.api.player.PlayerManager;
import net.pl3x.guithium.plugin.listener.PlayerListener;
import net.pl3x.guithium.plugin.network.BukkitNetworkHandler;
import net.pl3x.guithium.plugin.player.BukkitPlayerManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class Guithium extends JavaPlugin implements net.pl3x.guithium.api.Guithium {
    private static Guithium instance;

    @NotNull
    public static Guithium instance() {
        return instance;
    }

    private final String version;

    private final ActionRegistry actionRegistry;
    private final NetworkHandler networkHandler;
    private final BukkitPlayerManager playerManager;
    private final TextureManager textureManager;

    public Guithium() {
        instance = this;

        String version = getClass().getPackage().getImplementationVersion();
        this.version = version == null ? "unknown" : version;

        this.actionRegistry = new ActionRegistry();
        this.networkHandler = new BukkitNetworkHandler(this);
        this.playerManager = new BukkitPlayerManager();
        this.textureManager = new TextureManager();

        try {
            Field api = Guithium.Provider.class.getDeclaredField("api");
            api.setAccessible(true);
            api.set(null, this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void onEnable() {
        this.networkHandler.register();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @NotNull
    public String getVersion() {
        return this.version;
    }

    @NotNull
    public ActionRegistry getActionRegistry() {
        return this.actionRegistry;
    }

    @Override
    @NotNull
    public NetworkHandler getNetworkHandler() {
        return this.networkHandler;
    }

    @Override
    @NotNull
    public BukkitPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    @Override
    @NotNull
    public TextureManager getTextureManager() {
        return this.textureManager;
    }
}
