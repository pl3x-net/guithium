package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.Objects;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.Size;
import net.pl3x.guithium.api.json.JsonSerializable;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a rectangle with up to four colors (one for each corner)
 */
public class Rect extends AbstractElement<Rect> {
    /**
     * The default color gradient Minecraft likes to use.
     */
    public static final Rect GRADIENT = Rect.builder(Guithium.MOD_ID + ":gradient")
            .setColorTop(0xC0101010)
            .setColorBottom(0xD0101010)
            .build();

    private Size size;
    private final int[] color = new int[]{0, 0, 0, 0};

    /**
     * Creates a new element.
     *
     * @param key    Unique identifier for element
     * @param pos    Position of element
     * @param anchor Anchor for element
     * @param offset Offset of element
     * @param size   Size of element
     * @param color  Corner colors of element
     */
    protected Rect(
            @NotNull Key key,
            @Nullable Point pos,
            @Nullable Point anchor,
            @Nullable Point offset,
            @Nullable Size size,
            int[] color
    ) {
        super(key, pos, anchor, offset);
        this.size = size;
        this.color[0] = color.length > 0 ? color[0] : 0;
        this.color[1] = color.length > 1 ? color[1] : 0;
        this.color[2] = color.length > 2 ? color[2] : 0;
        this.color[3] = color.length > 3 ? color[3] : 0;
    }

    /**
     * Get the size of this element.
     *
     * @return Size of element
     */
    @Nullable
    public Size getSize() {
        return this.size;
    }

    /**
     * Set the size of this element.
     *
     * @param width  Width to set
     * @param height Height to set
     * @return This rect
     */
    @NotNull
    public Rect setSize(float width, float height) {
        return setSize(Size.of(width, height));
    }

    /**
     * Set the size of this element.
     *
     * @param size Size to set
     * @return This rect
     */
    @NotNull
    public Rect setSize(@Nullable Size size) {
        this.size = size;
        return this;
    }

    /**
     * Get the colors for all four corners.
     * <p>
     * Array index starts at top left and iterates counterclockwise.
     *
     * @return Array of colors of all four corners
     */
    public int[] getColors() {
        return this.color;
    }

    /**
     * Get the top left color.
     *
     * @return Top left color
     */
    public int getColorTopLeft() {
        return this.color[0];
    }

    /**
     * Set the top left color.
     *
     * @param color Color to set
     * @return This rect
     */
    @NotNull
    public Rect setColorTopLeft(int color) {
        this.color[0] = color;
        return this;
    }

    /**
     * Get the bottom left color.
     *
     * @return Bottom left color
     */
    public int getColorBottomLeft() {
        return this.color[1];
    }

    /**
     * Set the bottom left color.
     *
     * @param color Color to set
     * @return This rect
     */
    @NotNull
    public Rect setColorBottomLeft(int color) {
        this.color[1] = color;
        return this;
    }

    /**
     * Get the bottom right color.
     *
     * @return Bottom right color
     */
    public int getColorBottomRight() {
        return this.color[2];
    }

    /**
     * Set the bottom right color.
     *
     * @param color Color to set
     * @return This rect
     */
    @NotNull
    public Rect setColorBottomRight(int color) {
        this.color[2] = color;
        return this;
    }

    /**
     * Get the top right color.
     *
     * @return Top right color
     */
    public int getColorTopRight() {
        return this.color[3];
    }

    /**
     * Set the top right color.
     *
     * @param color Color to set
     * @return This rect
     */
    @NotNull
    public Rect setColorTopRight(int color) {
        this.color[3] = color;
        return this;
    }

    /**
     * Set both top (left and right) colors.
     *
     * @param color Color to set
     * @return This rect
     */
    @NotNull
    public Rect setColorTop(int color) {
        setColorTopLeft(color);
        setColorTopRight(color);
        return this;
    }

    /**
     * Set both bottom (left and right) colors.
     *
     * @param color Color to set
     * @return This rect
     */
    @NotNull
    public Rect setColorBottom(int color) {
        setColorBottomLeft(color);
        setColorBottomRight(color);
        return this;
    }

    /**
     * Set both left (top and bottom) colors.
     *
     * @param color Color to set
     * @return This rect
     */
    @NotNull
    public Rect setColorLeft(int color) {
        setColorTopLeft(color);
        setColorBottomLeft(color);
        return this;
    }

    /**
     * Set both right (top and bottom) colors.
     *
     * @param color Color to set
     * @return This rect
     */
    @NotNull
    public Rect setColorRight(int color) {
        setColorTopRight(color);
        setColorBottomRight(color);
        return this;
    }

    /**
     * Set all colors (all four corners) to a single color.
     *
     * @param color Color to set
     * @return This rect
     */
    @NotNull
    public Rect setColorAll(int color) {
        setColorTop(color);
        setColorBottom(color);
        return this;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Rect other = (Rect) o;
        return Objects.equals(getSize(), other.getSize())
                && Arrays.equals(getColors(), other.getColors())
                && super.equals(o);
    }

    /**
     * This method deserializes the specified JSON string into a rect object.
     *
     * @param json The string from which this rect is to be deserialized
     * @return a rect object from the string
     * @throws JsonSyntaxException      if json is not a valid representation for a rect object
     * @throws IllegalArgumentException if json is {@code null} or empty
     */
    @Nullable
    public static Rect fromJson(@NotNull String json) {
        return JsonSerializable.fromJson(json, Rect.class);
    }

    /**
     * Create a new rect builder.
     *
     * @param key Unique identifying key for the rect
     * @return New rect builder
     */
    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    /**
     * Create a new rect builder.
     *
     * @param key Unique identifying key for the rect
     * @return New rect builder
     */
    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    /**
     * Builder for rects.
     */
    public static class Builder extends AbstractBuilder<Builder> {
        private Size size;
        private final int[] colors = new int[]{0, 0, 0, 0};

        /**
         * Create a new rect builder.
         *
         * @param key Unique identifying key for the rect
         */
        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        /**
         * Create a new rect builder.
         *
         * @param key Unique identifying key for the rect
         */
        public Builder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get the size of this element.
         *
         * @return Size of element
         */
        @Nullable
        public Size getSize() {
            return this.size;
        }

        /**
         * Set the size of this element.
         *
         * @param width  Width to set
         * @param height Height to set
         * @return This builder
         */
        @NotNull
        public Builder setSize(float width, float height) {
            return setSize(Size.of(width, height));
        }

        /**
         * Set the size of this element.
         *
         * @param size Size to set
         * @return This builder
         */
        @NotNull
        public Builder setSize(@Nullable Size size) {
            this.size = size;
            return this;
        }

        /**
         * Get the colors for all four corners.
         * <p>
         * Array index starts at top left and iterates counterclockwise.
         *
         * @return Array of colors of all four corners
         */
        public int[] getColors() {
            return this.colors;
        }

        /**
         * Get the top left color.
         *
         * @return Top left color
         */
        public int getColorTopLeft() {
            return this.colors[0];
        }

        /**
         * Set the top left color.
         *
         * @param color Color to set
         * @return This builder
         */
        @NotNull
        public Builder setColorTopLeft(int color) {
            this.colors[0] = color;
            return this;
        }

        /**
         * Get the bottom left color.
         *
         * @return Bottom left color
         */
        public int getColorBottomLeft() {
            return this.colors[1];
        }

        /**
         * Set the bottom left color.
         *
         * @param color Color to set
         * @return This builder
         */
        @NotNull
        public Builder setColorBottomLeft(int color) {
            this.colors[1] = color;
            return this;
        }

        /**
         * Get the bottom right color.
         *
         * @return Bottom right color
         */
        public int getColorBottomRight() {
            return this.colors[2];
        }

        /**
         * Set the bottom right color.
         *
         * @param color Color to set
         * @return This builder
         */
        @NotNull
        public Builder setColorBottomRight(int color) {
            this.colors[2] = color;
            return this;
        }

        /**
         * Get the top right color.
         *
         * @return Top right color
         */
        public int getColorTopRight() {
            return this.colors[3];
        }

        /**
         * Set the top right color.
         *
         * @param color Color to set
         * @return This builder
         */
        @NotNull
        public Builder setColorTopRight(int color) {
            this.colors[3] = color;
            return this;
        }

        /**
         * Set both top (left and right) colors.
         *
         * @param color Color to set
         * @return This builder
         */
        @NotNull
        public Builder setColorTop(int color) {
            setColorTopLeft(color);
            setColorTopRight(color);
            return this;
        }

        /**
         * Set both bottom (left and right) colors.
         *
         * @param color Color to set
         * @return This builder
         */
        @NotNull
        public Builder setColorBottom(int color) {
            setColorBottomLeft(color);
            setColorBottomRight(color);
            return this;
        }

        /**
         * Set both left (top and bottom) colors.
         *
         * @param color Color to set
         * @return This builder
         */
        @NotNull
        public Builder setColorLeft(int color) {
            setColorTopLeft(color);
            setColorBottomLeft(color);
            return this;
        }

        /**
         * Set both right (top and bottom) colors.
         *
         * @param color Color to set
         * @return This builder
         */
        @NotNull
        public Builder setColorRight(int color) {
            setColorTopRight(color);
            setColorBottomRight(color);
            return this;
        }

        /**
         * Set all colors (all four corners) to a single color.
         *
         * @param color Color to set
         * @return This builder
         */
        @NotNull
        public Builder setColorAll(int color) {
            setColorTop(color);
            setColorBottom(color);
            return this;
        }

        /**
         * Build a new rect from the current properties in this builder.
         *
         * @return New rect
         */
        @Override
        @NotNull
        public Rect build() {
            return new Rect(
                    getKey(),
                    getPos(),
                    getAnchor(),
                    getOffset(),
                    getSize(),
                    getColors()
            );
        }
    }
}
