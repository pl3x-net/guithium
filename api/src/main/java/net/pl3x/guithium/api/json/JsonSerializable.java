package net.pl3x.guithium.api.json;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an object that can be serialized to json.
 */
public interface JsonSerializable {
    /**
     * Jsonify this object.
     *
     * @return object as json element
     */
    @NotNull
    JsonElement toJson();
}
