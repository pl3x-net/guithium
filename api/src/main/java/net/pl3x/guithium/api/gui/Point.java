package net.pl3x.guithium.api.gui;

import net.pl3x.guithium.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;

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
        return String.format("%s%s", getClass().getSimpleName(), GSON.toJson(this));
    }
}
