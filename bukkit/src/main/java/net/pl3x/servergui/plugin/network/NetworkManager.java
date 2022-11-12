package net.pl3x.servergui.plugin.network;

import net.pl3x.servergui.plugin.ServerGUIBukkit;
import net.pl3x.servergui.plugin.network.packet.ElementPacket;
import net.pl3x.servergui.plugin.network.packet.HelloPacket;
import net.pl3x.servergui.plugin.network.packet.TexturesPacket;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class NetworkManager {
    public static void register() {
        registerIn(HelloPacket.CHANNEL, HelloPacket::receive);

        registerOut(ElementPacket.CHANNEL);
        registerOut(HelloPacket.CHANNEL);
        registerOut(TexturesPacket.CHANNEL);
    }

    private static void registerIn(NamespacedKey channel, PluginMessageListener listener) {
        Bukkit.getMessenger().registerIncomingPluginChannel(ServerGUIBukkit.instance(), channel.asString(), listener);
    }

    private static void registerOut(NamespacedKey channel) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(ServerGUIBukkit.instance(), channel.asString());
    }
}
