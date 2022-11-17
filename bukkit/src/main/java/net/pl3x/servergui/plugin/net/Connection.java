package net.pl3x.servergui.plugin.net;

import net.pl3x.servergui.api.net.NetworkHandler;
import net.pl3x.servergui.api.net.packet.Packet;
import net.pl3x.servergui.plugin.ServerGUI;
import net.pl3x.servergui.plugin.player.Player;
import org.jetbrains.annotations.NotNull;

public class Connection implements net.pl3x.servergui.api.net.Connection {
    private final Player player;
    private final PacketListener packetListener;

    public Connection(@NotNull Player player) {
        this.player = player;
        this.packetListener = new PacketListener(player);
    }

    @Override
    @NotNull
    public PacketListener getPacketListener() {
        return this.packetListener;
    }

    @Override
    public void send(@NotNull Packet packet) {
        this.player.getBukkitPlayer().sendPluginMessage(
            ServerGUI.instance(),
            NetworkHandler.CHANNEL.toString(),
            packet.write().toByteArray()
        );
    }
}
