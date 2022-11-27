package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the initial handshake/hello packet.
 */
public class HelloPacket extends Packet {
    /**
     * Unique identifying key
     */
    public static final Key KEY = Key.of("packet:hello");

    private final int protocol;

    /**
     * Creates a new hello packet.
     */
    public HelloPacket() {
        super(KEY);
        this.protocol = Guithium.PROTOCOL;
    }

    /**
     * Creates a new hello packet.
     *
     * @param in Input byte array
     */
    public HelloPacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.protocol = in.readInt();
    }

    /**
     * Get the protocol of the other end of the handshake.
     *
     * @return Other end's protocol
     */
    public int getProtocol() {
        return this.protocol;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleHello(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeInt(getProtocol());
        return out;
    }
}
