package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet that players send when they join the server.
 * <p>
 * This packet is also used for the server to reply to the player.
 */
public class HelloPacket extends Packet {
    /**
     * Unique identifier for hello packets
     */
    public static final Key KEY = Key.of("packet:hello");

    private final int protocol;

    /**
     * Create new packet, ready to send to player.
     */
    public HelloPacket() {
        this(Guithium.PROTOCOL);
    }

    /**
     * Create new packet, captured from player.
     *
     * @param in Inbound raw data byte array
     */
    public HelloPacket(@NotNull ByteArrayDataInput in) {
        this(in.readInt());
    }

    private HelloPacket(int protocol) {
        super(KEY);
        this.protocol = protocol;
    }

    /**
     * Get the protocol stored in this packet.
     *
     * @return Protocol number
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
