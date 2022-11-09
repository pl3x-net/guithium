package net.pl3x.servergui.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import net.pl3x.servergui.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Image extends AbstractElement {
    private Point size;

    public Image(@NotNull String id, @Nullable Point pos, @Nullable Point size, @Nullable Point anchor, @Nullable Point offset, float scale, double zIndex) {
        super(id, "image", pos, anchor, offset, scale, zIndex);
        setSize(size);
    }

    @NotNull
    public Point getSize() {
        return this.size;
    }

    public void setSize(float x, float y) {
        setSize(Point.of(x, y));
    }

    public void setSize(@Nullable Point size) {
        this.size = size == null ? new Point(32, 32) : size;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper();
        json.addProperty("id", getId());
        json.addProperty("type", getType());
        json.addProperty("size", getSize());
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
        Image other = (Image) o;
        return getSize() == other.getSize()
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSize(), super.hashCode());
    }

    @Override
    public String toString() {
        return "Image{"
            + "id=" + getId()
            + ",type=" + getType()
            + ",size=" + getSize()
            + ",pos=" + getPos()
            + ",anchor=" + getAnchor()
            + ",offset=" + getOffset()
            + ",scale=" + getScale()
            + ",z-index=" + getZIndex()
            + "}";
    }

    public static Builder builder(@NotNull String id) {
        return new Builder(id);
    }

    public static class Builder {
        private String id;
        private Point pos = new Point();
        private Point size = new Point();
        private Point anchor = new Point();
        private Point offset = new Point();
        private float scale = 1.0F;
        private double zIndex = 0.0D;

        public Builder(@NotNull String id) {
            setId(id);
        }

        @NotNull
        public String getId() {
            return id;
        }

        @NotNull
        public Builder setId(@NotNull String id) {
            Preconditions.checkNotNull(id, "ID cannot be null");
            this.id = id;
            return this;
        }

        @NotNull
        public Point getPos() {
            return pos;
        }

        @NotNull
        public Builder setPos(float x, float y) {
            return setPos(Point.of(x, y));
        }

        @NotNull
        public Builder setPos(@Nullable Point pos) {
            this.pos = pos == null ? new Point() : pos;
            return this;
        }

        @NotNull
        public Point getSize() {
            return size;
        }

        @NotNull
        public Builder setSize(float x, float y) {
            return setSize(Point.of(x, y));
        }

        @NotNull
        public Builder setSize(@Nullable Point size) {
            this.size = size == null ? new Point(32, 32) : size;
            return this;
        }

        @NotNull
        public Point getAnchor() {
            return anchor;
        }

        @NotNull
        public Builder setAnchor(float x, float y) {
            return setAnchor(Point.of(x, y));
        }

        @NotNull
        public Builder setAnchor(@Nullable Point anchor) {
            this.anchor = anchor == null ? new Point() : anchor;
            return this;
        }

        @NotNull
        public Point getOffset() {
            return offset;
        }

        @NotNull
        public Builder setOffset(float x, float y) {
            return setOffset(Point.of(x, y));
        }

        @NotNull
        public Builder setOffset(@Nullable Point offset) {
            this.offset = offset == null ? new Point() : offset;
            return this;
        }

        public float getScale() {
            return scale;
        }

        @NotNull
        public Builder setScale(float scale) {
            this.scale = scale;
            return this;
        }

        public double getZIndex() {
            return zIndex;
        }

        @NotNull
        public Builder setZIndex(double zIndex) {
            this.zIndex = zIndex;
            return this;
        }

        @NotNull
        public Image build() {
            return new Image(getId(), getPos(), getSize(), getAnchor(), getOffset(), getScale(), getZIndex());
        }
    }
}
