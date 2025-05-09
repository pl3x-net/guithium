package net.pl3x.guithium.api;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

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
     * The Guithium instance provider.
     * <p>
     * For internal use.
     */
    final class Provider {
        static Guithium api;

        private Provider() {
            // Empty constructor to make javadoc lint happy
        }

        /**
         * Get the Guithium API instance.
         * <p>
         * <u>For internal use.</u>
         * <p>
         * Use {@link Guithium#api()} instead.
         *
         * @return Instance of Guithium API
         */
        @NotNull
        public static Guithium api() {
            return Provider.api;
        }

        /**
         * Set the Guithium API instance.
         * <p>
         * For internal use.
         *
         * @param guithium Instance of Guithium API
         */
        public static void set(Guithium guithium) {
            api = guithium;
        }
    }

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
     * Get Guithium's logger.
     * <p>
     * For internal use. Please use your own logger.
     *
     * @return Instance of Guithium's logger
     */
    @NotNull
    Logger logger();
}
