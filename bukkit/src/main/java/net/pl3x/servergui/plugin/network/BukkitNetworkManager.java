package net.pl3x.servergui.plugin.network;

import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.plugin.ServerGUIBukkit;
import net.pl3x.servergui.plugin.network.packet.ElementPacket;
import net.pl3x.servergui.plugin.network.packet.HelloPacket;
import net.pl3x.servergui.plugin.network.packet.TexturesPacket;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.UUID;

public class BukkitNetworkManager implements net.pl3x.servergui.api.network.NetworkManager {
    public void register() {
        registerIn(HelloPacket.CHANNEL, HelloPacket::receive);

        registerOut(ElementPacket.CHANNEL);
        registerOut(HelloPacket.CHANNEL);
        registerOut(TexturesPacket.CHANNEL);
    }

    private void registerIn(NamespacedKey channel, PluginMessageListener listener) {
        Bukkit.getMessenger().registerIncomingPluginChannel(ServerGUIBukkit.instance(), channel.asString(), listener);
    }

    private void registerOut(NamespacedKey channel) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(ServerGUIBukkit.instance(), channel.asString());
    }

    @Override
    public void send(UUID uuid, Element element) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            ElementPacket.send(player, element);
        }
    }
}
