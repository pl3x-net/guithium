package net.pl3x.servergui.api.gui.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.servergui.api.json.JsonObjectWrapper;
import net.pl3x.servergui.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Point implements JsonSerializable {
    private float x;
    private float y;

    public Point() {
        this(0, 0);
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(JsonObject json) {
        this(
            json.get("x").getAsFloat(),
            json.get("y").getAsFloat()
        );
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
