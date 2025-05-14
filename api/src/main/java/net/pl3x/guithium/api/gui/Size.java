package net.pl3x.guithium.api.gui;

import com.google.gson.JsonSyntaxException;
import net.pl3x.guithium.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a size (width and height)
 *
 * @param width  Width
 * @param height Height
 */
public record Size(float width, float height) implements JsonSerializable {
    /**
     * Create a new size.
     *
     * @param width  Width
     * @param height Height
     * @return A new size
     */
    @NotNull
    public static Size of(float width, float height) {
        return new Size(width, height);
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("%s%s", getClass().getSimpleName(), toJson());
    }

    /**
     * This method deserializes the specified JSON string into a size object.
     *
     * @param json The string from which this size is to be deserialized
     * @return a size object from the string
     * @throws JsonSyntaxException      if json is not a valid representation for a size object
     * @throws IllegalArgumentException if json is {@code null} or empty
     */
    @Nullable
    public static Size fromJson(@NotNull String json) {
        return JsonSerializable.fromJson(json, Size.class);
    }
}
