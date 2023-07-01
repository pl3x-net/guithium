package net.pl3x.guithium.plugin.network;

import net.pl3x.guithium.api.network.Connection;
import net.pl3x.guithium.api.network.NetworkHandler;
import net.pl3x.guithium.api.network.PacketListener;
import net.pl3x.guithium.api.network.packet.Packet;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.plugin.Guithium;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BukkitConnection implements Connection {
    private final WrappedPlayer player;
    private final PacketListener packetListener;

    public BukkitConnection(@NotNull WrappedPlayer player) {
        this.player = player;
        this.packetListener = new BukkitPacketListener(player);
    }

    @Override
    @NotNull
    public PacketListener getPacketListener() {
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
                    Guithium.instance(),
                    NetworkHandler.CHANNEL.toString(),
                    packet.write().toByteArray()
            );
        }
    }
}
