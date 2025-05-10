package net.pl3x.guithium.api.key;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an object with a unique identifier.
 */
public abstract class Keyed {
    private final Key key;

    /**
     * Create a new key identified object.
     *
     * @param key Unique identifier
     */
    public Keyed(@NotNull Key key) {
        Preconditions.checkNotNull(key, "Key cannot be null");
        this.key = key;
    }

    /**
     * Get the identifying key for this object.
     *
     * @return Unique identifier
     */
    @NotNull
    public Key getKey() {
        return this.key;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Keyed other = (Keyed) obj;
        return getKey().equals(other.getKey());
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

    @Override
    @NotNull
    public String toString() {
        return "Keyed{"
                + "key=" + getKey()
                + "}";
    }
}
