package net.pl3x.servergui.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.pl3x.servergui.fabric.gui.element.RenderableElementManager;
import net.pl3x.servergui.fabric.gui.texture.TextureManager;
import net.pl3x.servergui.fabric.network.NetworkManager;
import net.pl3x.servergui.fabric.scheduler.Scheduler;

public class ServerGUIFabric implements ClientModInitializer {
    public static MinecraftClient client;
    public static int screenWidth;
    public static int screenHeight;

    private static ServerGUIFabric instance;

    private final RenderableElementManager renderableElementManager;
    private final Scheduler scheduler;
    private final TextureManager textureManager;

    public ServerGUIFabric() {
        instance = this;

        this.renderableElementManager = new RenderableElementManager();
        this.scheduler = new Scheduler();
        this.textureManager = new TextureManager();
    }

    public static ServerGUIFabric instance() {
        return instance;
    }

    @Override
    public void onInitializeClient() {
        NetworkManager.register();

        this.scheduler.register();
    }

    public RenderableElementManager getRenderableElementManager() {
        return this.renderableElementManager;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public TextureManager getTextureManager() {
        return this.textureManager;
    }
}
