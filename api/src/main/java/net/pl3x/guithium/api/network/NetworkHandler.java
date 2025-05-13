package net.pl3x.guithium.api.network;

import com.google.common.io.ByteArrayDataInput;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import net.pl3x.guithium.api.network.packet.Packet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a network handler. All packets are registered here.
 */
public abstract class NetworkHandler {
    /**
     * A unique identifier for the network channel between client and server.
     */
    public final static Key CHANNEL = Key.of(Guithium.MOD_ID + ":" + Guithium.MOD_ID);

    private final Map<Key, Function<ByteArrayDataInput, ? extends Packet>> packets = new HashMap<>();

    /**
     * Create a new network handler instance
     */
    public NetworkHandler() {
        registerListener(HelloPacket.KEY, HelloPacket::new);
    }

    /**
     * Register the network listeners
     */
    public abstract void registerListeners();

    /**
     * Register incoming packet handlers.
     *
     * @param key      Identifying key
     * @param function Packet handler
     */
    protected void registerListener(@NotNull Key key, @NotNull Function<ByteArrayDataInput, ? extends Packet> function) {
        this.packets.put(key, function);
    }

    /**
     * Receive data.
     *
     * @param listener Packet listener
     * @param in       Data received
     */
    public void receive(@NotNull PacketListener listener, @NotNull ByteArrayDataInput in) {
        Packet packet = getPacket(in);
        if (packet != null) {
            packet.handle(listener);
        }
    }

    /**
     * Get the packet from the raw input data.
     *
     * @param in Raw input data
     * @return The packet for the data, or null if not recognized
     */
    @Nullable
    protected Packet getPacket(@NotNull ByteArrayDataInput in) {
        // verify protocol
        int protocol = in.readInt();
        if (protocol != Guithium.PROTOCOL) {
            Guithium.logger.warn("Received packet with invalid protocol ({}) from server", protocol);
            return null;
        }

        // get registered packet handler
        Key packetId = Key.of(in.readUTF());
        Function<ByteArrayDataInput, ? extends Packet> function = this.packets.get(packetId);
        if (function == null) {
            Guithium.logger.warn("Received unknown packet ({}) from player", packetId);
            return null;
        }
        return function.apply(in);
    }
}
