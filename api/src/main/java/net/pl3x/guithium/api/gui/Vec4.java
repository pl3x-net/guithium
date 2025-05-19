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
public record Vec4(int x, int y, int z, int w) implements JsonSerializable {
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
    public static Vec4 of(int x, int y, int z, int w) {
        return new Vec4(x, y, z, w);
    }

    /**
     * Get the X value.
     *
     * @return The X value
     */
    public int getX() {
        return x();
    }

    /**
     * Get the Y value.
     *
     * @return The Y value
     */
    public int getY() {
        return y();
    }

    /**
     * Get the Z value.
     *
     * @return The Z value
     */
    public int getZ() {
        return z();
    }

    /**
     * Get the W value.
     *
     * @return The W value
     */
    public int getW() {
        return w();
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("%s%s", getClass().getSimpleName(), GSON.toJson(this));
    }
}
