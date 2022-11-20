package net.pl3x.guithium.api.network;

import net.pl3x.guithium.api.network.packet.Packet;
import org.jetbrains.annotations.NotNull;

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
}
