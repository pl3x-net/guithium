package net.pl3x.guithium.api;

import net.pl3x.guithium.api.network.NetworkHandler;
import net.pl3x.guithium.api.player.PlayerManager;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Guithium API.
 */
public interface Guithium {
    /**
     * The identifier for this mod.
     */
    String MOD_ID = "guithium";

    /**
     * The protocol version.
     * <p>
     * This is used to ensure client and server can correctly talk to each other.
     */
    int PROTOCOL = 1;

    /**
     * bStats ID number for Guithium.
     */
    int BSTATS_ID = 25813;

    /**
     * Guithium's internal logger.
     * <p>
     * <u>For internal use</u>. Please use your own logger.
     */
    Logger logger = LoggerFactory.getLogger(StringUtils.capitalize(Guithium.MOD_ID));

    /**
     * Get the Guithium API instance.
     *
     * @return Instance of Guithium API
     */
    @NotNull
    static Guithium api() {
        return Provider.api();
    }

    /**
     * Get the network handler instance.
     *
     * @return Network handler
     */
    @NotNull
    NetworkHandler getNetworkHandler();

    /**
     * Get the player manager instance.
     *
     * @return Player manager
     */
    @NotNull
    PlayerManager getPlayerManager();

    /**
     * The Guithium instance provider.
     * <p>
     * <u>For internal use</u>.
     */
    final class Provider {
        static Guithium api;

        private Provider() {
            // Empty constructor to pacify javadoc lint
        }

        @NotNull
        private static Guithium api() {
            return Provider.api;
        }
    }
}
