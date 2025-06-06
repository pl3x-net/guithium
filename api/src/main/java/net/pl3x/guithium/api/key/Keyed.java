package net.pl3x.guithium.api.key;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an object with a unique identifier.
 */
public abstract class Keyed implements JsonSerializable {
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
        return String.format("%s%s", getClass().getSimpleName(), toJson());
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper();
        json.addProperty("key", getKey());
        json.addProperty("type", getClass().getSimpleName());
        return json.getJsonObject();
    }
}
