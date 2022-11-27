package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a close screen packet.
 */
public class CloseScreenPacket extends Packet {
    /**
     * Unique identifying key
     */
    public static final Key KEY = Key.of("packet:close_screen");

    private final Key screenKey;

    /**
     * Creates a new close screen packet.
     *
     * @param screenKey Unique identifying key for the screen to close
     */
    public CloseScreenPacket(@NotNull Key screenKey) {
        super(KEY);
        this.screenKey = screenKey;
    }

    /**
     * Creates a new close screen packet.
     *
     * @param in Input byte array
     */
    public CloseScreenPacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.screenKey = Key.of(in.readUTF());
    }

    /**
     * Get the unique identifying key for the screen to close.
     *
     * @return Screen's key
     */
    @NotNull
    public Key getScreenKey() {
        return this.screenKey;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleCloseScreen(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(getScreenKey().toString());
        return out;
    }
}
