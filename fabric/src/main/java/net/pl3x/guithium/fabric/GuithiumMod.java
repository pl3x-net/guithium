package net.pl3x.guithium.fabric;

import java.lang.reflect.Field;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.action.ActionRegistry;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import net.pl3x.guithium.api.player.PlayerManager;
import net.pl3x.guithium.fabric.gui.HudManager;
import net.pl3x.guithium.fabric.gui.texture.FabricTextureManager;
import net.pl3x.guithium.fabric.network.FabricNetworkHandler;
import net.pl3x.guithium.fabric.scheduler.Scheduler;
import org.jetbrains.annotations.NotNull;

public class GuithiumMod implements ClientModInitializer, Guithium {
    private final FabricNetworkHandler networkHandler = new FabricNetworkHandler(this);
    private final FabricTextureManager textureManager = new FabricTextureManager();

    private final HudManager hudManager = new HudManager();
    private final Scheduler scheduler = new Scheduler();

    private final String version;

    public GuithiumMod() {
        String version = getClass().getPackage().getImplementationVersion();
        this.version = version == null ? "unknown" : version;

        try {
            Field api = Guithium.Provider.class.getDeclaredField("api");
            api.setAccessible(true);
            api.set(null, this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("Failed to set Guithium API", e);
        }
    }

    @Override
    public void onInitializeClient() {
        getNetworkHandler().registerListeners();

        getScheduler().register();

        // tell server hello when joining so it knows we have guithium installed
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            // ensure we are not connecting to a single player game
            if (!client.isLocalServer()) {
                // wait for first client tick to ensure everything is ready to receive a reply
                getScheduler().addTask(() -> getNetworkHandler().getConnection().send(new HelloPacket()));
            }
        });

        // clean everything up when disconnecting from server
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            getScheduler().cancelAll();
            getTextureManager().clear();
            getHudManager().clear();
        });
    }

    @Override
    @NotNull
    public String getVersion() {
        return this.version;
    }

    @Override
    @NotNull
    public ActionRegistry getActionRegistry() {
        throw new UnsupportedOperationException("Not supported on client.");
    }

    @Override
    @NotNull
    public FabricNetworkHandler getNetworkHandler() {
        return this.networkHandler;
    }

    @Override
    @NotNull
    public PlayerManager getPlayerManager() {
        throw new UnsupportedOperationException("Not supported on client.");
    }

    @Override
    @NotNull
    public FabricTextureManager getTextureManager() {
        return this.textureManager;
    }

    @NotNull
    public HudManager getHudManager() {
        return this.hudManager;
    }

    @NotNull
    public Scheduler getScheduler() {
        return this.scheduler;
    }
}
