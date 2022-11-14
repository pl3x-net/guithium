package net.pl3x.servergui.plugin.network;

import com.google.common.base.Preconditions;
import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.plugin.ServerGUIBukkit;
import net.pl3x.servergui.plugin.network.packet.ElementPacket;
import net.pl3x.servergui.plugin.network.packet.HelloPacket;
import net.pl3x.servergui.plugin.network.packet.ScreenPacket;
import net.pl3x.servergui.plugin.network.packet.TexturesPacket;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class NetworkManager implements net.pl3x.servergui.api.network.NetworkManager {
    private final ServerGUIBukkit plugin;

    public NetworkManager(ServerGUIBukkit plugin) {
        this.plugin = plugin;
    }

    public void register() {
        registerIn(HelloPacket.CHANNEL, HelloPacket::receive);

        registerOut(ElementPacket.CHANNEL);
        registerOut(HelloPacket.CHANNEL);
        registerOut(ScreenPacket.CHANNEL);
        registerOut(TexturesPacket.CHANNEL);
    }

    private void registerIn(@NotNull NamespacedKey channel, @NotNull PluginMessageListener listener) {
        Bukkit.getMessenger().registerIncomingPluginChannel(this.plugin, channel.asString(), listener);
    }

    private void registerOut(@NotNull NamespacedKey channel) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this.plugin, channel.asString());
    }

    @Override
    public void send(@NotNull UUID uuid, Element element) {
        Preconditions.checkNotNull(uuid, "Uuid cannot be null");
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            ElementPacket.send(player, element);
        }
    }

    @Override
    public void send(@NotNull UUID uuid, Screen screen) {
        Preconditions.checkNotNull(uuid, "Uuid cannot be null");
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            ScreenPacket.send(player, screen);
        }
    }
}
