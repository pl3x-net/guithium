package net.pl3x.guithium.api.gui;

import net.pl3x.guithium.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a size (x and y)
 *
 * @param x X value
 * @param y Y value
 */
public record Vec2(float x, float y) implements JsonSerializable {
    /**
     * Vec2 of 0,0
     */
    public static final Vec2 ZERO = Vec2.of(0, 0);

    /**
     * Vec2 of 1,1
     */
    public static final Vec2 ONE = Vec2.of(1, 1);

    /**
     * Create a new 2D vector.
     *
     * @param x X value
     * @param y Y value
     * @return A new 2D vector
     */
    @NotNull
    public static Vec2 of(float x, float y) {
        return new Vec2(x, y);
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("%s%s", getClass().getSimpleName(), GSON.toJson(this));
    }
}
