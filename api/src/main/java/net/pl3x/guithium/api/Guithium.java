package net.pl3x.guithium.api;

import net.pl3x.guithium.api.action.ActionRegistry;
import net.pl3x.guithium.api.gui.texture.TextureManager;
import net.pl3x.guithium.api.network.NetworkHandler;
import net.pl3x.guithium.api.player.PlayerManager;
import org.jetbrains.annotations.NotNull;

public interface Guithium {
    String MOD_ID = "guithium";
    int PROTOCOL = 1;

    final class Provider {
        static Guithium api;

        @NotNull
        public static Guithium api() {
            return Provider.api;
        }
    }

    @NotNull
    static Guithium api() {
        return Provider.api();
    }

    @NotNull
    ActionRegistry getActionRegistry();

    @NotNull
    NetworkHandler getNetworkHandler();

    @NotNull
    PlayerManager getPlayerManager();

    @NotNull
    TextureManager getTextureManager();
}
