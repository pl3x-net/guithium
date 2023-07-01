package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.Vec4;
import net.pl3x.guithium.api.gui.texture.Texture;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an image.
 */
public class Image extends Rect {
    private Texture texture;
    private Vec4 uv;
    private Integer vertexColor;
    private Float tileModifier;

    /**
     * Creates a new image.
     *
     * @param key          Unique identifier for image
     * @param pos          Position of image
     * @param anchor       Anchor for image
     * @param offset       Offset of image
     * @param rotation     Rotation in degrees
     * @param scale        Scale of element
     * @param size         Size of image
     * @param texture      Texture of image
     * @param uv           Texture UV
     * @param vertexColor  Vertex color modifier
     * @param tileModifier Tile modifier
     */
    protected Image(@NotNull Key key, @Nullable Vec2 pos, @Nullable Vec2 anchor, @Nullable Vec2 offset, @Nullable Float rotation, @Nullable Float scale, @Nullable Vec2 size, @NotNull Texture texture, @Nullable Vec4 uv, @Nullable Integer vertexColor, @Nullable Float tileModifier) {
        super(key, Type.IMAGE, pos, anchor, offset, rotation, scale, size);
        setTexture(texture);
        setUV(uv);
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
     * Get the texture UV.
     * <p>
     * If null, default UV of <code>0,0,1,1</code> will be used.
     *
     * @return Texture UV
     */
    @Nullable
    public Vec4 getUV() {
        return this.uv;
    }

    /**
     * Set the texture UV.
     * <p>
     * If null, default UV of <code>0,0,1,1</code> will be used.
     *
     * @param uv Texture UV
     */
    public void setUV(@Nullable Vec4 uv) {
        this.uv = uv;
    }

    /**
     * Get the vertex color modifier.
     * <p>
     * This is a color modifier used in mojang's `position_tex_color` fragment
     * shader to control how much of a texture's colors show through.
     * <p>
     * Eg, a modifier of `0xFF00FFFF` will remove all red from the texture.
     * <p>
     * If null, default vertex color of <code>0xFFFFFFFF</code> (opaque white) will be used.
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
     * <p>
     * If null, default vertex color of <code>0xFFFFFFFF</code> (opaque white) will be used.
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
     * <p>
     * If null, no tile modifier will be used. If not null and <code>{@link #getUV()}</code> is null,
     * then width and height of the image will replace the UV before applying the tile modifier.
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
     * <p>
     * If null, no tile modifier will be used. If not null and <code>{@link #getUV()}</code> is null,
     * then width and height of the image will replace the UV before applying the tile modifier.
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
        json.addProperty("uv", getUV());
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
                !json.has("pos") ? null : Vec2.fromJson(json.get("pos").getAsJsonObject()),
                !json.has("anchor") ? null : Vec2.fromJson(json.get("anchor").getAsJsonObject()),
                !json.has("offset") ? null : Vec2.fromJson(json.get("offset").getAsJsonObject()),
                !json.has("rotation") ? null : json.get("rotation").getAsFloat(),
                !json.has("scale") ? null : json.get("scale").getAsFloat(),
                !json.has("size") ? null : Vec2.fromJson(json.get("size").getAsJsonObject()),
                Texture.fromJson(json.get("texture").getAsJsonObject()),
                !json.has("uv") ? null : Vec4.fromJson(json.get("uv").getAsJsonObject()),
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
                && Objects.equals(getUV(), other.getUV())
                && Objects.equals(getVertexColor(), other.getVertexColor())
                && Objects.equals(getTileModifier(), other.getTileModifier())
                && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTexture(), getUV(), getVertexColor(), getTileModifier(), super.hashCode());
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
                + ",uv=" + getUV()
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
        private Vec4 uv;
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
         * @return This builder
         */
        @NotNull
        public Builder setTexture(@NotNull Texture texture) {
            Preconditions.checkNotNull(texture, "Texture cannot be null");
            this.texture = texture;
            return this;
        }

        /**
         * Get the texture UV.
         * <p>
         * If null, default UV of <code>0,0,1,1</code> will be used.
         *
         * @return Texture UV
         */
        @Nullable
        public Vec4 getUV() {
            return this.uv;
        }

        /**
         * Set the texture UV.
         * <p>
         * If null, default UV of <code>0,0,1,1</code> will be used.
         *
         * @param uv Texture UV
         * @return This builder
         */
        @NotNull
        public Builder setUV(@Nullable Vec4 uv) {
            this.uv = uv;
            return this;
        }

        /**
         * Get the vertex color modifier.
         * <p>
         * This is a color modifier used in mojang's `position_tex_color` fragment
         * shader to control how much of a texture's colors show through.
         * <p>
         * Eg, a modifier of `0xFF00FFFF` will remove all red from the texture.
         * <p>
         * If null, default vertex color of <code>0xFFFFFFFF</code> will be used.
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
         * <p>
         * If null, default vertex color of <code>0xFFFFFFFF</code> will be used.
         *
         * @param color Vertex color modifier
         * @return This builder
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
         * <p>
         * If null, no tile modifier will be used. If not null and <code>{@link #getUV()}</code> is null,
         * then width and height of the image will replace the UV before applying the tile modifier.
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
         * <p>
         * If null, no tile modifier will be used. If not null and <code>{@link #getUV()}</code> is null,
         * then width and height of the image will replace the UV before applying the tile modifier.
         *
         * @param tileModifier Tile modifier
         * @return This builder
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
            return new Image(getKey(), getPos(), getAnchor(), getOffset(), getRotation(), getScale(), getSize(), getTexture(), getUV(), getVertexColor(), getTileModifier());
        }
    }
}
