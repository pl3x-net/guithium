package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import net.pl3x.guithium.api.gui.texture.Texture;
import net.pl3x.guithium.api.json.Gson;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a textures packet containing a list of textures to preload.
 */
public class TexturesPacket extends Packet {
    /**
     * Unique identifying key
     */
    public static final Key KEY = Key.of("packet:textures");

    private static final Type TYPE_TOKEN = new TypeToken<Collection<Texture>>() {
    }.getType();

    private final Collection<Texture> textures;

    /**
     * Create a new texture packet.
     *
     * @param textures Textures to preload
     */
    public TexturesPacket(@NotNull Collection<Texture> textures) {
        super(KEY);
        this.textures = textures;
    }

    /**
     * Create a new texture packet.
     *
     * @param in Input byte array
     */
    public TexturesPacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.textures = Gson.fromJson(in.readUTF(), TYPE_TOKEN);
    }

    /**
     * Get the textures to preload.
     *
     * @return Texture to preload
     */
    @NotNull
    public Collection<Texture> getTextures() {
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
        out.writeUTF(Gson.toJson(getTextures()));
        return out;
    }
}
