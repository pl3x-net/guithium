package net.pl3x.servergui.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.Keyed;
import net.pl3x.servergui.api.gui.Point;
import net.pl3x.servergui.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class AbstractElement implements Element {
    private final Key key;
    private final String type;
    private Point pos;
    private Point anchor;
    private Point offset;
    private Float scale;
    private Double zIndex;

    public AbstractElement(@NotNull Key key, @NotNull String type, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, @Nullable Float scale, @Nullable Double zIndex) {
        Preconditions.checkNotNull(key, "Key cannot be null");
        Preconditions.checkNotNull(type, "Type cannot be null");
        this.key = key;
        this.type = type;
        setPos(pos);
        setAnchor(anchor);
        setOffset(offset);
        setScale(scale);
        setZIndex(zIndex);
    }

    @Override
    @NotNull
    public Key getKey() {
        return this.key;
    }

    @Override
    @NotNull
    public String getType() {
        return this.type;
    }

    @Override
    @Nullable
    public Point getPos() {
        return this.pos;
    }

    @Override
    public void setPos(float x, float y) {
        setPos(Point.of(x, y));
    }

    @Override
    public void setPos(@Nullable Point pos) {
        this.pos = pos;
    }

    @Override
    @Nullable
    public Point getAnchor() {
        return this.anchor;
    }

    @Override
    public void setAnchor(float x, float y) {
        setAnchor(Point.of(x, y));
    }

    @Override
    public void setAnchor(@Nullable Point anchor) {
        this.anchor = anchor;
    }

    @Override
    @Nullable
    public Point getOffset() {
        return this.offset;
    }

    @Override
    public void setOffset(float x, float y) {
        setOffset(Point.of(x, y));
    }

    @Override
    public void setOffset(@Nullable Point offset) {
        this.offset = offset;
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
    @Nullable
    public Double getZIndex() {
        return this.zIndex;
    }

    @Override
    public void setZIndex(@Nullable Double zIndex) {
        this.zIndex = zIndex;
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
        json.addProperty("scale", getScale());
        json.addProperty("zIndex", getZIndex());
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
            && Objects.equals(getScale(), other.getScale())
            && Objects.equals(getZIndex(), other.getZIndex());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getType(), getPos(), getAnchor(), getOffset(), getScale(), getZIndex());
    }

    @Override
    public String toString() {
        return String.format("%s{%s}", "AbstractElement", getPropertiesAsString());
    }

    @NotNull
    public String getPropertiesAsString() {
        return "key=" + getKey()
            + ",type=" + getType()
            + ",pos=" + getPos()
            + ",anchor=" + getAnchor()
            + ",offset=" + getOffset()
            + ",scale=" + getScale()
            + ",z-index=" + getZIndex();
    }

    public static abstract class AbstractBuilder<T extends AbstractBuilder<T>> extends Keyed {
        private Point pos;
        private Point anchor;
        private Point offset;
        private Float scale;
        private Double zIndex;

        public AbstractBuilder(@NotNull Key key) {
            super(key);
        }

        @Nullable
        public Point getPos() {
            return pos;
        }

        @NotNull
        public T setPos(float x, float y) {
            return setPos(Point.of(x, y));
        }

        @NotNull
        @SuppressWarnings("unchecked")
        public T setPos(@Nullable Point pos) {
            this.pos = pos;
            return (T) this;
        }

        @Nullable
        public Point getAnchor() {
            return anchor;
        }

        @NotNull
        public T setAnchor(float x, float y) {
            return setAnchor(Point.of(x, y));
        }

        @NotNull
        @SuppressWarnings("unchecked")
        public T setAnchor(@Nullable Point anchor) {
            this.anchor = anchor;
            return (T) this;
        }

        @Nullable
        public Point getOffset() {
            return offset;
        }

        @NotNull
        public T setOffset(float x, float y) {
            return setOffset(Point.of(x, y));
        }

        @NotNull
        @SuppressWarnings("unchecked")
        public T setOffset(@Nullable Point offset) {
            this.offset = offset;
            return (T) this;
        }

        @Nullable
        public Float getScale() {
            return scale;
        }

        @NotNull
        @SuppressWarnings("unchecked")
        public T setScale(@Nullable Float scale) {
            this.scale = scale;
            return (T) this;
        }

        @Nullable
        public Double getZIndex() {
            return zIndex;
        }

        @NotNull
        @SuppressWarnings("unchecked")
        public T setZIndex(@Nullable Double zIndex) {
            this.zIndex = zIndex;
            return (T) this;
        }

        @NotNull
        public abstract AbstractElement build();
    }
}
