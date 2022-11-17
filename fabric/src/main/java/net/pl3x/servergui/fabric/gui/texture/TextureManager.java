package net.pl3x.servergui.fabric.gui.texture;

import net.pl3x.servergui.api.Key;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TextureManager extends net.pl3x.servergui.api.gui.texture.TextureManager {
    private final Map<Key, Texture> textures = new HashMap<>();

    public void add(String id, String url) {
        Key key = Key.of(id);
        this.textures.put(key, new Texture(key, url));
    }

    public Texture get(Key key) {
        return this.textures.get(key);
    }

    public void remove(Key key) {
        Texture texture = this.textures.remove(key);
        if (texture != null) {
            texture.unload();
        }
    }

    public void clear() {
        new HashSet<>(this.textures.keySet()).forEach(this::remove);
    }
}
