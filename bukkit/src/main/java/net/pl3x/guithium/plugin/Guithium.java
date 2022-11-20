package net.pl3x.guithium.plugin;

import net.pl3x.guithium.api.gui.texture.TextureManager;
import net.pl3x.guithium.api.network.NetworkHandler;
import net.pl3x.guithium.api.player.PlayerManager;
import net.pl3x.guithium.plugin.listener.PlayerListener;
import net.pl3x.guithium.plugin.network.BukkitNetworkHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class Guithium extends JavaPlugin implements net.pl3x.guithium.api.Guithium {
    private static Guithium instance;

    @NotNull
    public static Guithium instance() {
        return instance;
    }

    private final NetworkHandler networkHandler;
    private final PlayerManager playerManager;
    private final TextureManager textureManager;

    public Guithium() {
        instance = this;

        this.networkHandler = new BukkitNetworkHandler(this);
        this.playerManager = new PlayerManager();
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

    @Override
    @NotNull
    public NetworkHandler getNetworkHandler() {
        return this.networkHandler;
    }

    @Override
    @NotNull
    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    @Override
    @NotNull
    public TextureManager getTextureManager() {
        return this.textureManager;
    }
}
