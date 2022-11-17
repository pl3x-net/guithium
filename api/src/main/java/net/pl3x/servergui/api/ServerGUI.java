package net.pl3x.servergui.api;

import net.pl3x.servergui.api.gui.texture.TextureManager;
import net.pl3x.servergui.api.net.NetworkHandler;
import net.pl3x.servergui.api.player.PlayerManager;
import org.jetbrains.annotations.NotNull;

public interface ServerGUI {
    String MOD_ID = "servergui";
    int PROTOCOL = 1;

    final class Provider {
        static ServerGUI api;

        @NotNull
        public static ServerGUI api() {
            return Provider.api;
        }
    }

    @NotNull
    static ServerGUI api() {
        return Provider.api();
    }

    @NotNull
    NetworkHandler getNetworkHandler();

    @NotNull
    PlayerManager getPlayerManager();

    @NotNull
    TextureManager getTextureManager();
}
