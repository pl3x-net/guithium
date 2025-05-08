package net.pl3x.guithium.api;

import org.jetbrains.annotations.NotNull;

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
     * The API instance provider
     * <br>
     * For internal use only
     */
    final class Provider {
        static Guithium api;

        private Provider() {
            // Empty constructor to make javadoc lint happy
        }

        /**
         * Get the instance to the API
         * <br>
         * For internal use only
         *
         * @return API instance
         */
        @NotNull
        public static Guithium api() {
            return Provider.api;
        }
    }

    /**
     * Get the instance to the API
     *
     * @return API instance
     */
    @NotNull
    static Guithium api() {
        return Provider.api();
    }
}
