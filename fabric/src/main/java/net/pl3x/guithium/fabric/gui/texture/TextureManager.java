package net.pl3x.guithium.fabric.gui.texture;

import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.element.Image;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TextureManager extends net.pl3x.guithium.api.gui.texture.TextureManager {
    private final Map<Key, Texture> textures = new HashMap<>();

    public Texture getOrCreate(@NotNull Image image) {
        Texture texture = this.textures.get(image.getTexture().getKey());
        if (texture != null) {
            return texture;
        }
        return new Texture(image.getTexture().getKey(), image.getTexture().getUrl());
    }

    public void add(@NotNull Key key, @NotNull String url) {
        this.textures.put(key, new Texture(key, url));
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
