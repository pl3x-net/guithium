package net.pl3x.guithium.api.network;

import net.pl3x.guithium.api.network.packet.HelloPacket;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a listener of packets. Packets sent from the client to the server are handled here.
 */
public interface PacketListener {
    /**
     * Handle the hello packet.
     *
     * @param packet Hello packet
     */
    void handleHello(@NotNull HelloPacket packet);
}
