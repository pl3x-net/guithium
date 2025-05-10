package net.pl3x.guithium.api.gui;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a size (width and height)
 *
 * @param width  Width
 * @param height Height
 */
public record Size(float width, float height) {
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
        return String.format("Size{width=%s,height=%s}", width(), height());
    }
}
