package net.pl3x.guithium.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.pl3x.guithium.api.Guithium;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuithiumMod implements ClientModInitializer, Guithium {
    private final Logger logger;

    public static GuithiumMod instance() {
        return (GuithiumMod) Guithium.api();
    }

    public GuithiumMod() {
        this.logger = LoggerFactory.getLogger(Guithium.MOD_ID);
        Guithium.Provider.set(this);
    }

    @Override
    public void onInitializeClient() {
        //
    }

    @Override
    @NotNull
    public Logger logger() {
        return this.logger;
    }
}
