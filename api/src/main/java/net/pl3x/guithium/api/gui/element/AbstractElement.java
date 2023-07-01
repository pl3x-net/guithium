package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import java.util.Objects;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.Keyed;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a base keyed element.
 */
public abstract class AbstractElement extends Keyed implements Element {
    private final Type type;
    private Vec2 pos;
    private Vec2 anchor;
    private Vec2 offset;
    private Float rotation;
    private Float scale;

    /**
     * Creates a new element.
     *
     * @param key      Unique identifier for element
     * @param type     Type of element
     * @param pos      Position of element
     * @param anchor   Anchor for element
     * @param offset   Offset of element
     * @param rotation Rotation in degrees
     * @param scale    Scale of element
     */
    protected AbstractElement(@NotNull Key key, @NotNull Type type, @Nullable Vec2 pos, @Nullable Vec2 anchor, @Nullable Vec2 offset, @Nullable Float rotation, @Nullable Float scale) {
        super(key);
        Preconditions.checkNotNull(type, "Type cannot be null");
        this.type = type;
        setPos(pos);
        setAnchor(anchor);
        setOffset(offset);
        setRotation(rotation);
        setScale(scale);
    }

    @Override
    @NotNull
    public Type getType() {
        return this.type;
    }

    @Override
    @Nullable
    public Vec2 getPos() {
        return this.pos;
    }

    @Override
    public void setPos(float x, float y) {
        setPos(Vec2.of(x, y));
    }

    @Override
    public void setPos(@Nullable Vec2 pos) {
        this.pos = pos;
    }

    @Override
    @Nullable
    public Vec2 getAnchor() {
        return this.anchor;
    }

    @Override
    public void setAnchor(float x, float y) {
        setAnchor(Vec2.of(x, y));
    }

    @Override
    public void setAnchor(@Nullable Vec2 anchor) {
        this.anchor = anchor;
    }

    @Override
    @Nullable
    public Vec2 getOffset() {
        return this.offset;
    }

    @Override
    public void setOffset(float x, float y) {
        setOffset(Vec2.of(x, y));
    }

    @Override
    public void setOffset(@Nullable Vec2 offset) {
        this.offset = offset;
    }

    @Override
    @Nullable
    public Float getRotation() {
        return this.rotation;
    }

    @Override
    public void setRotation(@Nullable Float degrees) {
        this.rotation = degrees;
    }

    @Override
    @Nullable
    public Float getScale() {
        return this.scale;
    }

    @Override
    public void setScale(@Nullable Float scale) {
        this.scale = scale;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper();
        json.addProperty("key", getKey());
        json.addProperty("type", getType());
        json.addProperty("pos", getPos());
        json.addProperty("anchor", getAnchor());
        json.addProperty("offset", getOffset());
        json.addProperty("rotation", getRotation());
        json.addProperty("scale", getScale());
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
        Element other = (Element) o;
        return Objects.equals(getKey(), other.getKey())
                && Objects.equals(getType(), other.getType())
                && Objects.equals(getPos(), other.getPos())
                && Objects.equals(getAnchor(), other.getAnchor())
                && Objects.equals(getOffset(), other.getOffset())
                && Objects.equals(getRotation(), other.getRotation())
                && Objects.equals(getScale(), other.getScale());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getType(), getPos(), getAnchor(), getOffset(), getRotation(), getScale());
    }

    @Override
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
        return "key=" + getKey()
                + ",type=" + getType()
                + ",pos=" + getPos()
                + ",anchor=" + getAnchor()
                + ",offset=" + getOffset()
                + ",rotation=" + getRotation()
                + ",scale=" + getScale();
    }

    /**
     * Builder for base element.
     *
     * @param <T> Type of builder
     */
    public static abstract class AbstractBuilder<T extends AbstractBuilder<T>> extends Keyed {
        private Vec2 pos;
        private Vec2 anchor;
        private Vec2 offset;
        private Float rotation;
        private Float scale;

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
         * <p>
         * If null, default position <code>0,0</code> will be used.
         *
         * @return Position from anchor
         */
        @Nullable
        public Vec2 getPos() {
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
            return setPos(Vec2.of(x, y));
        }

        /**
         * Set this element's position from the anchor point.
         * <p>
         * If null, default position <code>0,0</code> will be used.
         *
         * @param pos Position from anchor
         * @return This builder
         */
        @NotNull
        @SuppressWarnings("unchecked")
        public T setPos(@Nullable Vec2 pos) {
            this.pos = pos;
            return (T) this;
        }

        /**
         * Get this element's anchor point on the screen.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the parent's size.
         * <p>
         * If null, default anchor <code>0,0</code> will be used.
         *
         * @return Anchor point
         */
        @Nullable
        public Vec2 getAnchor() {
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
            return setAnchor(Vec2.of(x, y));
        }

        /**
         * Set this element's anchor point on the screen.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the parent's size.
         * <p>
         * If null, default anchor <code>0,0</code> will be used.
         *
         * @param anchor Anchor point
         * @return This builder
         */
        @NotNull
        @SuppressWarnings("unchecked")
        public T setAnchor(@Nullable Vec2 anchor) {
            this.anchor = anchor;
            return (T) this;
        }

        /**
         * Get this element's position offset.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the element's size.
         * <p>
         * If null, default offset <code>0,0</code> will be used.
         *
         * @return Position offset
         */
        @Nullable
        public Vec2 getOffset() {
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
            return setOffset(Vec2.of(x, y));
        }

        /**
         * Set this element's position offset.
         * <p>
         * This is represented as a percentage (0.0-1.0) of the element's size.
         * <p>
         * If null, default offset <code>0,0</code> will be used.
         *
         * @param offset Position offset
         * @return This builder
         */
        @NotNull
        @SuppressWarnings("unchecked")
        public T setOffset(@Nullable Vec2 offset) {
            this.offset = offset;
            return (T) this;
        }

        /**
         * Get this element's rotation in degrees.
         * <p>
         * If null, default rotation <code>0.0</code> will be used.
         *
         * @return Degrees of rotation
         */
        @Nullable
        public Float getRotation() {
            return this.rotation;
        }

        /**
         * Set this element's rotation in degrees.
         * <p>
         * If null, default rotation <code>0.0</code> will be used.
         *
         * @param degrees Degrees of rotation
         * @return This builder
         */
        @SuppressWarnings("unchecked")
        public T setRotation(@Nullable Float degrees) {
            this.rotation = degrees;
            return (T) this;
        }

        /**
         * Get this element's scale.
         * <p>
         * If null, default scale <code>1.0</code> will be used.
         *
         * @return Element's scale
         */
        @Nullable
        public Float getScale() {
            return this.scale;
        }

        /**
         * Set this element's scale.
         * <p>
         * If null, default scale <code>1.0</code> will be used.
         *
         * @param scale Element's scale
         * @return This builder
         */
        @SuppressWarnings("unchecked")
        public T setScale(@Nullable Float scale) {
            this.scale = scale;
            return (T) this;
        }

        /**
         * Build a new element from the current properties in this builder.
         *
         * @return New element
         */
        @NotNull
        public abstract AbstractElement build();
    }
}
