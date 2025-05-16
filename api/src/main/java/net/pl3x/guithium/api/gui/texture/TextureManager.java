package net.pl3x.guithium.api.gui.texture;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A texture manager that manages textures to store textures that need to be managed.
 * <p>
 * Textures added here will be preloaded into memory for later use.
 */
public class TextureManager {
    private final Map<Key, Texture> textures = new HashMap<>();

    /**
     * Create a new texture manager.
     */
    public TextureManager() {
        // Empty constructor to pacify javadoc lint
    }

    /**
     * Add a texture.
     * <p>
     * If the texture is from the internet, it will be preloaded into memory on the next render thread tick.
     *
     * @param texture Texture to add
     */
    public void add(@NotNull Texture texture) {
        this.textures.put(texture.getKey(), texture);
    }

    /**
     * Get a texture by string.
     *
     * @param key Unique identifier for texture
     * @return A texture with specified string if it exists, otherwise {@code null}
     */
    @Nullable
    public Texture get(@NotNull String key) {
        return get(Key.of(key));
    }

    /**
     * Get a texture by key.
     *
     * @param key Unique identifier of texture
     * @return A texture with specified key if it exists, otherwise {@code null}
     */
    @Nullable
    public Texture get(@NotNull Key key) {
        return this.textures.get(key);
    }

    /**
     * Remove a texture by string.
     *
     * @param key Unique identifier of texture to remove
     */
    public void remove(@NotNull String key) {
        remove(Key.of(key));
    }

    /**
     * Remove a texture by key.
     *
     * @param key Unique identifier of texture to remove
     */
    public void remove(@NotNull Key key) {
        this.textures.remove(key);
    }

    /**
     * Get an <a href="Collection.html#unmodview">unmodifiable view</a> of the stored textures map.
     *
     * @return An unmodifiable view of stored textures map
     */
    @NotNull
    public Map<Key, Texture> getTextures() {
        return Collections.unmodifiableMap(this.textures);
    }
}
