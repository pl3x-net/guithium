package net.pl3x.servergui.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import net.pl3x.servergui.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Text extends AbstractElement {
    private String text;
    private boolean shadow;

    public Text(@NotNull String id, @NotNull String text, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, boolean shadow, float scale, double zIndex) {
        super(id, "text", pos, anchor, offset, scale, zIndex);
        setText(text);
        setShadow(shadow);
    }

    @NotNull
    public String getText() {
        return this.text;
    }

    public void setText(@NotNull String text) {
        Preconditions.checkNotNull(text, "Text cannot be null");
        this.text = text;
    }

    public boolean hasShadow() {
        return this.shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper();
        json.addProperty("id", getId());
        json.addProperty("type", getType());
        json.addProperty("text", getText());
        json.addProperty("shadow", hasShadow());
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
        Text other = (Text) o;
        return getText().equals(other.getText())
            && hasShadow() == other.hasShadow()
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), hasShadow(), super.hashCode());
    }

    @Override
    public String toString() {
        return "Text{"
            + "id=" + getId()
            + ",type=" + getType()
            + ",text=" + getText()
            + ",shadow=" + hasShadow()
            + ",pos=" + getPos()
            + ",anchor=" + getAnchor()
            + ",offset=" + getOffset()
            + ",scale=" + getScale()
            + ",z-index=" + getZIndex()
            + "}";
    }

    public static Builder builder(@NotNull String id, @NotNull String text) {
        return new Builder(id, text);
    }

    public static class Builder {
        private String id;
        private String text;
        private Point pos = new Point();
        private Point anchor = new Point();
        private Point offset = new Point();
        private boolean shadow = true;
        private float scale = 1.0F;
        private double zIndex = 0.0D;

        public Builder(@NotNull String id, @NotNull String text) {
            setId(id);
            setText(text);
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
        public String getText() {
            return text;
        }

        @NotNull
        public Builder setText(@NotNull String text) {
            Preconditions.checkNotNull(text, "Text cannot be null");
            this.text = text;
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

        public boolean hasShadow() {
            return shadow;
        }

        @NotNull
        public Builder setShadow(boolean shadow) {
            this.shadow = shadow;
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
        public Text build() {
            return new Text(getId(), getText(), getPos(), getAnchor(), getOffset(), hasShadow(), getScale(), getZIndex());
        }
    }
}
