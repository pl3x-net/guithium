package net.pl3x.guithium.api.network;

import net.pl3x.guithium.api.network.packet.Packet;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a network connection.
 */
public interface Connection {
    /**
     * Get the packet listener.
     *
     * @return Packet listener
     */
    @NotNull
    PacketListener getPacketListener();

    /**
     * Send packet.
     *
     * @param packet Packet to send
     */
    void send(@NotNull Packet packet);

    /**
     * Send packet.
     *
     * @param packet Packet to send
     * @param force  Ignore protocol checks
     */
    void send(@NotNull Packet packet, boolean force);
}
