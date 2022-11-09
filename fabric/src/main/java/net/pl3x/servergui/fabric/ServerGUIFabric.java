package net.pl3x.servergui.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.pl3x.servergui.api.gui.GuiManager;
import net.pl3x.servergui.api.json.Gson;
import net.pl3x.servergui.fabric.gui.hud.HudManager;
import net.pl3x.servergui.fabric.gui.texture.TextureManager;
import net.pl3x.servergui.fabric.json.ElementAdapter;
import net.pl3x.servergui.fabric.json.GuiAdapter;
import net.pl3x.servergui.fabric.network.NetworkManager;
import net.pl3x.servergui.fabric.scheduler.Scheduler;

public class ServerGUIFabric implements ClientModInitializer {
    public static final String MOD_ID = "servergui";

    public static MinecraftClient client;
    public static int screenWidth;
    public static int screenHeight;

    private static ServerGUIFabric instance;

    private final Gson gson;
    private final GuiManager guiManager;
    private final HudManager hudManager;
    private final Scheduler scheduler;
    private final TextureManager textureManager;

    public ServerGUIFabric() {
        instance = this;

        this.gson = new Gson(new GuiAdapter(), new ElementAdapter());
        this.guiManager = new GuiManager();
        this.hudManager = new HudManager(this);
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

    public Gson gson() {
        return this.gson;
    }

    public GuiManager getGuiManager() {
        return this.guiManager;
    }

    public HudManager getHudManager() {
        return this.hudManager;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public TextureManager getTextureManager() {
        return this.textureManager;
    }
}
