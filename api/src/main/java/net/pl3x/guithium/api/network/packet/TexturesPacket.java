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

public class TexturesPacket extends Packet {
    public static final Key KEY = Key.of("packet:textures");

    private static final Type TYPE_TOKEN = new TypeToken<Map<String, String>>() {
    }.getType();

    private final Map<Key, Texture> textures;
    private final Map<String, String> rawTex;

    public TexturesPacket(@NotNull Map<Key, Texture> textures) {
        this.textures = textures;
        this.rawTex = new HashMap<>();
        textures.forEach((key, texture) -> this.rawTex.put(key.toString(), texture.getUrl()));
    }

    public TexturesPacket(@NotNull ByteArrayDataInput in) {
        this.textures = new HashMap<>();
        this.rawTex = Gson.fromJson(in.readUTF(), TYPE_TOKEN);
        if (this.rawTex != null) {
            this.rawTex.forEach((id, url) -> {
                Key key = Key.of(id);
                this.textures.put(key, Texture.of(key, url));
            });
        }
    }

    @Override
    @NotNull
    public Key getKey() {
        return KEY;
    }

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
