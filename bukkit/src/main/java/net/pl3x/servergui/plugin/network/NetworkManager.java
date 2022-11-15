package net.pl3x.servergui.plugin.network;

import com.google.common.base.Preconditions;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.player.Player;
import net.pl3x.servergui.plugin.ServerGUIBukkit;
import net.pl3x.servergui.plugin.network.packet.ElementPacket;
import net.pl3x.servergui.plugin.network.packet.HelloPacket;
import net.pl3x.servergui.plugin.network.packet.ScreenPacket;
import net.pl3x.servergui.plugin.network.packet.TexturesPacket;
import org.bukkit.Bukkit;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NetworkManager implements net.pl3x.servergui.api.network.NetworkManager {
    private final ServerGUIBukkit plugin;

    public NetworkManager(@NotNull ServerGUIBukkit plugin) {
        this.plugin = plugin;
    }

    public void register() {
        registerIn(ElementPacket.CHANNEL, ElementPacket::receive);
        registerIn(HelloPacket.CHANNEL, HelloPacket::receive);
        registerIn(ScreenPacket.CHANNEL, ScreenPacket::receive);

        registerOut(ElementPacket.CHANNEL);
        registerOut(HelloPacket.CHANNEL);
        registerOut(ScreenPacket.CHANNEL);
        registerOut(TexturesPacket.CHANNEL);
    }

    private void registerIn(@NotNull Key channel, @NotNull PluginMessageListener listener) {
        Bukkit.getMessenger().registerIncomingPluginChannel(this.plugin, channel.toString(), listener);
    }

    private void registerOut(@NotNull Key channel) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this.plugin, channel.toString());
    }

    @Override
    public void send(@NotNull Player player, @NotNull Element element) {
        Preconditions.checkNotNull(player, "Player cannot be null");
        ElementPacket.send(player, element);
    }

    @Override
    public void send(@NotNull Player player, @Nullable Screen screen) {
        Preconditions.checkNotNull(player, "Player cannot be null");
        ScreenPacket.send(player, screen);
    }
}
