package net.pl3x.servergui.plugin;

import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.texture.TextureManager;
import net.pl3x.servergui.plugin.network.NetworkManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class ServerGUIBukkit extends JavaPlugin implements ServerGUI {
    private static ServerGUIBukkit instance;

    public static ServerGUIBukkit instance() {
        return instance;
    }

    private final NetworkManager networkManager;
    private final TextureManager textureManager;

    public ServerGUIBukkit() {
        instance = this;

        this.networkManager = new NetworkManager(this);
        this.textureManager = new TextureManager();

        try {
            Field api = ServerGUI.Provider.class.getDeclaredField("api");
            api.setAccessible(true);
            api.set(null, this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void onEnable() {
        getNetworkManager().register();
    }

    @Override
    @NotNull
    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    @Override
    @NotNull
    public TextureManager getTextureManager() {
        return this.textureManager;
    }
}
