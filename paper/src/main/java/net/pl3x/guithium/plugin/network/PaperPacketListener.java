package net.pl3x.guithium.plugin.network;

import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.network.PacketListener;
import net.pl3x.guithium.api.network.packet.CloseScreenPacket;
import net.pl3x.guithium.api.network.packet.ElementPacket;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import net.pl3x.guithium.api.network.packet.OpenScreenPacket;
import net.pl3x.guithium.plugin.player.PaperPlayer;
import org.jetbrains.annotations.NotNull;

public class PaperPacketListener implements PacketListener {
    private final PaperPlayer player;

    public PaperPacketListener(@NotNull PaperPlayer player) {
        this.player = player;
    }

    @Override
    public void handleCloseScreen(@NotNull CloseScreenPacket packet) {
        Screen screen = this.player.getCurrentScreen();
        if (screen == null || !screen.getKey().equals(packet.getScreenKey())) {
            return;
        }
        this.player.setCurrentScreen(null);
    }

    @Override
    public void handleElement(@NotNull ElementPacket packet) {
        // client does not send this packet to the server
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleHello(@NotNull HelloPacket packet) {
        int protocol = packet.getProtocol();

        Guithium.logger.info("{} is using Guithium with protocol {}", this.player.getName(), protocol);

        // set the player's client protocol
        this.player.setProtocol(protocol);

        // reply to the player with server's protocol
        this.player.getConnection().send(new HelloPacket(), true);
    }

    @Override
    public void handleOpenScreen(@NotNull OpenScreenPacket packet) {
        // client does not send this packet to the server
        throw new UnsupportedOperationException("Not supported.");
    }
}
