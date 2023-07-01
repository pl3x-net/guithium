package net.pl3x.guithium.api.gui.texture;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.pl3x.guithium.api.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Texture manager that manages textures. Hmm.
 * <p>
 * Textures added here will be preloaded into memory for later use.
 */
public class TextureManager {
    private final Map<Key, Texture> textures = new HashMap<>();

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
     * Get a texture.
     *
     * @param id Unique id for texture
     * @return Texture if it exists, otherwise null
     */
    @Nullable
    public Texture get(@NotNull String id) {
        return get(Key.of(id));
    }

    /**
     * Get a texture.
     *
     * @param key Unique id for texture
     * @return Texture if it exists, otherwise null
     */
    @Nullable
    public Texture get(@NotNull Key key) {
        return this.textures.get(key);
    }

    /**
     * Remove a texture.
     *
     * @param key Unique id for texture to remove
     */
    public void remove(@NotNull Key key) {
        this.textures.remove(key);
    }

    /**
     * Get an unmodifiable view of the stored textures.
     *
     * @return Unmodifiable view of stored textures
     */
    @NotNull
    public Map<Key, Texture> getTextures() {
        return Collections.unmodifiableMap(this.textures);
    }
}
