package net.pl3x.guithium.plugin.network;

import net.pl3x.guithium.api.network.NetworkHandler;
import net.pl3x.guithium.api.network.packet.Packet;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.plugin.Guithium;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class BukkitNetworkHandler extends NetworkHandler {
    private final Guithium plugin;

    public BukkitNetworkHandler(@NotNull Guithium plugin) {
        this.plugin = plugin;
    }

    @Override
    public void register() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this.plugin, CHANNEL.toString());
        Bukkit.getMessenger().registerIncomingPluginChannel(this.plugin, CHANNEL.toString(),
            (channel, sender, bytes) -> {
                // verify player
                WrappedPlayer player = this.plugin.getPlayerManager().get(sender.getUniqueId());
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
