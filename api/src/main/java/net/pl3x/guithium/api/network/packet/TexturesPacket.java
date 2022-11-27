package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.reflect.TypeToken;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.texture.Texture;
import net.pl3x.guithium.api.json.Gson;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a textures packet containing a list of textures to preload.
 */
public class TexturesPacket extends Packet {
    /**
     * Unique identifying key
     */
    public static final Key KEY = Key.of("packet:textures");

    private static final Type TYPE_TOKEN = new TypeToken<Map<String, String>>() {
    }.getType();

    private final Map<Key, Texture> textures;
    private final Map<String, String> rawTex;

    /**
     * Create a new texture packet.
     *
     * @param textures Textures to preload
     */
    public TexturesPacket(@NotNull Map<Key, Texture> textures) {
        super(KEY);
        this.textures = textures;
        this.rawTex = new HashMap<>();
        textures.forEach((key, texture) -> this.rawTex.put(key.toString(), texture.getUrl()));
    }

    /**
     * Creates a new texture packet.
     *
     * @param in Input byte array
     */
    public TexturesPacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.textures = new HashMap<>();
        this.rawTex = Gson.fromJson(in.readUTF(), TYPE_TOKEN);
        if (this.rawTex != null) {
            this.rawTex.forEach((id, url) -> {
                Key key = Key.of(id);
                this.textures.put(key, Texture.of(key, url));
            });
        }
    }

    /**
     * Get the textures to preload.
     *
     * @return Texture to preload
     */
    @NotNull
    public Map<Key, Texture> getTextures() {
        return this.textures;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleTextures(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(Gson.toJson(this.rawTex));
        return out;
    }
}
