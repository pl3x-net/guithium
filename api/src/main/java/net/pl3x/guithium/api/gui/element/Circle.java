package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a gradient filled circle.
 */
public class Circle extends AbstractElement {
    private Float radius;
    private Integer resolution;
    private int innerColor;
    private int outerColor;

    /**
     * Creates a new gradient filled circle.
     *
     * @param key        Unique identifier for circle
     * @param pos        Position of circle
     * @param anchor     Anchor for circle
     * @param offset     Offset of circle
     * @param rotation   Rotation in degrees
     * @param scale      Scale of element
     * @param radius     Radius of circle
     * @param resolution Number of points around the circle
     * @param innerColor Color in the center of the circle
     * @param outerColor Color on the outer edges of the circle
     */
    protected Circle(@NotNull Key key, @Nullable Vec2 pos, @Nullable Vec2 anchor, @Nullable Vec2 offset, @Nullable Float rotation, @Nullable Float scale, @Nullable Float radius, @Nullable Integer resolution, int innerColor, int outerColor) {
        super(key, Type.CIRCLE, pos, anchor, offset, rotation, scale);
        setRadius(radius);
        setResolution(resolution);
        setInnerColor(innerColor);
        setOuterColor(outerColor);
    }

    /**
     * Get the radius of this circle.
     * <p>
     * If null, will default to half screen's width or height, whichever is smaller.
     *
     * @return The radius
     */
    @Nullable
    public Float getRadius() {
        return this.radius;
    }

    /**
     * Set the radius of this circle.
     * <p>
     * If null, will default to half screen's width or height, whichever is smaller.
     *
     * @param radius The radius
     */
    public void setRadius(@Nullable Float radius) {
        this.radius = radius;
    }

    /**
     * Get the resolution (number of outer points) for this circle.
     * <p>
     * If null, default of <code>{@link #getRadius()}</code> will be used.
     *
     * @return Circle resolution
     */
    @Nullable
    public Integer getResolution() {
        return this.resolution;
    }

    /**
     * Set the resolution (number of outer points) for this circle.
     * <p>
     * If null, default of <code>{@link #getRadius()}</code> will be used.
     *
     * @param resolution Circle resolution
     */
    public void setResolution(@Nullable Integer resolution) {
        this.resolution = resolution;
    }

    /**
     * Get the inner color of this circle.
     *
     * @return Inner color
     */
    public int getInnerColor() {
        return this.innerColor;
    }

    /**
     * Set the inner color of this circle.
     *
     * @param color Inner color
     */
    public void setInnerColor(int color) {
        this.innerColor = color;
    }

    /**
     * Get the outer color of this circle.
     *
     * @return Outer color
     */
    public int getOuterColor() {
        return this.outerColor;
    }

    /**
     * Set the outer color of this circle.
     *
     * @param color Outer color
     */
    public void setOuterColor(int color) {
        this.outerColor = color;
    }

    /**
     * Set the color of this circle.
     * <p>
     * Both the inner and outer colors will be set to the specified color.
     *
     * @param color Circle color
     */
    public void setColor(int color) {
        setInnerColor(color);
        setOuterColor(color);
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("radius", getRadius());
        json.addProperty("resolution", getResolution());
        json.addProperty("innerColor", getInnerColor());
        json.addProperty("outerColor", getOuterColor());
        return json.getJsonObject();
    }

    /**
     * Create a new circle from Json.
     *
     * @param json Json representation of a circle
     * @return A new circle
     */
    @NotNull
    public static Circle fromJson(JsonObject json) {
        return new Circle(
                Key.of(json.get("key").getAsString()),
                !json.has("pos") ? null : Vec2.fromJson(json.get("pos").getAsJsonObject()),
                !json.has("anchor") ? null : Vec2.fromJson(json.get("anchor").getAsJsonObject()),
                !json.has("offset") ? null : Vec2.fromJson(json.get("offset").getAsJsonObject()),
                !json.has("rotation") ? null : json.get("rotation").getAsFloat(),
                !json.has("scale") ? null : json.get("scale").getAsFloat(),
                !json.has("radius") ? null : json.get("radius").getAsFloat(),
                !json.has("resolution") ? null : json.get("resolution").getAsInt(),
                !json.has("innerColor") ? 0 : json.get("innerColor").getAsInt(),
                !json.has("outerColor") ? 0 : json.get("outerColor").getAsInt()
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
        Circle other = (Circle) o;
        return Objects.equals(getRadius(), other.getRadius())
                && Objects.equals(getResolution(), other.getResolution())
                && getInnerColor() == other.getInnerColor()
                && getOuterColor() == other.getOuterColor()
                && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRadius(), getResolution(), getInnerColor(), getOuterColor(), super.hashCode());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("Circle{%s}", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
                + ",radius=" + getRadius()
                + ",resolution=" + getResolution()
                + ",innerColor=" + getInnerColor()
                + ",outerColor=" + getOuterColor();
    }

    /**
     * Create a new circle builder.
     *
     * @param key Unique identifying key for the circle
     * @return New circle builder
     */
    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    /**
     * Create a new circle builder.
     *
     * @param key Unique identifying key for the circle
     * @return New circle builder
     */
    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    /**
     * Builder for circles.
     */
    public static class Builder extends AbstractBuilder<Builder> {
        private Float radius;
        private Integer resolution;
        private int innerColor;
        private int outerColor;

        /**
         * Create a new circle builder.
         *
         * @param key Unique identifying key for the circle
         */
        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        /**
         * Create a new circle builder.
         *
         * @param key Unique identifying key for the circle
         */
        public Builder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get the radius of this circle.
         * <p>
         * If null, will default to half screen's width or height, whichever is smaller.
         *
         * @return The radius
         */
        @Nullable
        public Float getRadius() {
            return this.radius;
        }

        /**
         * Set the radius of this circle.
         * <p>
         * If null, will default to half screen's width or height, whichever is smaller.
         *
         * @param radius The radius
         * @return This builder
         */
        @NotNull
        public Builder setRadius(@Nullable Float radius) {
            this.radius = radius;
            return this;
        }

        /**
         * Get the resolution (number of outer points) for this circle.
         * <p>
         * If null, default of <code>{@link #getRadius()}</code> will be used.
         *
         * @return Circle resolution
         */
        @Nullable
        public Integer getResolution() {
            return this.resolution;
        }

        /**
         * Set the resolution (number of outer points) for this circle.
         * <p>
         * If null, default of <code>{@link #getRadius()}</code> will be used.
         *
         * @param resolution Circle resolution
         * @return This builder
         */
        @NotNull
        public Builder setResolution(@Nullable Integer resolution) {
            this.resolution = resolution;
            return this;
        }

        /**
         * Get the inner color of this circle.
         *
         * @return Inner color
         */
        public int getInnerColor() {
            return this.innerColor;
        }

        /**
         * Set the inner color of this circle.
         *
         * @param color Inner color
         * @return This builder
         */
        @NotNull
        public Builder setInnerColor(int color) {
            this.innerColor = color;
            return this;
        }

        /**
         * Get the outer color of this circle.
         *
         * @return Outer color
         */
        public int getOuterColor() {
            return this.outerColor;
        }

        /**
         * Set the outer color of this circle.
         *
         * @param color Outer color
         * @return This builder
         */
        @NotNull
        public Builder setOuterColor(int color) {
            this.outerColor = color;
            return this;
        }

        /**
         * Set the color of this circle.
         * <p>
         * Both the inner and outer colors will be set to the specified color.
         *
         * @param color Circle color
         * @return This builder
         */
        @NotNull
        public Builder setColor(int color) {
            setInnerColor(color);
            setOuterColor(color);
            return this;
        }

        /**
         * Build a new circle from the current properties in this builder.
         *
         * @return New circle
         */
        @Override
        @NotNull
        public Circle build() {
            return new Circle(getKey(), getPos(), getAnchor(), getOffset(), getRotation(), getScale(), getRadius(), getResolution(), getInnerColor(), getOuterColor());
        }
    }
}
