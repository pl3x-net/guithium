package net.pl3x.guithium.api.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Point implements JsonSerializable {
    public static final Point ZERO = new Point(0, 0);
    public static final Point ONE = new Point(1, 1);

    private float x;
    private float y;

    public Point() {
        this(0, 0);
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @NotNull
    public static Point of(float x, float y) {
        return new Point(x, y);
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper();
        json.addProperty("x", getX());
        json.addProperty("y", getY());
        return json.getJsonObject();
    }

    @NotNull
    public static Point fromJson(@NotNull JsonObject json) {
        return new Point(
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
        Point other = (Point) o;
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
        return "Point{x=" + getX() + ",y=" + getY() + "}";
    }
}
