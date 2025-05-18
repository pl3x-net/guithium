package net.pl3x.guithium.api.network;

import net.pl3x.guithium.api.network.packet.CloseScreenPacket;
import net.pl3x.guithium.api.network.packet.ElementPacket;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import net.pl3x.guithium.api.network.packet.OpenScreenPacket;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a listener of packets. Packets sent from the client to the server are handled here.
 */
public interface PacketListener {
    /**
     * Handle clock screen packet.
     *
     * @param packet Close screen packet to handle
     */
    void handleCloseScreen(@NotNull CloseScreenPacket packet);

    /**
     * Handle update element packet.
     *
     * @param packet Element packet to handle
     */
    void handleElement(@NotNull ElementPacket packet);

    /**
     * Handle the hello packet.
     *
     * @param packet Hello packet to handle
     */
    void handleHello(@NotNull HelloPacket packet);

    /**
     * Handle open screen packet.
     *
     * @param packet Open screen packet to handle
     */
    void handleOpenScreen(@NotNull OpenScreenPacket packet);
}
