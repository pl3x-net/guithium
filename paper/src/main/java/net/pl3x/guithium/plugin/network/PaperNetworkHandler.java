package net.pl3x.guithium.plugin.network;

import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.network.NetworkHandler;
import net.pl3x.guithium.api.network.packet.Packet;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.plugin.GuithiumPlugin;
import org.bukkit.plugin.messaging.Messenger;

public class PaperNetworkHandler extends NetworkHandler {
    private final GuithiumPlugin plugin;

    public PaperNetworkHandler(final GuithiumPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void registerListeners() {
        Messenger messenger = this.plugin.getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(this.plugin, CHANNEL.toString());
        messenger.registerIncomingPluginChannel(this.plugin, CHANNEL.toString(),
                (channel, sender, bytes) -> {
                    // verify player
                    WrappedPlayer player = this.plugin.getPlayerManager().get(sender.getUniqueId());
                    if (player == null) {
                        Guithium.logger.warn("Received packet from unknown player ({})", sender.getName());
                        return;
                    }

                    // receive data from player
                    receive(player.getConnection().getPacketListener(), Packet.in(bytes));
                }
        );
    }
}
