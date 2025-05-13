package net.pl3x.guithium.fabric;

import java.lang.reflect.Field;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import net.pl3x.guithium.api.player.PlayerManager;
import net.pl3x.guithium.fabric.network.FabricNetworkHandler;
import net.pl3x.guithium.fabric.scheduler.Scheduler;
import org.jetbrains.annotations.NotNull;

public class GuithiumMod implements ClientModInitializer, Guithium {
    private final FabricNetworkHandler networkHandler;
    private final Scheduler scheduler;

    public GuithiumMod() {
        this.networkHandler = new FabricNetworkHandler();
        this.scheduler = new Scheduler();

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
                // send hello on first client tick to ensure everything is ready to receive a reply
                getScheduler().addTask(() -> getNetworkHandler().getConnection().send(new HelloPacket()));
            }
        });
    }

    @Override
    public @NotNull FabricNetworkHandler getNetworkHandler() {
        return this.networkHandler;
    }

    @Override
    public @NotNull PlayerManager getPlayerManager() {
        throw new UnsupportedOperationException("Not supported on client mod.");
    }

    @NotNull
    public Scheduler getScheduler() {
        return this.scheduler;
    }
}
