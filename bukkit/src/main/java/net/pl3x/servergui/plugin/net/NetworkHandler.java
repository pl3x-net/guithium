package net.pl3x.servergui.plugin.net;

import net.pl3x.servergui.api.net.packet.Packet;
import net.pl3x.servergui.api.player.Player;
import net.pl3x.servergui.plugin.ServerGUI;
import org.bukkit.Bukkit;

public class NetworkHandler extends net.pl3x.servergui.api.net.NetworkHandler {
    private final ServerGUI plugin;

    public NetworkHandler(ServerGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public void register() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this.plugin, CHANNEL.toString());
        Bukkit.getMessenger().registerIncomingPluginChannel(this.plugin, CHANNEL.toString(),
            (channel, sender, bytes) -> {
                // verify player
                Player player = this.plugin.getPlayerManager().get(sender.getUniqueId());
                if (player == null) {
                    System.out.printf("Received packet from unknown player (%s)%n", sender.getName());
                    return;
                }

                // receive data from player
                receive(
                    player.getConnection().getPacketListener(),
                    Packet.in(bytes)
                );
            }
        );
    }
}
