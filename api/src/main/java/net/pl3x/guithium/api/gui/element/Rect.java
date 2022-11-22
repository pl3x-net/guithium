package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a rectangular element with a size property.
 */
public abstract class Rect extends AbstractElement {
    private Point size;

    /**
     * Creates a new element.
     *
     * @param key    Unique identifier for element
     * @param type   Type of element
     * @param pos    Position of element
     * @param anchor Anchor for element
     * @param offset Offset of element
     * @param size   Size of element
     */
    protected Rect(@NotNull Key key, @NotNull Type type, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, @Nullable Point size) {
        super(key, type, pos, anchor, offset);
        setSize(size);
    }

    /**
     * Get the size of this element (width and height).
     *
     * @return Size of element
     */
    @Nullable
    public Point getSize() {
        return this.size;
    }

    /**
     * Set the size of this element.
     *
     * @param x X size (width)
     * @param y Y size (height)
     */
    public void setSize(float x, float y) {
        setSize(Point.of(x, y));
    }

    /**
     * Set the size of this element.
     *
     * @param size Size of element
     */
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
        return String.format("Rect{%s}", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",size=" + getSize();
    }

    /**
     * Builder for sizeable element.
     *
     * @param <T> Type of builder
     */
    public abstract static class Builder<T extends Builder<T>> extends AbstractBuilder<T> {
        private Point size;

        /**
         * Creates a new builder.
         *
         * @param key Unique identifying key for element
         */
        public Builder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get the size of this element (width and height).
         *
         * @return Size of element
         */
        @Nullable
        public Point getSize() {
            return this.size;
        }

        /**
         * Set the size of this element.
         *
         * @param x X size (width)
         * @param y Y size (height)
         * @return This builder
         */
        @NotNull
        public T setSize(float x, float y) {
            return setSize(Point.of(x, y));
        }

        /**
         * Set the size of this element.
         *
         * @param size Size of element
         * @return This builder
         */
        @NotNull
        @SuppressWarnings("unchecked")
        public T setSize(@Nullable Point size) {
            Preconditions.checkNotNull(size, "Size cannot be null");
            this.size = size;
            return (T) this;
        }

        /**
         * Build a new rect from the current properties in this builder.
         *
         * @return New rect
         */
        @Override
        @NotNull
        public abstract Rect build();
    }
}
