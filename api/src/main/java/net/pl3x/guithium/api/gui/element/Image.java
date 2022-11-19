package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.texture.Texture;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Image extends Rect {
    public static final Image TILED_DIRT = Image.builder(Guithium.MOD_ID + ":tiled_dirt")
        .setTexture(Texture.DIRT)
        .setTint(0xFF404040)
        .setTileModifier(32.0F)
        .build();

    private Texture texture;
    private Integer tint;
    private Float tileModifier;

    public Image(@NotNull Key key, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, @Nullable Point size, @NotNull Texture texture, @Nullable Integer tint, @Nullable Float tileModifier) {
        super(key, Type.IMAGE, pos, anchor, offset, size);
        setTexture(texture);
        setTint(tint);
        setTileModifier(tileModifier);
    }

    @NotNull
    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(@NotNull Texture texture) {
        Preconditions.checkNotNull(texture, "Texture cannot be null");
        this.texture = texture;
    }

    @Nullable
    public Integer getTint() {
        return this.tint;
    }

    public void setTint(@Nullable Integer tint) {
        this.tint = tint;
    }

    @Nullable
    public Float getTileModifier() {
        return this.tileModifier;
    }

    public void setTileModifier(@Nullable Float tileModifier) {
        this.tileModifier = tileModifier;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("texture", getTexture());
        json.addProperty("tint", getTint());
        json.addProperty("tileMod", getTileModifier());
        return json.getJsonObject();
    }

    @NotNull
    public static Image fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        Preconditions.checkArgument(json.has("texture"), "Texture cannot be null");
        return new Image(
            Key.of(json.get("key").getAsString()),
            !json.has("pos") ? null : Point.fromJson(json.get("pos").getAsJsonObject()),
            !json.has("anchor") ? null : Point.fromJson(json.get("anchor").getAsJsonObject()),
            !json.has("offset") ? null : Point.fromJson(json.get("offset").getAsJsonObject()),
            !json.has("size") ? null : Point.fromJson(json.get("size").getAsJsonObject()),
            Texture.fromJson(json.get("texture").getAsJsonObject()),
            !json.has("tint") ? null : json.get("tint").getAsInt(),
            !json.has("tileMod") ? null : json.get("tileMod").getAsFloat()
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
        return getTexture().equals(other.getTexture())
            && Objects.equals(getTint(), other.getTint())
            && Objects.equals(getTileModifier(), other.getTileModifier())
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTexture(), getTint(), getTileModifier(), super.hashCode());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("%s{%s}", "Image", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",texture=" + getTexture()
            + ",tint=" + getTint()
            + ",tileMod=" + getTileModifier();
    }

    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    public static class Builder extends Rect.Builder<Builder> {
        private Texture texture = Texture.DIRT;
        private Integer tint;
        private Float tileModifier;

        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        public Builder(@NotNull Key key) {
            super(key);
        }

        @NotNull
        public Texture getTexture() {
            return this.texture;
        }

        @NotNull
        public Builder setTexture(@NotNull Texture texture) {
            Preconditions.checkNotNull(texture, "Texture cannot be null");
            this.texture = texture;
            return this;
        }

        @Nullable
        public Integer getTint() {
            return this.tint;
        }

        @NotNull
        public Builder setTint(@Nullable Integer tint) {
            this.tint = tint;
            return this;
        }

        @Nullable
        public Float getTileModifier() {
            return this.tileModifier;
        }

        @NotNull
        public Builder setTileModifier(@Nullable Float tileModifier) {
            this.tileModifier = tileModifier;
            return this;
        }

        @Override
        @NotNull
        public Image build() {
            return new Image(getKey(), getPos(), getAnchor(), getOffset(), getSize(), getTexture(), getTint(), getTileModifier());
        }
    }
}
