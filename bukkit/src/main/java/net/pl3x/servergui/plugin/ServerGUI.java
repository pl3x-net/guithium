package net.pl3x.servergui.plugin;

import net.pl3x.servergui.api.gui.texture.TextureManager;
import net.pl3x.servergui.api.player.PlayerManager;
import net.pl3x.servergui.plugin.listener.PlayerListener;
import net.pl3x.servergui.plugin.net.NetworkHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class ServerGUI extends JavaPlugin implements net.pl3x.servergui.api.ServerGUI {
    private static ServerGUI instance;

    public static ServerGUI instance() {
        return instance;
    }

    private final net.pl3x.servergui.api.net.NetworkHandler networkHandler;
    private final PlayerManager playerManager;
    private final TextureManager textureManager;

    public ServerGUI() {
        instance = this;

        this.networkHandler = new NetworkHandler(this);
        this.playerManager = new PlayerManager();
        this.textureManager = new TextureManager();

        try {
            Field api = net.pl3x.servergui.api.ServerGUI.Provider.class.getDeclaredField("api");
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
    public net.pl3x.servergui.api.net.NetworkHandler getNetworkHandler() {
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
