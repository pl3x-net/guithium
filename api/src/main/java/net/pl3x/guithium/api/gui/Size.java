package net.pl3x.guithium.api.gui;

import net.pl3x.guithium.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;

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
        return String.format("%s%s", getClass().getSimpleName(), GSON.toJson(this));
    }
}
