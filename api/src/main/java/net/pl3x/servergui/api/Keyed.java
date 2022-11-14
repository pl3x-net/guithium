package net.pl3x.servergui.api;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public abstract class Keyed {
    private final Key key;

    /**
     * Create a new key identified object.
     *
     * @param key key for object
     */
    public Keyed(@NotNull Key key) {
        Preconditions.checkNotNull(key, "Key cannot be null");
        this.key = key;
    }

    /**
     * Get the identifying key for this object.
     *
     * @return the key
     */
    @NotNull
    public Key getKey() {
        return this.key;
    }
}
