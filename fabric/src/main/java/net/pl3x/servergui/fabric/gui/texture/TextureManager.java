package net.pl3x.servergui.fabric.gui.texture;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TextureManager {
    private final Map<String, Texture> textures = new HashMap<>();

    public void add(String id, String url) {
        this.textures.put(id, new Texture(id, url));
    }

    public Texture get(String id) {
        return this.textures.get(id);
    }

    public void remove(String id) {
        Texture texture = this.textures.remove(id);
        if (texture != null) {
            texture.unload();
        }
    }

    public void clear() {
        new HashSet<>(this.textures.keySet()).forEach(this::remove);
    }
}
