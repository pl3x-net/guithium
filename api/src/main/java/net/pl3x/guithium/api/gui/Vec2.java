package net.pl3x.guithium.api.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a 2D vector.
 */
public class Vec2 implements JsonSerializable {
    public static final Vec2 ZERO = new Vec2(0, 0);
    public static final Vec2 ONE = new Vec2(1, 1);

    private final float x;
    private final float y;

    /**
     * Creates a new 2D vector.
     *
     * @param x X component
     * @param y Y component
     */
    protected Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new 2D vector.
     *
     * @param x X component
     * @param y Y component
     */
    @NotNull
    public static Vec2 of(float x, float y) {
        return new Vec2(x, y);
    }

    /**
     * Get the X component.
     *
     * @return The X component
     */
    public float getX() {
        return this.x;
    }

    /**
     * Get the Y component.
     *
     * @return The Y component
     */
    public float getY() {
        return this.y;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper();
        json.addProperty("x", getX());
        json.addProperty("y", getY());
        return json.getJsonObject();
    }

    /**
     * Create a new 2D vector from Json.
     *
     * @param json Json representation of a 2D vector
     * @return A new 2D vector
     */
    @NotNull
    public static Vec2 fromJson(@NotNull JsonObject json) {
        return new Vec2(
            !json.has("x") ? 0 : json.get("x").getAsFloat(),
            !json.has("y") ? 0 : json.get("y").getAsFloat()
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
        Vec2 other = (Vec2) o;
        return Float.compare(getX(), other.getX()) == 0
            && Float.compare(getY(), other.getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    @NotNull
    public String toString() {
        return "Vec2{x=" + getX() + ",y=" + getY() + "}";
    }
}
