package net.pl3x.guithium.fabric.gui.texture;

import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.element.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TextureManager extends net.pl3x.guithium.api.gui.texture.TextureManager {
    private final Map<Key, Texture> textures = new HashMap<>();

    public Texture getOrAdd(@NotNull Image image) {
        net.pl3x.guithium.api.gui.texture.Texture texture = image.getTexture();
        return this.textures.computeIfAbsent(texture.getKey(), k -> new Texture(texture.getKey(), texture.getUrl()));
    }

    public void add(@NotNull String id, @NotNull String url) {
        Key key = Key.of(id);
        this.textures.put(key, new Texture(key, url));
    }

    @Nullable
    public Texture get(@NotNull Key key) {
        return this.textures.get(key);
    }

    public void remove(@NotNull Key key) {
        Texture texture = this.textures.remove(key);
        if (texture != null) {
            texture.unload();
        }
    }

    public void clear() {
        new HashSet<>(this.textures.keySet()).forEach(this::remove);
    }
}
