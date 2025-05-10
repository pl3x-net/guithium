package net.pl3x.guithium.api;

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
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
