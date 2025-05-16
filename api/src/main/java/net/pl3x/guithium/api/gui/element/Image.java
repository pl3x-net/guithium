package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import java.util.Objects;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.Vec4;
import net.pl3x.guithium.api.gui.texture.Texture;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an image element.
 */
public class Image extends Rect<Image> {
    /**
     * The default tiled dirt background used on a lot of options type screens.
     */
    public static final Image TILED_DIRT_BACKGROUND = Image.of(Guithium.MOD_ID + ":tiled_dirt")
            .setTexture(Texture.DIRT)
            .setVertexColor(0xFF404040)
            .setTileModifier(32.0F);

    private Texture texture;
    private Vec4 uv;
    private Integer vertex;
    private Float tile;

    /**
     * Create a new image element.
     *
     * @param key Unique identifier
     */
    public Image(@NotNull String key) {
        this(Key.of(key));
    }

    /**
     * Create a new image element.
     *
     * @param key Unique identifier
     */
    public Image(@NotNull Key key) {
        super(key, Type.IMAGE);
    }

    /**
     * Create a new image element.
     *
     * @param key Unique identifier
     * @return New image element
     */
    @NotNull
    public static Image of(@NotNull String key) {
        return of(Key.of(key));
    }

    /**
     * Create a new image element.
     *
     * @param key Unique identifier
     * @return New image element
     */
    @NotNull
    public static Image of(@NotNull Key key) {
        return new Image(key);
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
     * @return This image
     */
    @NotNull
    public Image setTexture(@NotNull Texture texture) {
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
     * @return This image
     */
    @NotNull
    public Image setUV(@Nullable Vec4 uv) {
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
     * If null, default vertex color of <code>0xFFFFFFFF</code> (opaque white) will be used.
     *
     * @return Vertex color modifier
     */
    @Nullable
    public Integer getVertexColor() {
        return this.vertex;
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
     * @return This image
     */
    @NotNull
    public Image setVertexColor(@Nullable Integer color) {
        this.vertex = color;
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
        return this.tile;
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
     * @param tile Tile modifier
     * @return This image
     */
    @NotNull
    public Image setTileModifier(@Nullable Float tile) {
        this.tile = tile;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Image other = (Image) obj;
        return Objects.equals(getTexture(), other.getTexture())
                && Objects.equals(getUV(), other.getUV())
                && Objects.equals(getVertexColor(), other.getVertexColor())
                && Objects.equals(getTileModifier(), other.getTileModifier());
    }

    @Override
    public int hashCode() {
        // pacifies codefactor.io
        return super.hashCode();
    }
}
