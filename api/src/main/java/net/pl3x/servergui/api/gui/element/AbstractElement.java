package net.pl3x.servergui.api.gui.element;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class AbstractElement implements Element {
    private final String id;
    private final String type;
    private Point pos;
    private Point anchor;
    private Point offset;
    private float scale;
    private double zIndex;

    public AbstractElement(@NotNull String id, @NotNull String type, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, float scale, double zIndex) {
        Preconditions.checkNotNull(id, "ID cannot be null");
        Preconditions.checkNotNull(type, "Type cannot be null");
        this.id = id;
        this.type = type;
        setPos(pos);
        setAnchor(anchor);
        setOffset(offset);
        setScale(scale);
        setZIndex(zIndex);
    }

    @Override
    @NotNull
    public String getId() {
        return this.id;
    }

    @Override
    @NotNull
    public String getType() {
        return this.type;
    }

    @Override
    @NotNull
    public Point getPos() {
        return this.pos;
    }

    public void setPos(float x, float y) {
        setPos(Point.of(x, y));
    }

    @Override
    public void setPos(@Nullable Point pos) {
        this.pos = pos == null ? new Point() : pos;
    }

    @Override
    @NotNull
    public Point getAnchor() {
        return this.anchor;
    }

    public void setAnchor(float x, float y) {
        setAnchor(Point.of(x, y));
    }

    @Override
    public void setAnchor(@Nullable Point anchor) {
        this.anchor = anchor == null ? new Point() : anchor;
    }

    @Override
    @NotNull
    public Point getOffset() {
        return this.offset;
    }

    public void setOffset(float x, float y) {
        setOffset(Point.of(x, y));
    }

    @Override
    public void setOffset(@Nullable Point offset) {
        this.offset = offset == null ? new Point() : offset;
    }

    @Override
    public float getScale() {
        return this.scale;
    }

    @Override
    public void setScale(float scale) {
        this.scale = scale;
    }

    @Override
    public double getZIndex() {
        return this.zIndex;
    }

    @Override
    public void setZIndex(double zIndex) {
        this.zIndex = zIndex;
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
        return getId().equals(other.getId())
            && getType().equals(other.getType())
            && getPos().equals(other.getPos())
            && getAnchor().equals(other.getAnchor())
            && getOffset().equals(other.getOffset())
            && getScale() == other.getScale()
            && getZIndex() == other.getZIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getPos(), getAnchor(), getOffset(), getScale(), getZIndex());
    }

    @Override
    public String toString() {
        return "Element{"
            + "id=" + getId()
            + ",type=" + getType()
            + ",pos=" + getPos()
            + ",anchor=" + getAnchor()
            + ",offset=" + getOffset()
            + ",scale=" + getScale()
            + ",z-index=" + getZIndex()
            + "}";
    }
}
