package net.pl3x.guithium.api;

import org.jetbrains.annotations.Nullable;

/**
 * Unsafe utils
 */
public abstract class Unsafe {
    private Unsafe() {
    }

    /**
     * Cast object to T
     *
     * @param obj Object to cast
     * @param <T> Type to cast to
     * @return Object as T
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T cast(@Nullable Object obj) {
        return (T) obj;
    }
}
