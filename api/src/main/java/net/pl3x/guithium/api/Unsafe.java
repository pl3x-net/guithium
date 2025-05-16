package net.pl3x.guithium.api;

import org.jetbrains.annotations.NotNull;

/**
 * Unsafe utils
 */
public abstract class Unsafe {
    private Unsafe() {
        // Empty constructor to pacify javadoc lint
    }

    /**
     * Cast object to T
     *
     * @param obj Object to cast
     * @param <T> Type to cast to
     * @return Object as T
     */
    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> T cast(@NotNull Object obj) {
        return (T) obj;
    }
}
