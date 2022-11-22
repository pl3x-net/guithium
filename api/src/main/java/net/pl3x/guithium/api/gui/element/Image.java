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

/**
 * Represents an image.
 */
public class Image extends Rect {
    public static final Image TILED_DIRT = Image.builder(Guithium.MOD_ID + ":tiled_dirt")
        .setTexture(Texture.DIRT)
        .setVertexColor(0xFF404040)
        .setTileModifier(32.0F)
        .build();

    private Texture texture;
    private Integer vertexColor;
    private Float tileModifier;

    /**
     * Creates a new image.
     *
     * @param key          Unique identifier for element
     * @param pos          Position of element
     * @param anchor       Anchor for element
     * @param offset       Offset of element
     * @param size         Size of element
     * @param texture      Texture of image
     * @param vertexColor  Vertex color modifier
     * @param tileModifier Tile modifier
     */
    protected Image(@NotNull Key key, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, @Nullable Point size, @NotNull Texture texture, @Nullable Integer vertexColor, @Nullable Float tileModifier) {
        super(key, Type.IMAGE, pos, anchor, offset, size);
        setTexture(texture);
        setVertexColor(vertexColor);
        setTileModifier(tileModifier);
    }

    /**
     * Get the image's texture.
     *
     * @return Image texture
     */
    @NotNull
    public Texture getTexture() {
        return this.texture;
    }

    /**
     * Set the image's texture.
     *
     * @param texture Image texture
     */
    public void setTexture(@NotNull Texture texture) {
        Preconditions.checkNotNull(texture, "Texture cannot be null");
        this.texture = texture;
    }

    /**
     * Get the vertex color modifier.
     * <p>
     * This is a color modifier used in mojang's `position_tex_color` fragment
     * shader to control how much of a texture's colors show through.
     * <p>
     * Eg, a modifier of `0xFF00FFFF` will remove all red from the texture.
     *
     * @return Vertex color modifier
     */
    @Nullable
    public Integer getVertexColor() {
        return this.vertexColor;
    }

    /**
     * Set the vertex color modifier.
     * <p>
     * This is a color modifier used in mojang's `position_tex_color` fragment
     * shader to control how much of a texture's colors show through.
     * <p>
     * Eg, a modifier of `0xFF00FFFF` will remove all red from the texture.
     *
     * @param color Vertex color modifier
     */
    public void setVertexColor(@Nullable Integer color) {
        this.vertexColor = color;
    }

    /**
     * Get the image's tile modifier.
     * <p>
     * This is used to divide the UV into tiled segments on each axis.
     * <p>
     * Eg, a value of 32 is used on vanilla option screens with the dirt texture
     * in order to get the tiled effect. This does <em>not</em> mean it is tiled 32 times.
     *
     * @return Tile modifier
     */
    @Nullable
    public Float getTileModifier() {
        return this.tileModifier;
    }

    /**
     * Set the image's tile modifier.
     * <p>
     * This is used to divide the UV into tiled segments on each axis.
     * <p>
     * Eg, a value of 32 is used on vanilla option screens with the dirt texture
     * in order to get the tiled effect. This does <em>not</em> mean it is tiled 32 times.
     *
     * @param tileModifier Tile modifier
     */
    public void setTileModifier(@Nullable Float tileModifier) {
        this.tileModifier = tileModifier;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("texture", getTexture());
        json.addProperty("vertexColor", getVertexColor());
        json.addProperty("tileMod", getTileModifier());
        return json.getJsonObject();
    }

    /**
     * Create a new image from Json.
     *
     * @param json Json representation of a image
     * @return A new image
     */
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
            !json.has("vertexColor") ? null : json.get("vertexColor").getAsInt(),
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
            && Objects.equals(getVertexColor(), other.getVertexColor())
            && Objects.equals(getTileModifier(), other.getTileModifier())
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTexture(), getVertexColor(), getTileModifier(), super.hashCode());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("Image{%s}", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",texture=" + getTexture()
            + ",vertexColor=" + getVertexColor()
            + ",tileMod=" + getTileModifier();
    }

    /**
     * Create a new image builder.
     *
     * @param key Unique identifying key for the image
     * @return New image builder
     */
    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    /**
     * Create a new image builder.
     *
     * @param key Unique identifying key for the image
     * @return New image builder
     */
    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    /**
     * Builder for images.
     */
    public static class Builder extends Rect.Builder<Builder> {
        private Texture texture = Texture.DIRT;
        private Integer vertexColor;
        private Float tileModifier;

        /**
         * Create a new image builder.
         *
         * @param key Unique identifying key for the image
         */
        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        /**
         * Create a new image builder.
         *
         * @param key Unique identifying key for the image
         */
        public Builder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get the image's texture.
         *
         * @return Image texture
         */
        @NotNull
        public Texture getTexture() {
            return this.texture;
        }

        /**
         * Set the image's texture.
         *
         * @param texture Image texture
         */
        @NotNull
        public Builder setTexture(@NotNull Texture texture) {
            Preconditions.checkNotNull(texture, "Texture cannot be null");
            this.texture = texture;
            return this;
        }

        /**
         * Get the vertex color modifier.
         * <p>
         * This is a color modifier used in mojang's `position_tex_color` fragment
         * shader to control how much of a texture's colors show through.
         * <p>
         * Eg, a modifier of `0xFF00FFFF` will remove all red from the texture.
         *
         * @return Vertex color modifier
         */
        @Nullable
        public Integer getVertexColor() {
            return this.vertexColor;
        }

        /**
         * Set the vertex color modifier.
         * <p>
         * This is a color modifier used in mojang's `position_tex_color` fragment
         * shader to control how much of a texture's colors show through.
         * <p>
         * Eg, a modifier of `0xFF00FFFF` will remove all red from the texture.
         *
         * @param color Vertex color modifier
         */
        @NotNull
        public Builder setVertexColor(@Nullable Integer color) {
            this.vertexColor = color;
            return this;
        }

        /**
         * Get the image's tile modifier.
         * <p>
         * This is used to divide the UV into tiled segments on each axis.
         * <p>
         * Eg, a value of 32 is used on vanilla option screens with the dirt texture
         * in order to get the tiled effect. This does <em>not</em> mean it is tiled 32 times.
         *
         * @return Tile modifier
         */
        @Nullable
        public Float getTileModifier() {
            return this.tileModifier;
        }

        /**
         * Set the image's tile modifier.
         * <p>
         * This is used to divide the UV into tiled segments on each axis.
         * <p>
         * Eg, a value of 32 is used on vanilla option screens with the dirt texture
         * in order to get the tiled effect. This does <em>not</em> mean it is tiled 32 times.
         *
         * @param tileModifier Tile modifier
         */
        @NotNull
        public Builder setTileModifier(@Nullable Float tileModifier) {
            this.tileModifier = tileModifier;
            return this;
        }

        /**
         * Build a new image from the current properties in this builder.
         *
         * @return New image
         */
        @Override
        @NotNull
        public Image build() {
            return new Image(getKey(), getPos(), getAnchor(), getOffset(), getSize(), getTexture(), getVertexColor(), getTileModifier());
        }
    }
}
