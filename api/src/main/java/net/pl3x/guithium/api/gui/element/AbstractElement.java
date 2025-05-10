package net.pl3x.guithium.api.gui.element;

import java.util.Objects;
import net.pl3x.guithium.api.Unsafe;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.key.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a base keyed element to be displayed on a {@link Screen}.
 *
 * @param <T> An abstract element
 */
public abstract class AbstractElement<T extends AbstractElement<T>> extends Keyed implements Element {
    private Point pos;
    private Point anchor;
    private Point offset;

    /**
     * Create a new keyed element.
     *
     * @param key    Unique identifier for element
     * @param pos    Position of element
     * @param anchor Anchor for element
     * @param offset Offset of element
     */
    protected AbstractElement(@NotNull Key key, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset) {
        super(key);
        setPos(pos);
        setAnchor(anchor);
        setOffset(offset);
    }

    /**
     * Get the element's position from the anchor point.
     *
     * @return Position from anchor
     */
    @Override
    @Nullable
    public Point getPos() {
        return this.pos;
    }

    /**
     * Set this element's position from the anchor point.
     *
     * @param x X (horizontal) position from anchor
     * @param y Y (vertical) position from anchor
     * @return This element
     */
    @Override
    public T setPos(float x, float y) {
        setPos(Point.of(x, y));
        return Unsafe.cast(this);
    }

    @Override
    public T setPos(@Nullable Point pos) {
        this.pos = pos;
        return Unsafe.cast(this);
    }

    @Override
    @Nullable
    public Point getAnchor() {
        return this.anchor;
    }

    @Override
    public T setAnchor(float x, float y) {
        setAnchor(Point.of(x, y));
        return Unsafe.cast(this);
    }

    @Override
    public T setAnchor(@Nullable Point anchor) {
        this.anchor = anchor;
        return Unsafe.cast(this);
    }

    @Override
    @Nullable
    public Point getOffset() {
        return this.offset;
    }

    @Override
    public T setOffset(float x, float y) {
        setOffset(Point.of(x, y));
        return Unsafe.cast(this);
    }

    @Override
    public T setOffset(@Nullable Point offset) {
        this.offset = offset;
        return Unsafe.cast(this);
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
        T other = Unsafe.cast(o);
        return super.equals(o)
                && Objects.equals(getKey(), other.getKey())
                && Objects.equals(getPos(), other.getPos())
                && Objects.equals(getAnchor(), other.getAnchor())
                && Objects.equals(getOffset(), other.getOffset());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getPos(), getAnchor(), getOffset());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("AbstractElement{%s}", getPropertiesAsString());
    }

    /**
     * Gets this element's properties as a comma separated list.
     * <p>
     * For internal use.
     *
     * @return Comma separated list of properties
     */
    @NotNull
    protected String getPropertiesAsString() {
        return String.format("key=%s,pos=%s,anchor=%s,offset=%s", getKey(), getPos(), getAnchor(), getOffset());
    }

    /**
     * Builder for base element.
     *
     * @param <T> Type of builder
     */
    public static abstract class AbstractBuilder<T extends AbstractBuilder<T>> extends Keyed {
        private Point pos;
        private Point anchor;
        private Point offset;

        /**
         * Creates a new builder.
         *
         * @param key Unique identifying key for element
         */
        public AbstractBuilder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get the element's position from the anchor point.
         *
         * @return Position from anchor
         */
        @Nullable
        public Point getPos() {
            return pos;
        }

        /**
         * Set this element's position from the anchor point.
         *
         * @param x X (horizontal) position from anchor
         * @param y Y (vertical) position from anchor
         * @return This builder
         */
        @NotNull
        public T setPos(float x, float y) {
            return setPos(Point.of(x, y));
        }

        /**
         * Set this element's position from the anchor point.
         *
         * @param pos Position from anchor
         * @return This builder
         */
        @NotNull
        public T setPos(@Nullable Point pos) {
            this.pos = pos;
            return Unsafe.cast(this);
        }

        /**
         * Get this element's anchor point on the screen.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the parent's size.
         *
         * @return Anchor point
         */
        @Nullable
        public Point getAnchor() {
            return anchor;
        }

        /**
         * Set this element's anchor point on the screen.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the parent's size.
         *
         * @param x X (horizontal) anchor
         * @param y Y (vertical) anchor
         * @return This builder
         */
        @NotNull
        public T setAnchor(float x, float y) {
            return setAnchor(Point.of(x, y));
        }

        /**
         * Set this element's anchor point on the screen.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the parent's size.
         *
         * @param anchor Anchor point
         * @return This builder
         */
        @NotNull
        public T setAnchor(@Nullable Point anchor) {
            this.anchor = anchor;
            return Unsafe.cast(this);
        }

        /**
         * Get this element's position offset.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the element's size.
         *
         * @return Position offset
         */
        @Nullable
        public Point getOffset() {
            return offset;
        }

        /**
         * Set this element's position offset.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the element's size.
         *
         * @param x X (horizontal) position offset
         * @param y Y (vertical) position offset
         * @return This builder
         */
        @NotNull
        public T setOffset(float x, float y) {
            return setOffset(Point.of(x, y));
        }

        /**
         * Set this element's position offset.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the element's size.
         *
         * @param offset Position offset
         * @return This builder
         */
        @NotNull
        public T setOffset(@Nullable Point offset) {
            this.offset = offset;
            return Unsafe.cast(this);
        }

        /**
         * Build a new element from the current properties in this builder.
         *
         * @return New element
         */
        @NotNull
        public abstract AbstractElement<? extends AbstractElement<?>> build();
    }
}
