package net.pl3x.servergui.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.pl3x.servergui.api.player.PlayerManager;
import net.pl3x.servergui.fabric.gui.ScreenManager;
import net.pl3x.servergui.fabric.gui.texture.TextureManager;
import net.pl3x.servergui.fabric.net.NetworkHandler;
import net.pl3x.servergui.fabric.scheduler.Scheduler;
import org.jetbrains.annotations.NotNull;

public class ServerGUI implements ClientModInitializer, net.pl3x.servergui.api.ServerGUI {
    public static Minecraft client;
    public static int screenWidth;
    public static int screenHeight;

    private static ServerGUI instance;

    public static ServerGUI instance() {
        return instance;
    }

    private final NetworkHandler networkHandler;
    private final Scheduler scheduler;
    private final ScreenManager screenManager;
    private final TextureManager textureManager;

    public ServerGUI() {
        instance = this;

        this.networkHandler = new NetworkHandler(this);
        this.scheduler = new Scheduler();
        this.screenManager = new ScreenManager();
        this.textureManager = new TextureManager();
    }

    @Override
    public void onInitializeClient() {
        getNetworkHandler().register();

        this.scheduler.register();
    }

    @Override
    @NotNull
    public NetworkHandler getNetworkHandler() {
        return this.networkHandler;
    }

    @Override
    @NotNull
    public PlayerManager getPlayerManager() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @NotNull
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @NotNull
    public ScreenManager getScreenManager() {
        return this.screenManager;
    }

    @NotNull
    public TextureManager getTextureManager() {
        return this.textureManager;
    }
}
