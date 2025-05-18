package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.json.JsonSerializable;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an open screen packet.
 */
public class OpenScreenPacket extends Packet {
    /**
     * Unique identifying key
     */
    public static final Key KEY = Key.of("packet:open_screen");

    private final Screen screen;

    /**
     * Create an open screen packet.
     *
     * @param screen Screen to open
     */
    public OpenScreenPacket(@NotNull Screen screen) {
        super(KEY);
        this.screen = screen;
    }

    /**
     * Create an open screen packet.
     *
     * @param in Input byte array
     */
    public OpenScreenPacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.screen = JsonSerializable.fromJson(in.readUTF(), Screen.class);
    }

    /**
     * Get the screen to open
     *
     * @return Screen to open
     */
    @NotNull
    public Screen getScreen() {
        return this.screen;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleOpenScreen(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(getScreen().toJson());
        return out;
    }
}
