package net.pl3x.guithium.api.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a 4D vector.
 */
public class Vec4 implements JsonSerializable {
    private final float x0;
    private final float y0;
    private final float x1;
    private final float y1;

    /**
     * Creates a new 4D vector.
     *
     * @param x0 X0 component
     * @param y0 Y0 component
     * @param x1 X1 component
     * @param y1 Y1 component
     */
    protected Vec4(float x0, float y0, float x1, float y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    /**
     * Creates a new 4D vector.
     *
     * @param x0 X0 component
     * @param y0 Y0 component
     * @param x1 X1 component
     * @param y1 Y1 component
     */
    @NotNull
    public static Vec4 of(float x0, float y0, float x1, float y1) {
        return new Vec4(x0, y0, x1, y1);
    }

    /**
     * Get the X0 component.
     *
     * @return The X0 component
     */
    public float getX0() {
        return this.x0;
    }

    /**
     * Get the Y0 component.
     *
     * @return The Y0 component
     */
    public float getY0() {
        return this.y0;
    }

    /**
     * Get the X1 component.
     *
     * @return The X1 component
     */
    public float getX1() {
        return this.x1;
    }

    /**
     * Get the Y1 component.
     *
     * @return The Y1 component
     */
    public float getY1() {
        return this.y1;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper();
        json.addProperty("x0", getX0());
        json.addProperty("y0", getY0());
        json.addProperty("x1", getX1());
        json.addProperty("y1", getY1());
        return json.getJsonObject();
    }

    /**
     * Create a new 4D vector from Json.
     *
     * @param json Json representation of a 4D vector
     * @return A new 4D vector
     */
    @NotNull
    public static Vec4 fromJson(@NotNull JsonObject json) {
        return new Vec4(
            !json.has("x0") ? 0 : json.get("x0").getAsFloat(),
            !json.has("y0") ? 0 : json.get("y0").getAsFloat(),
            !json.has("x1") ? 0 : json.get("x1").getAsFloat(),
            !json.has("y1") ? 0 : json.get("y1").getAsFloat()
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
        Vec4 other = (Vec4) o;
        return Float.compare(getX0(), other.getX0()) == 0
            && Float.compare(getY0(), other.getY0()) == 0
            && Float.compare(getX1(), other.getX1()) == 0
            && Float.compare(getY1(), other.getY1()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX0(), getY0(), getX1(), getY1());
    }

    @Override
    @NotNull
    public String toString() {
        return "Vec4{"
            + "x0=" + getX0()
            + ",y0=" + getY0()
            + ",x1=" + getX1()
            + ",y1=" + getY1()
            + "}";
    }
}
