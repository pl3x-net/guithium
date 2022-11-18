package net.pl3x.guithium.api.net;

import net.pl3x.guithium.api.net.packet.Packet;
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
