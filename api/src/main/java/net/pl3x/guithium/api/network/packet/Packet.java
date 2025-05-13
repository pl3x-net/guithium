package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.key.Keyed;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet of data to send to/from server/client.
 */
public abstract class Packet extends Keyed {
    /**
     * Create a new key identified object.
     *
     * @param key Unique identifier
     */
    public Packet(@NotNull Key key) {
        super(key);
    }

    /**
     * Handle this packet with specified listener
     *
     * @param listener Handling listener
     * @param <T>      Type of listener
     */
    public abstract <T extends PacketListener> void handle(@NotNull T listener);

    /**
     * Write packet to raw data byte array
     *
     * @return Raw data byte array
     */
    @NotNull
    public abstract ByteArrayDataOutput write();

    /**
     * Get outbound raw data byte array for specified packet.
     *
     * @param packet Packet to use
     * @return Outbound raw data byte array
     */
    @NotNull
    public static ByteArrayDataOutput out(@NotNull Packet packet) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeInt(Guithium.PROTOCOL);
        out.writeUTF(packet.getKey().toString());
        return out;
    }

    /**
     * Get inbound raw data byte array from raw data
     *
     * @param bytes Raw data
     * @return Inbound raw data byte array
     */
    @NotNull
    public static ByteArrayDataInput in(byte[] bytes) {
        return ByteStreams.newDataInput(bytes);
    }
}
