package net.pl3x.guithium.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.fabric.gui.screen.TestScreen;
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
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("screen").executes(context -> {
                    Minecraft client = Minecraft.getInstance();
                    client.schedule(() -> client.setScreen(new TestScreen()));
                    context.getSource().sendFeedback(Component.literal("Called /screen"));
                    return 1;
                }))
        );
    }

    @Override
    @NotNull
    public Logger logger() {
        return this.logger;
    }
}
