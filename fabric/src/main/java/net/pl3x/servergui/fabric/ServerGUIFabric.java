package net.pl3x.servergui.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.pl3x.servergui.fabric.gui.ScreenManager;
import net.pl3x.servergui.fabric.gui.texture.TextureManager;
import net.pl3x.servergui.fabric.network.NetworkManager;
import net.pl3x.servergui.fabric.scheduler.Scheduler;

public class ServerGUIFabric implements ClientModInitializer {
    public static Minecraft client;
    public static int screenWidth;
    public static int screenHeight;

    private static ServerGUIFabric instance;

    public static ServerGUIFabric instance() {
        return instance;
    }

    private final Scheduler scheduler;
    private final ScreenManager screenManager;
    private final TextureManager textureManager;

    public ServerGUIFabric() {
        instance = this;

        this.scheduler = new Scheduler();
        this.screenManager = new ScreenManager();
        this.textureManager = new TextureManager();
    }

    @Override
    public void onInitializeClient() {
        NetworkManager.register();

        this.scheduler.register();
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public ScreenManager getScreenManager() {
        return this.screenManager;
    }

    public TextureManager getTextureManager() {
        return this.textureManager;
    }
}
