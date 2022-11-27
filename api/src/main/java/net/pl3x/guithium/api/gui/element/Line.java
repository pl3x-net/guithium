package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a gradient colored line.
 */
public class Line extends AbstractElement {
    private Vec2 endPos;
    private Vec2 endAnchor;
    private Float width;
    private int startColor;
    private int endColor;

    /**
     * Creates a new gradient colored line.
     *
     * @param key        Unique identifier for line
     * @param pos        Start position of line
     * @param anchor     Start anchor for line
     * @param endPos     End position of line
     * @param endAnchor  End anchor for line
     * @param rotation   Rotation in degrees
     * @param scale      Scale of element
     * @param width      Width of line
     * @param startColor Starting color of line
     * @param endColor   Ending color of line
     */
    protected Line(@NotNull Key key, @Nullable Vec2 pos, @Nullable Vec2 anchor, @Nullable Vec2 endPos, @Nullable Vec2 endAnchor, @Nullable Float rotation, @Nullable Float scale, @Nullable Float width, int startColor, int endColor) {
        super(key, Type.LINE, pos, anchor, null, rotation, scale);
        setEndPos(endPos);
        setEndAnchor(endAnchor);
        setWidth(width);
        setStartColor(startColor);
        setEndColor(endColor);
    }

    /**
     * Get this line's end position from the end anchor point.
     * <p>
     * If null, default end position <code>0,0</code> will be used.
     *
     * @return End position from end anchor
     */
    @Nullable
    public Vec2 getEndPos() {
        return this.endPos;
    }

    /**
     * Set this line's end position from the end anchor point.
     *
     * @param x X (horizontal) end position
     * @param y Y (vertical) end position
     */
    public void setEndPos(float x, float y) {
        setEndPos(Vec2.of(x, y));
    }

    /**
     * Set this line's end position from the end anchor point.
     * <p>
     * If null, default end position <code>0,0</code> will be used.
     *
     * @param pos End position
     */
    public void setEndPos(@Nullable Vec2 pos) {
        this.endPos = pos;
    }

    /**
     * Get this line's end anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     * <p>
     * If null, default end anchor <code>0,0</code> will be used.
     *
     * @return End anchor point
     */
    @Nullable
    public Vec2 getEndAnchor() {
        return this.endAnchor;
    }

    /**
     * Set this line's end anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @param x X (horizontal) end anchor
     * @param y Y (vertical) end anchor
     */
    public void setEndAnchor(float x, float y) {
        setEndAnchor(Vec2.of(x, y));
    }

    /**
     * Set this line's end anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     * <p>
     * If null, default end anchor <code>0,0</code> will be used.
     *
     * @param anchor End anchor point
     */
    public void setEndAnchor(@Nullable Vec2 anchor) {
        this.endAnchor = anchor;
    }

    /**
     * Get this line's width in scaled pixels.
     * <p>
     * If null, default width <code>1.0</code> will be used.
     *
     * @return Line width
     */
    @Nullable
    public Float getWidth() {
        return this.width;
    }

    /**
     * Set this line's width in scaled pixels.
     * <p>
     * If null, default width <code>1.0</code> will be used.
     *
     * @param width Line width
     */
    public void setWidth(@Nullable Float width) {
        this.width = width;
    }

    /**
     * Get the starting color for this line.
     *
     * @return Starting color
     */
    public int getStartColor() {
        return this.startColor;
    }

    /**
     * Set the starting color for this line.
     *
     * @param color Starting color
     */
    public void setStartColor(int color) {
        this.startColor = color;
    }

    /**
     * Get the ending color for this line.
     *
     * @return Ending color
     */
    public int getEndColor() {
        return this.endColor;
    }

    /**
     * Set the ending color for this line.
     *
     * @param color Ending color
     */
    public void setEndColor(int color) {
        this.endColor = color;
    }

    /**
     * Set the color for this line.
     * <p>
     * Both the start and end colors will be set to the specified color.
     *
     * @param color Line color
     */
    public void setColor(int color) {
        setStartColor(color);
        setEndColor(color);
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("endPos", getEndPos());
        json.addProperty("endAnchor", getEndAnchor());
        json.addProperty("width", getWidth());
        json.addProperty("startColor", getStartColor());
        json.addProperty("endColor", getEndColor());
        return json.getJsonObject();
    }

    /**
     * Create a new line from Json.
     *
     * @param json Json representation of a line
     * @return A new line
     */
    @NotNull
    public static Line fromJson(JsonObject json) {
        return new Line(
            Key.of(json.get("key").getAsString()),
            !json.has("pos") ? null : Vec2.fromJson(json.get("pos").getAsJsonObject()),
            !json.has("anchor") ? null : Vec2.fromJson(json.get("anchor").getAsJsonObject()),
            !json.has("endPos") ? null : Vec2.fromJson(json.get("endPos").getAsJsonObject()),
            !json.has("endAnchor") ? null : Vec2.fromJson(json.get("endAnchor").getAsJsonObject()),
            !json.has("rotation") ? null : json.get("rotation").getAsFloat(),
            !json.has("scale") ? null : json.get("scale").getAsFloat(),
            !json.has("width") ? null : json.get("width").getAsFloat(),
            !json.has("startColor") ? 0 : json.get("startColor").getAsInt(),
            !json.has("endColor") ? 0 : json.get("endColor").getAsInt()
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
        Line other = (Line) o;
        return Objects.equals(getEndPos(), other.getEndPos())
            && Objects.equals(getEndAnchor(), other.getEndAnchor())
            && Objects.equals(getWidth(), other.getWidth())
            && getStartColor() == other.getStartColor()
            && getEndColor() == other.getEndColor()
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEndPos(), getEndAnchor(), getWidth(), getStartColor(), getEndColor(), super.hashCode());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("Line{%s}", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",endPos=" + getEndPos()
            + ",endAnchor=" + getEndAnchor()
            + ",width=" + getWidth()
            + ",startColor=" + getStartColor()
            + ",endColor=" + getEndColor();
    }

    /**
     * Create a new line builder.
     *
     * @param key Unique identifying key for the line
     * @return New line builder
     */
    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    /**
     * Create a new line builder.
     *
     * @param key Unique identifying key for the line
     * @return New line builder
     */
    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    /**
     * Builder for lines.
     */
    public static class Builder extends AbstractBuilder<Builder> {
        private Vec2 endPos;
        private Vec2 endAnchor;
        private Float width;
        private int startColor;
        private int endColor;

        /**
         * Create a new line builder.
         *
         * @param key Unique identifying key for the line
         */
        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        /**
         * Create a new line builder.
         *
         * @param key Unique identifying key for the line
         */
        public Builder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get this line's end position from the end anchor point.
         * <p>
         * If null, default end position <code>0,0</code> will be used.
         *
         * @return End position from end anchor
         */
        @Nullable
        public Vec2 getEndPos() {
            return this.endPos;
        }

        /**
         * Set this line's end position from the end anchor point.
         *
         * @param x X (horizontal) end position
         * @param y Y (vertical) end position
         * @return This builder
         */
        @NotNull
        public Builder setEndPos(float x, float y) {
            setEndPos(Vec2.of(x, y));
            return this;
        }

        /**
         * Set this line's end position from the end anchor point.
         * <p>
         * If null, default end position <code>0,0</code> will be used.
         *
         * @param pos End position
         * @return This builder
         */
        @NotNull
        public Builder setEndPos(@Nullable Vec2 pos) {
            this.endPos = pos;
            return this;
        }

        /**
         * Get this line's end anchor point on the screen.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the parent's size.
         * <p>
         * If null, default end anchor <code>0,0</code> will be used.
         *
         * @return End anchor point
         */
        @Nullable
        public Vec2 getEndAnchor() {
            return this.endAnchor;
        }

        /**
         * Set this line's end anchor point on the screen.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the parent's size.
         *
         * @param x X (horizontal) end anchor
         * @param y Y (vertical) end anchor
         * @return This builder
         */
        @NotNull
        public Builder setEndAnchor(float x, float y) {
            setEndAnchor(Vec2.of(x, y));
            return this;
        }

        /**
         * Set this line's end anchor point on the screen.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the parent's size.
         * <p>
         * If null, default end anchor <code>0,0</code> will be used.
         *
         * @param anchor End anchor point
         * @return This builder
         */
        @NotNull
        public Builder setEndAnchor(@Nullable Vec2 anchor) {
            this.endAnchor = anchor;
            return this;
        }

        /**
         * Get this line's width in scaled pixels.
         * <p>
         * If null, default width <code>1.0</code> will be used.
         *
         * @return Line width
         */
        @Nullable
        public Float getWidth() {
            return this.width;
        }

        /**
         * Set this line's width in scaled pixels.
         * <p>
         * If null, default width <code>1.0</code> will be used.
         *
         * @param width Line width
         * @return This builder
         */
        @NotNull
        public Builder setWidth(@Nullable Float width) {
            this.width = width;
            return this;
        }

        /**
         * Get the starting color for this line.
         *
         * @return Starting color
         */
        public int getStartColor() {
            return this.startColor;
        }

        /**
         * Set the starting color for this line.
         *
         * @param color Starting color
         * @return This builder
         */
        @NotNull
        public Builder setStartColor(int color) {
            this.startColor = color;
            return this;
        }

        /**
         * Get the ending color for this line.
         *
         * @return Ending color
         */
        public int getEndColor() {
            return this.endColor;
        }

        /**
         * Set the ending color for this line.
         *
         * @param color Ending color
         * @return This builder
         */
        @NotNull
        public Builder setEndColor(int color) {
            this.endColor = color;
            return this;
        }

        /**
         * Set the color for this line.
         * <p>
         * Both the start and end colors will be set to the specified color.
         *
         * @param color Line color
         * @return This builder
         */
        @NotNull
        public Builder setColor(int color) {
            setStartColor(color);
            setEndColor(color);
            return this;
        }

        /**
         * Build a new line from the current properties in this builder.
         *
         * @return New line
         */
        @Override
        @NotNull
        public Line build() {
            return new Line(getKey(), getPos(), getAnchor(), getEndPos(), getEndAnchor(), getRotation(), getScale(), getWidth(), getStartColor(), getEndColor());
        }
    }
}
