package net.pl3x.servergui.api.net.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.reflect.TypeToken;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.json.Gson;
import net.pl3x.servergui.api.net.PacketListener;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Map;

public class TexturesPacket extends Packet {
    public static final Key KEY = Key.of("packet:textures");

    private static final Type TYPE_TOKEN = new TypeToken<Map<String, String>>() {
    }.getType();
    private final Map<String, String> textures;

    public TexturesPacket(Map<String, String> textures) {
        this.textures = textures;
    }

    public TexturesPacket(@NotNull ByteArrayDataInput in) {
        this.textures = Gson.fromJson(in.readUTF(), TYPE_TOKEN);
    }

    @Override
    public Key getKey() {
        return KEY;
    }

    public Map<String, String> getTextures() {
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
