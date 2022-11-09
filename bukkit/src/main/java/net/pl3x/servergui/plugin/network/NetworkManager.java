package net.pl3x.servergui.plugin.network;

import net.pl3x.servergui.plugin.ServerGUIBukkit;
import net.pl3x.servergui.plugin.network.packet.GuiPacket;
import net.pl3x.servergui.plugin.network.packet.HelloPacket;
import net.pl3x.servergui.plugin.network.packet.TexturesPacket;
import org.bukkit.Bukkit;

public class NetworkManager {
    public static void register() {
        Bukkit.getMessenger().registerIncomingPluginChannel(ServerGUIBukkit.instance(), HelloPacket.CHANNEL, HelloPacket::receive);

        Bukkit.getMessenger().registerOutgoingPluginChannel(ServerGUIBukkit.instance(), GuiPacket.CHANNEL);
        Bukkit.getMessenger().registerOutgoingPluginChannel(ServerGUIBukkit.instance(), HelloPacket.CHANNEL);
        Bukkit.getMessenger().registerOutgoingPluginChannel(ServerGUIBukkit.instance(), TexturesPacket.CHANNEL);
    }
}
