package net.pl3x.guithium.plugin.network;

import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.network.Connection;
import net.pl3x.guithium.api.network.NetworkHandler;
import net.pl3x.guithium.api.network.packet.Packet;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.plugin.GuithiumPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PaperConnection implements Connection {
    private final WrappedPlayer player;
    private final PaperPacketListener packetListener;

    public PaperConnection(@NotNull WrappedPlayer player) {
        this.player = player;
        this.packetListener = new PaperPacketListener(player);
    }

    @Override
    @NotNull
    public PaperPacketListener getPacketListener() {
        return this.packetListener;
    }

    @Override
    public void send(@NotNull Packet packet) {
        send(packet, false);
    }

    @Override
    public void send(@NotNull Packet packet, boolean force) {
        if (force || this.player.hasGuithium()) {
            this.player.<Player>unwrap().sendPluginMessage(
                    (GuithiumPlugin) Guithium.api(),
                    NetworkHandler.CHANNEL,
                    packet.write().toByteArray()
            );
        }
    }
}
