package net.pl3x.guithium.api;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * The Guithium API
 */
public interface Guithium {
    /**
     * The identifier for this mod
     */
    String MOD_ID = "guithium";

    /**
     * The protocol version
     * <br>
     * This is used to ensure client and server can correctly talk to each other
     */
    int PROTOCOL = 1;

    /**
     * The Guithium instance provider
     * <br>
     * For internal use
     */
    final class Provider {
        static Guithium api;

        private Provider() {
            // Empty constructor to make javadoc lint happy
        }

        /**
         * Get the Guithium API instance
         * <br>
         * For internal use
         *
         * @return instance of Guithium API
         */
        @NotNull
        public static Guithium api() {
            return Provider.api;
        }

        /**
         * Set the Guithium API instance
         * <br>
         * For internal use
         *
         * @param guithium instance of Guithium API
         */
        public static void set(Guithium guithium) {
            api = guithium;
        }
    }

    /**
     * Get the Guithium API instance
     *
     * @return instance of Guithium API
     */
    @NotNull
    static Guithium api() {
        return Provider.api();
    }

    /**
     * Get Guithium's logger
     *
     * @return logger instance
     */
    @NotNull
    Logger logger();
}
