package net.pl3x.guithium.api.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.guithium.api.json.Gson;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
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

    /**
     * Get the X value.
     *
     * @return The X value
     */
    public float getX() {
        return x();
    }

    /**
     * Get the Y value.
     *
     * @return The Y value
     */
    public float getY() {
        return y();
    }

    /**
     * Get the Z value.
     *
     * @return The Z value
     */
    public float getZ() {
        return z();
    }

    /**
     * Get the W value.
     *
     * @return The W value
     */
    public float getW() {
        return w();
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("%s%s", getClass().getSimpleName(), Gson.toJson(this));
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper();
        json.addProperty("x", getX());
        json.addProperty("y", getY());
        json.addProperty("z", getZ());
        json.addProperty("w", getW());
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
                !json.has("x") ? 0 : json.get("x").getAsFloat(),
                !json.has("y") ? 0 : json.get("y").getAsFloat(),
                !json.has("z") ? 0 : json.get("z").getAsFloat(),
                !json.has("w") ? 0 : json.get("w").getAsFloat()
        );
    }
}
