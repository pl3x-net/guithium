package net.pl3x.guithium.api;

import net.pl3x.guithium.api.action.ActionRegistry;
import net.pl3x.guithium.api.gui.texture.TextureManager;
import net.pl3x.guithium.api.network.NetworkHandler;
import net.pl3x.guithium.api.player.PlayerManager;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the Guithium API.
 */
public interface Guithium {
    /**
     * Guithium's unique ID
     */
    String MOD_ID = "guithium";

    /**
     * Guithium's current protocol version
     */
    int PROTOCOL = 1;

    /**
     * Provides the Guithium API.
     */
    final class Provider {
        static Guithium api;

        /**
         * Get the Guithium API.
         *
         * @return Guithium API
         */
        @NotNull
        public static Guithium api() {
            return Provider.api;
        }
    }

    /**
     * Get the Guithium API.
     *
     * @return Guithium API
     */
    @NotNull
    static Guithium api() {
        return Provider.api();
    }

    /**
     * Get the action registry.
     *
     * @return The action registry
     */
    @NotNull
    ActionRegistry getActionRegistry();

    /**
     * Get the network handler
     *
     * @return The network handler
     */
    @NotNull
    NetworkHandler getNetworkHandler();

    /**
     * Get the player manager.
     *
     * @return The player manager
     */
    @NotNull
    PlayerManager getPlayerManager();

    /**
     * Get the texture manager.
     *
     * @return The texture manager
     */
    @NotNull
    TextureManager getTextureManager();
}
