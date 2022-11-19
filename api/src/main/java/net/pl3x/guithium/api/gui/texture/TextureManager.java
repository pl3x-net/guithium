package net.pl3x.guithium.api.gui.texture;

import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.net.packet.TexturesPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {
    private final Map<Key, Texture> textures = new HashMap<>();

    public void add(@NotNull Texture texture) {
        this.textures.put(texture.getKey(), texture);
    }

    @Nullable
    public Texture get(@NotNull String id) {
        return get(Key.of(id));
    }

    @Nullable
    public Texture get(@NotNull Key key) {
        return this.textures.get(key);
    }

    @Nullable
    public TexturesPacket getPacket() {
        if (this.textures.isEmpty()) {
            return null;
        }
        return new TexturesPacket(this.textures);
    }

    public void remove(@NotNull Key key) {
        this.textures.remove(key);
    }
}
