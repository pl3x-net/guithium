package net.pl3x.servergui.api.gui.texture;

import net.pl3x.servergui.api.net.packet.TexturesPacket;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {
    private final Map<String, String> textures = new HashMap<>();

    public void add(String id, String url) {
        this.textures.put(id, url);
    }

    public TexturesPacket getPacket() {
        if (this.textures.isEmpty()) {
            return null;
        }
        return new TexturesPacket(this.textures);
    }

    public void remove(String id) {
        this.textures.remove(id);
    }
}
