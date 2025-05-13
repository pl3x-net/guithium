package net.pl3x.guithium.fabric;

import java.lang.reflect.Field;
import net.fabricmc.api.ClientModInitializer;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.player.PlayerManager;
import net.pl3x.guithium.fabric.network.FabricNetworkHandler;
import net.pl3x.guithium.fabric.scheduler.Scheduler;
import org.jetbrains.annotations.NotNull;

public class GuithiumMod implements ClientModInitializer, Guithium {
    public static GuithiumMod instance() {
        return (GuithiumMod) Guithium.api();
    }

    private final FabricNetworkHandler networkHandler;
    private final Scheduler scheduler;

    public GuithiumMod() {
        this.networkHandler = new FabricNetworkHandler(this);
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
    }

    @Override
    public @NotNull FabricNetworkHandler getNetworkHandler() {
        return this.networkHandler;
    }

    @Override
    public @NotNull PlayerManager getPlayerManager() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @NotNull
    public Scheduler getScheduler() {
        return this.scheduler;
    }
}
