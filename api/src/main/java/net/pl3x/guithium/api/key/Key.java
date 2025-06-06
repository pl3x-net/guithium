package net.pl3x.guithium.api.key;

import com.google.common.base.Preconditions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Simple string wrapper used to identify objects.
 * <p>
 * In most cases keys should be unique, so prefixing keys with a plugin
 * name, for example {@code myplugin:screen-1}, would be good practice.
 */
public final class Key {
    private static final Pattern VALID_CHARS = Pattern.compile("[a-zA-Z0-9:/._-]+");

    private final String key;

    /**
     * Create a new key.
     * <p>
     * Valid key values {@code [a-zA-Z0-9:/._-]+}
     *
     * @param key Unique identifier
     */
    public Key(@NotNull String key) {
        Preconditions.checkNotNull(key, "Key cannot be null");
        if (!VALID_CHARS.matcher(key).matches()) {
            throw new IllegalArgumentException(String.format("Non [a-zA-Z0-9:/._-] character in key '%s'", key));
        }
        this.key = key;
    }

    /**
     * Create a new key.
     *
     * @param key Unique identifier
     * @return A new {@link Key}
     */
    @NotNull
    public static Key of(@NotNull String key) {
        return new Key(key);
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

    /**
     * A custom JSON (de)serializer for the GSON library specifically for Key objects.
     */
    public static class Adapter implements JsonSerializer<Key>, JsonDeserializer<Key> {
        /**
         * Create a new Key adapter for gson.
         */
        public Adapter() {
            // Empty constructor to pacify javadoc lint
        }

        @Override
        @NotNull
        public JsonElement serialize(@NotNull Key key, @NotNull Type type, @NotNull JsonSerializationContext context) {
            return new JsonPrimitive(key.toString());
        }

        @Override
        @Nullable
        public Key deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext context) throws JsonParseException {
            return Key.of(json.getAsString());
        }
    }
}
