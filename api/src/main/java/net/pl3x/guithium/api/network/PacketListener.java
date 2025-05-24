package net.pl3x.guithium.api.network;

import net.pl3x.guithium.api.gui.element.ClickableElement;
import net.pl3x.guithium.api.gui.element.ValueElement;
import net.pl3x.guithium.api.network.packet.CloseScreenPacket;
import net.pl3x.guithium.api.network.packet.ElementChangedValuePacket;
import net.pl3x.guithium.api.network.packet.ElementClickedPacket;
import net.pl3x.guithium.api.network.packet.ElementPacket;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import net.pl3x.guithium.api.network.packet.OpenScreenPacket;
import net.pl3x.guithium.api.network.packet.TexturesPacket;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a listener of packets. Packets sent from the client to the server are handled here.
 */
public interface PacketListener {
    /**
     * Handle element click packet.
     *
     * @param packet Element click packet to handle
     * @param <T>    Type of element
     */
    <T extends ClickableElement<T>> void handleElementClick(@NotNull ElementClickedPacket<T> packet);

    /**
     * Handle close screen packet.
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

    /**
     * Handle element changed value packet.
     *
     * @param packet Element changed value packet to handle
     * @param <T>    Type of element
     * @param <V>    Type of value
     */
    <T extends ValueElement<T, V>, V> void handleElementChangedValue(@NotNull ElementChangedValuePacket<V> packet);

    /**
     * Handle texture preload packet.
     *
     * @param packet Texture preload packet to handle
     */
    void handleTextures(@NotNull TexturesPacket packet);
}
