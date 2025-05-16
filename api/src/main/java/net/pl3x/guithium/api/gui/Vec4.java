package net.pl3x.guithium.api.gui;

import net.pl3x.guithium.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a 4D vector.
 *
 * @param x X value
 * @param y Y value
 * @param z Z value
 * @param w W value
 */
public record Vec4(float x, float y, float z, float w) implements JsonSerializable {
    /**
     * Vec4 of 0,0,0,0
     */
    public static final Vec4 ZERO = Vec4.of(0, 0, 0, 0);

    /**
     * Vec4 of 1,1,1,1
     */
    public static final Vec4 ONE = Vec4.of(1, 1, 1, 1);

    /**
     * Create a new 4D vector.
     *
     * @param x X value
     * @param y Y value
     * @param z Z value
     * @param w W value
     * @return A new 4D vector
     */
    @NotNull
    public static Vec4 of(float x, float y, float z, float w) {
        return new Vec4(x, y, z, w);
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("%s%s", getClass().getSimpleName(), GSON.toJson(this));
    }
}
