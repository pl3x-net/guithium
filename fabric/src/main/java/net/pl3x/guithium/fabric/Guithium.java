package net.pl3x.guithium.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.pl3x.guithium.api.player.PlayerManager;
import net.pl3x.guithium.fabric.gui.HudManager;
import net.pl3x.guithium.fabric.gui.texture.TextureManager;
import net.pl3x.guithium.fabric.net.NetworkHandler;
import net.pl3x.guithium.fabric.scheduler.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class Guithium implements ClientModInitializer, net.pl3x.guithium.api.Guithium {
    private static Guithium instance;

    public static Guithium instance() {
        return instance;
    }

    private final HudManager hudManager;
    private final NetworkHandler networkHandler;
    private final Scheduler scheduler;
    private final TextureManager textureManager;

    public Guithium() {
        instance = this;

        this.hudManager = new HudManager();
        this.networkHandler = new NetworkHandler(this);
        this.scheduler = new Scheduler();
        this.textureManager = new TextureManager();

        try {
            Field api = Guithium.Provider.class.getDeclaredField("api");
            api.setAccessible(true);
            api.set(null, this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitializeClient() {
        getNetworkHandler().register();

        this.scheduler.register();
    }

    @NotNull
    public HudManager getHudManager() {
        return this.hudManager;
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
    public TextureManager getTextureManager() {
        return this.textureManager;
    }
}
