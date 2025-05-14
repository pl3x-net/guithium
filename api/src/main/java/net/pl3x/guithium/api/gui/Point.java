package net.pl3x.guithium.api.gui;

import com.google.gson.JsonSyntaxException;
import net.pl3x.guithium.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a point position.
 *
 * @param x X position
 * @param y Y position
 */
public record Point(float x, float y) implements JsonSerializable {
    public static final Point ZERO = new Point(0, 0);

    /**
     * Create a new point.
     *
     * @param x X position
     * @param y Y position
     * @return A new point
     */
    @NotNull
    public static Point of(float x, float y) {
        return new Point(x, y);
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("%s%s", getClass().getSimpleName(), toJson());
    }

    /**
     * This method deserializes the specified JSON string into a point object.
     *
     * @param json The string from which this point is to be deserialized
     * @return a point object from the string
     * @throws JsonSyntaxException      if json is not a valid representation for a point object
     * @throws IllegalArgumentException if json is {@code null} or empty
     */
    @Nullable
    public static Point fromJson(@NotNull String json) {
        return JsonSerializable.fromJson(json, Point.class);
    }
}
