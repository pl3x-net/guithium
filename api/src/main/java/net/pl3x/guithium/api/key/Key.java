package net.pl3x.guithium.api.key;

import com.google.common.base.Preconditions;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Simple string wrapper used to identify objects
 * <p>
 * In most cases keys should be unique, so prefixing keys with a plugin
 * name, for example {@code myplugin:screen-1}, would be good practice
 */
public final class Key {
    private static final Pattern VALID_CHARS = Pattern.compile("[a-zA-Z0-9:/._-]+");

    private final String key;

    /**
     * Create a new {@link Key}
     *
     * @param key unique identifier
     */
    public Key(@NotNull String key) {
        Preconditions.checkNotNull(key, "Key cannot be null");
        if (!VALID_CHARS.matcher(key).matches()) {
            throw new IllegalArgumentException(String.format("Non [a-zA-Z0-9:/._-] character in key '%s'", key));
        }
        this.key = key;
    }

    /**
     * Create a new key
     *
     * @param key unique identifier
     * @return a new {@link Key}
     */
    @NotNull
    public static Key of(@NotNull String key) {
        return new Key(key);
    }

    /**
     * Indicates whether some other object is "equal to" this one
     *
     * @param obj the reference object with which to compare
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     * @see Key#equals(Object)
     */
    public boolean equals(@Nullable Keyed obj) {
        return equals(obj == null ? null : obj.getKey());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Key other = (Key) obj;
        return toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    @NotNull
    public String toString() {
        return this.key;
    }
}
