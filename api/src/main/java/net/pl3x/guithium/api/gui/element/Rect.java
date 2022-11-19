package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class Rect extends AbstractElement {
    private Point size;

    public Rect(@NotNull Key key, @NotNull Type type, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, @Nullable Point size) {
        super(key, type, pos, anchor, offset);
        setSize(size);
    }

    @Nullable
    public Point getSize() {
        return this.size;
    }

    public void setSize(float x, float y) {
        setSize(Point.of(x, y));
    }

    public void setSize(@Nullable Point size) {
        this.size = size;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("size", getSize());
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
        Gradient other = (Gradient) o;
        return Objects.equals(getSize(), other.getSize())
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSize(), super.hashCode());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("%s{%s}", "Rect", getPropertiesAsString());
    }

    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",size=" + getSize();
    }

    public abstract static class Builder<T extends Builder<T>> extends AbstractBuilder<T> {
        private Point size;

        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        public Builder(@NotNull Key key) {
            super(key);
        }

        @Nullable
        public Point getSize() {
            return this.size;
        }

        @NotNull
        public T setSize(float x, float y) {
            return setSize(Point.of(x, y));
        }

        @NotNull
        @SuppressWarnings("unchecked")
        public T setSize(@Nullable Point size) {
            Preconditions.checkNotNull(size, "Size cannot be null");
            this.size = size;
            return (T) this;
        }

        @NotNull
        public abstract Rect build();
    }
}
