package net.pl3x.servergui.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.servergui.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Image extends AbstractElement {
    private Point size;

    public Image(@NotNull String id, @Nullable String parent, @Nullable Point pos, @Nullable Point size, @Nullable Point anchor, @Nullable Point offset, @Nullable Float scale, @Nullable Double zIndex) {
        super(id, "image", parent, pos, anchor, offset, scale, zIndex);
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

    @NotNull
    public static Image fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("id"), "ID cannot be null");
        return new Image(
            json.get("id").getAsString(),
            !json.has("parent") ? null : json.get("parent").getAsString(),
            !json.has("pos") ? null : Point.fromJson(json.get("pos").getAsJsonObject()),
            !json.has("size") ? null : Point.fromJson(json.get("size").getAsJsonObject()),
            !json.has("anchor") ? null : Point.fromJson(json.get("anchor").getAsJsonObject()),
            !json.has("offset") ? null : Point.fromJson(json.get("offset").getAsJsonObject()),
            !json.has("scale") ? null : json.get("scale").getAsFloat(),
            !json.has("zIndex") ? null : json.get("zIndex").getAsDouble()
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
        Image other = (Image) o;
        return Objects.equals(getSize(), other.getSize())
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSize(), super.hashCode());
    }

    @Override
    public String toString() {
        return String.format("%s{%s}", "Image", getPropertiesAsString());
    }

    @Override
    @NotNull
    public String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",size=" + getSize();
    }

    public static Builder builder(@NotNull String id) {
        return new Builder(id);
    }

    public static class Builder extends AbstractBuilder<Builder> {
        private Point size;

        public Builder(@NotNull String id) {
            super(id);
        }

        @Nullable
        public Point getSize() {
            return size;
        }

        @NotNull
        public Builder setSize(float x, float y) {
            return setSize(Point.of(x, y));
        }

        @NotNull
        public Builder setSize(@Nullable Point size) {
            this.size = size;
            return this;
        }

        @Override
        @NotNull
        public Image build() {
            return new Image(getId(), getParent(), getPos(), getSize(), getAnchor(), getOffset(), getScale(), getZIndex());
        }
    }
}
