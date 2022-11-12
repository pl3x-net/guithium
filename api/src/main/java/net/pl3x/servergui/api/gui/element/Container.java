package net.pl3x.servergui.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Container extends AbstractElement {
    public Container(@NotNull String id, @Nullable String parent, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, @Nullable Float scale, @Nullable Double zIndex) {
        super(id, "container", parent, pos, anchor, offset, scale, zIndex);
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        return super.toJson();
    }

    @NotNull
    public static Container fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("id"), "ID cannot be null");
        return new Container(
            json.get("id").getAsString(),
            !json.has("parent") ? null : json.get("parent").getAsString(),
            !json.has("pos") ? null : Point.fromJson(json.get("pos").getAsJsonObject()),
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
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s{%s}", "Container", getPropertiesAsString());
    }

    public static Builder builder(@NotNull String id) {
        return new Builder(id);
    }

    public static class Builder extends AbstractBuilder<Builder> {
        public Builder(@NotNull String id) {
            super(id);
        }

        @Override
        @NotNull
        public Container build() {
            return new Container(getId(), getParent(), getPos(), getAnchor(), getOffset(), getScale(), getZIndex());
        }
    }
}
