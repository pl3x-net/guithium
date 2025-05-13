package net.pl3x.guithium.plugin.network;

import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.network.NetworkHandler;
import net.pl3x.guithium.api.network.packet.Packet;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.plugin.GuithiumPlugin;
import org.bukkit.plugin.messaging.Messenger;

public class PaperNetworkHandler extends NetworkHandler {
    @Override
    public void registerListeners() {
        GuithiumPlugin plugin = (GuithiumPlugin) Guithium.api();
        Messenger messenger = plugin.getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(plugin, CHANNEL);
        messenger.registerIncomingPluginChannel(plugin, CHANNEL,
                (channel, sender, bytes) -> {
                    // verify player
                    WrappedPlayer player = plugin.getPlayerManager().get(sender.getUniqueId());
                    if (player == null) {
                        Guithium.logger.warn("Received packet from unknown player ({})", sender.getName());
                        return;
                    }

                    // receive data from player
                    receive(player.getConnection(), Packet.in(bytes));
                }
        );
    }
}
