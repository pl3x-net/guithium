package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.Keyed;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Guithium packet.
 */
public abstract class Packet extends Keyed {
    /**
     * Create a new packet
     *
     * @param key Unique identifying key for packet
     */
    public Packet(@NotNull Key key) {
        super(key);
    }

    /**
     * Handle this packet to the appropriate packet listener.
     *
     * @param listener Appropriate packet listener
     * @param <T>      Type of packet listener
     */
    public abstract <T extends PacketListener> void handle(@NotNull T listener);

    /**
     * Serialize this packet into an output byte array.
     *
     * @return Output byte array
     */
    @NotNull
    public abstract ByteArrayDataOutput write();

    /**
     * Create the standard output byte array with Guithium's protocol for the specified packet.
     *
     * @param packet Packet to create output byte array for
     * @return Output byte array
     */
    @NotNull
    @SuppressWarnings("UnstableApiUsage")
    public static ByteArrayDataOutput out(@NotNull Packet packet) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeInt(Guithium.PROTOCOL);
        out.writeUTF(packet.getKey().toString());
        return out;
    }

    /**
     * Create a standard input byte array from a raw array of bytes.
     *
     * @param bytes Raw byte array
     * @return Input byte array
     */
    @NotNull
    @SuppressWarnings("UnstableApiUsage")
    public static ByteArrayDataInput in(byte[] bytes) {
        return ByteStreams.newDataInput(bytes);
    }
}
