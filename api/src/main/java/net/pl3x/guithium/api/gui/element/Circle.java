package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a circle element.
 */
public class Circle extends AbstractElement<Circle> {
    private Integer radius;
    private Integer resolution;
    private int innerColor;
    private int outerColor;

    /**
     * Create a new circle element.
     *
     * @param key Unique identifier
     */
    public Circle(@NotNull String key) {
        this(Key.of(key));
    }

    /**
     * Create a new circle element.
     *
     * @param key Unique identifier
     */
    public Circle(@NotNull Key key) {
        super(key);
    }

    /**
     * Create a new circle element.
     *
     * @param key Unique identifier
     * @return New circle element
     */
    @NotNull
    public static Circle of(@NotNull String key) {
        return of(Key.of(key));
    }

    /**
     * Create a new circle element.
     *
     * @param key Unique identifier
     * @return New circle element
     */
    @NotNull
    public static Circle of(@NotNull Key key) {
        return new Circle(key);
    }

    /**
     * Get the radius of this circle.
     * <p>
     * If null, will default to half screen's width or height, whichever is smaller.
     *
     * @return The radius
     */
    @Nullable
    public Integer getRadius() {
        return this.radius;
    }

    /**
     * Set the radius of this circle.
     * <p>
     * If null, will default to half screen's width or height, whichever is smaller.
     *
     * @param radius The radius
     * @return This circle
     */
    @NotNull
    public Circle setRadius(@Nullable Integer radius) {
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
     * @return This circle
     */
    @NotNull
    public Circle setResolution(@Nullable Integer resolution) {
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
     * @return This circle
     */
    @NotNull
    public Circle setInnerColor(int color) {
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
     * @return This circle
     */
    @NotNull
    public Circle setOuterColor(int color) {
        this.outerColor = color;
        return this;
    }

    /**
     * Set the color of this circle.
     * <p>
     * Both the inner and outer colors will be set to the specified color.
     *
     * @param color Circle color
     * @return This circle
     */
    @NotNull
    public Circle setColor(int color) {
        setInnerColor(color);
        setOuterColor(color);
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Circle other = (Circle) obj;
        return Objects.equals(getRadius(), other.getRadius())
                && Objects.equals(getResolution(), other.getResolution())
                && Objects.equals(getInnerColor(), other.getInnerColor())
                && Objects.equals(getOuterColor(), other.getOuterColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getRadius(),
                getResolution(),
                getInnerColor(),
                getOuterColor()
        );
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
    public static Circle fromJson(@NotNull JsonObject json) {
        Circle circle = new Circle(Key.of(json.get("key").getAsString()));
        AbstractElement.fromJson(circle, json);
        circle.setRadius(!json.has("radius") ? null : json.get("radius").getAsInt());
        circle.setResolution(!json.has("resolution") ? null : json.get("resolution").getAsInt());
        circle.setInnerColor(!json.has("innerColor") ? 0 : json.get("innerColor").getAsInt());
        circle.setOuterColor(!json.has("outerColor") ? 0 : json.get("outerColor").getAsInt());
        return circle;
    }
}
