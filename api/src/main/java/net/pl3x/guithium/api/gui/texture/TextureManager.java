package net.pl3x.guithium.api.gui.texture;

import net.pl3x.guithium.api.net.packet.TexturesPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {
    private final Map<String, String> textures = new HashMap<>();

    public void add(@NotNull String id, @NotNull String url) {
        this.textures.put(id, url);
    }

    @Nullable
    public TexturesPacket getPacket() {
        if (this.textures.isEmpty()) {
            return null;
        }
        return new TexturesPacket(this.textures);
    }

    public void remove(@NotNull String id) {
        this.textures.remove(id);
    }
}
