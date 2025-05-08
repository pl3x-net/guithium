package net.pl3x.guithium.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.pl3x.guithium.api.Guithium;

import java.lang.reflect.Field;

public class GuithiumMod implements ClientModInitializer, Guithium {
    private static GuithiumMod instance;

    public static GuithiumMod getInstance() {
        return instance;
    }

    public GuithiumMod() {
        instance = this;

        try {
            Field api = Guithium.Provider.class.getDeclaredField("api");
            api.setAccessible(true);
            api.set(null, this);
        } catch (NoSuchFieldException | IllegalAccessException ignore) {
        }
    }

    @Override
    public void onInitializeClient() {
    }
}
