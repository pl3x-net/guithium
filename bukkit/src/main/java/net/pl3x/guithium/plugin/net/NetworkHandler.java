package net.pl3x.guithium.plugin.net;

import net.pl3x.guithium.api.net.packet.Packet;
import net.pl3x.guithium.api.player.Player;
import net.pl3x.guithium.plugin.Guithium;
import org.bukkit.Bukkit;

public class NetworkHandler extends net.pl3x.guithium.api.net.NetworkHandler {
    private final Guithium plugin;

    public NetworkHandler(Guithium plugin) {
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
