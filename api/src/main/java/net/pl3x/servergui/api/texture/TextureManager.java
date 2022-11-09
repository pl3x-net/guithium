package net.pl3x.servergui.api.texture;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {
    private final Map<String, String> textures = new HashMap<>();

    public void add(String id, String url) {
        this.textures.put(id, url);
    }

    public Map<String, String> get() {
        return this.textures;
    }

    public void remove(String id) {
        this.textures.remove(id);
    }
}
