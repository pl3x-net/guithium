package net.pl3x.servergui.plugin.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.pl3x.servergui.plugin.ServerGUIBukkit;
import org.bukkit.entity.Player;

public abstract class Packet {
    protected void send(Player player, String channel, ByteArrayDataOutput out) {
        player.sendPluginMessage(ServerGUIBukkit.instance(), channel, out.toByteArray());
    }

    @SuppressWarnings("UnstableApiUsage")
    protected static ByteArrayDataOutput out() {
        return ByteStreams.newDataOutput();
    }

    @SuppressWarnings("UnstableApiUsage")
    protected static ByteArrayDataInput in(byte[] bytes) {
        return ByteStreams.newDataInput(bytes);
    }
}
