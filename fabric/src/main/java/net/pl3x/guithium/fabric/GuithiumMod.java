package net.pl3x.guithium.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.pl3x.guithium.api.Guithium;

public class GuithiumMod implements ClientModInitializer, Guithium {
    public static GuithiumMod instance() {
        return (GuithiumMod) Guithium.api();
    }

    public GuithiumMod() {
        Guithium.Provider.set(this);
    }

    @Override
    public void onInitializeClient() {
    }
}
