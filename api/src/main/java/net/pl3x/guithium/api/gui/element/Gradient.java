package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Arrays;
import java.util.Objects;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a gradient filled rectangle.
 */
public class Gradient extends Rect {
    private final int[] colors = new int[]{0, 0, 0, 0};

    /**
     * Creates a new gradient filled rectangle.
     *
     * @param key              Unique identifier for rectangle
     * @param pos              Position of rectangle
     * @param anchor           Anchor for rectangle
     * @param offset           Offset of rectangle
     * @param rotation         Rotation in degrees
     * @param scale            Scale of element
     * @param size             Size of rectangle
     * @param colorTopLeft     Top left color
     * @param colorTopRight    Top right color
     * @param colorBottomLeft  Bottom left color
     * @param colorBottomRight Bottom right color
     */
    protected Gradient(@NotNull Key key, @Nullable Vec2 pos, @Nullable Vec2 anchor, @Nullable Vec2 offset, @Nullable Float rotation, @Nullable Float scale, @Nullable Vec2 size, int colorTopLeft, int colorTopRight, int colorBottomLeft, int colorBottomRight) {
        super(key, Type.GRADIENT, pos, anchor, offset, rotation, scale, size);
        setColorTopLeft(colorTopLeft);
        setColorTopRight(colorTopRight);
        setColorBottomLeft(colorBottomLeft);
        setColorBottomRight(colorBottomRight);
    }

    /**
     * Get the colors for all four corners.
     * <p>
     * Array indexes as follows:
     * <ul>
     * <li><code>[0]</code> - top left color</li>
     * <li><code>[1]</code> - top right color</li>
     * <li><code>[2]</code> - bottom left color</li>
     * <li><code>[3]</code> - bottom right color</li>
     * </ul>
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
     * @param color Top left color
     */
    public void setColorTopLeft(int color) {
        this.colors[0] = color;
    }

    /**
     * Get the top right color.
     *
     * @return Top right color
     */
    public int getColorTopRight() {
        return this.colors[1];
    }

    /**
     * Set the top right color.
     *
     * @param color Top right color
     */
    public void setColorTopRight(int color) {
        this.colors[1] = color;
    }

    /**
     * Get the bottom left color.
     *
     * @return Bottom left color
     */
    public int getColorBottomLeft() {
        return this.colors[2];
    }

    /**
     * Set the bottom left color.
     *
     * @param color Bottom left color
     */
    public void setColorBottomLeft(int color) {
        this.colors[2] = color;
    }

    /**
     * Get the bottom right color.
     *
     * @return Bottom right color
     */
    public int getColorBottomRight() {
        return this.colors[3];
    }

    /**
     * Set the bottom right color.
     *
     * @param color Bottom right color
     */
    public void setColorBottomRight(int color) {
        this.colors[3] = color;
    }

    /**
     * Set the top (left and right) colors.
     *
     * @param color Top colors
     */
    public void setColorTop(int color) {
        setColorTopLeft(color);
        setColorTopRight(color);
    }

    /**
     * Set the left (top and bottom) colors.
     *
     * @param color Left colors
     */
    public void setColorLeft(int color) {
        setColorTopLeft(color);
        setColorBottomLeft(color);
    }

    /**
     * Set the right (top and bottom) colors.
     *
     * @param color Right colors
     */
    public void setColorRight(int color) {
        setColorTopRight(color);
        setColorBottomRight(color);
    }

    /**
     * Set the bottom (left and right) colors.
     *
     * @param color Bottom colors
     */
    public void setColorBottom(int color) {
        setColorBottomLeft(color);
        setColorBottomRight(color);
    }

    /**
     * Set the colors of all four corners.
     *
     * @param color All corners colors
     */
    public void setColor(int color) {
        setColorTop(color);
        setColorBottom(color);
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("colors", getColors());
        return json.getJsonObject();
    }

    /**
     * Create a new gradient from Json.
     *
     * @param json Json representation of a gradient
     * @return A new gradient
     */
    @NotNull
    public static Gradient fromJson(JsonObject json) {
        if (!json.has("colors")) {
            throw new JsonParseException("Missing colors array");
        }
        JsonArray arr = json.get("colors").getAsJsonArray();
        if (arr.size() != 4) {
            throw new JsonParseException("Malformed colors array (size!=4)");
        }
        int[] colors = new int[4];
        for (int i = 0; i < 4; i++) {
            colors[i] = arr.get(i).getAsInt();
        }
        return new Gradient(
                Key.of(json.get("key").getAsString()),
                !json.has("pos") ? null : Vec2.fromJson(json.get("pos").getAsJsonObject()),
                !json.has("anchor") ? null : Vec2.fromJson(json.get("anchor").getAsJsonObject()),
                !json.has("offset") ? null : Vec2.fromJson(json.get("offset").getAsJsonObject()),
                !json.has("rotation") ? null : json.get("rotation").getAsFloat(),
                !json.has("scale") ? null : json.get("scale").getAsFloat(),
                !json.has("size") ? null : Vec2.fromJson(json.get("size").getAsJsonObject()),
                colors[0], colors[1], colors[2], colors[3]
        );
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
        Gradient other = (Gradient) o;
        return getColorTopLeft() == other.getColorTopLeft()
                && getColorTopRight() == other.getColorTopRight()
                && getColorBottomLeft() == other.getColorBottomLeft()
                && getColorBottomRight() == other.getColorBottomRight()
                && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColorTopLeft(), getColorTopRight(), getColorBottomLeft(), getColorBottomRight(), super.hashCode());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("Gradient{%s}", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
                + ",colors=" + Arrays.toString(getColors());
    }

    /**
     * Create a new gradient builder.
     *
     * @param key Unique identifying key for the gradient
     * @return New gradient builder
     */
    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    /**
     * Create a new gradient builder.
     *
     * @param key Unique identifying key for the gradient
     * @return New gradient builder
     */
    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    /**
     * Builder for gradients.
     */
    public static class Builder extends Rect.Builder<Builder> {
        private final int[] colors = new int[]{0, 0, 0, 0};

        /**
         * Create a new gradient builder.
         *
         * @param key Unique identifying key for the gradient
         */
        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        /**
         * Create a new gradient builder.
         *
         * @param key Unique identifying key for the gradient
         */
        public Builder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get the colors for all four corners.
         * <p>
         * Array index as follows:
         * <ul>
         * <li>top left</li>
         * <li>top right</li>
         * <li>bottom left</li>
         * <li>bottom right</li>
         * </ul>
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
         * @param color Top left color
         * @return This builder
         */
        @NotNull
        public Builder setColorTopLeft(int color) {
            this.colors[0] = color;
            return this;
        }

        /**
         * Get the top right color.
         *
         * @return Top right color
         */
        public int getColorTopRight() {
            return this.colors[1];
        }

        /**
         * Set the top right color.
         *
         * @param color Top right color
         * @return This builder
         */
        @NotNull
        public Builder setColorTopRight(int color) {
            this.colors[1] = color;
            return this;
        }

        /**
         * Get the bottom left color.
         *
         * @return Bottom left color
         */
        public int getColorBottomLeft() {
            return this.colors[2];
        }

        /**
         * Set the bottom left color.
         *
         * @param color Bottom left color
         * @return This builder
         */
        @NotNull
        public Builder setColorBottomLeft(int color) {
            this.colors[2] = color;
            return this;
        }

        /**
         * Get the bottom right color.
         *
         * @return Bottom right color
         */
        public int getColorBottomRight() {
            return this.colors[3];
        }

        /**
         * Set the bottom right color.
         *
         * @param color Bottom right color
         * @return This builder
         */
        @NotNull
        public Builder setColorBottomRight(int color) {
            this.colors[3] = color;
            return this;
        }

        /**
         * Set the top (left and right) colors.
         *
         * @param color Top colors
         * @return This builder
         */
        @NotNull
        public Builder setColorTop(int color) {
            setColorTopLeft(color);
            setColorTopRight(color);
            return this;
        }

        /**
         * Set the left (top and bottom) colors.
         *
         * @param color Left colors
         * @return This builder
         */
        @NotNull
        public Builder setColorLeft(int color) {
            setColorTopLeft(color);
            setColorBottomLeft(color);
            return this;
        }

        /**
         * Set the right (top and bottom) colors.
         *
         * @param color Right colors
         * @return This builder
         */
        @NotNull
        public Builder setColorRight(int color) {
            setColorTopRight(color);
            setColorBottomRight(color);
            return this;
        }

        /**
         * Set the bottom (left and right) colors.
         *
         * @param color Bottom colors
         * @return This builder
         */
        @NotNull
        public Builder setColorBottom(int color) {
            setColorBottomLeft(color);
            setColorBottomRight(color);
            return this;
        }

        /**
         * Set the colors of all four corners.
         *
         * @param color All corners colors
         * @return This builder
         */
        @NotNull
        public Builder setColor(int color) {
            setColorTop(color);
            setColorBottom(color);
            return this;
        }

        /**
         * Build a new gradient from the current properties in this builder.
         *
         * @return New gradient
         */
        @Override
        @NotNull
        public Gradient build() {
            return new Gradient(getKey(), getPos(), getAnchor(), getOffset(), getRotation(), getScale(), getSize(), getColorTopLeft(), getColorTopRight(), getColorBottomLeft(), getColorBottomRight());
        }
    }
}
