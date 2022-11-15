package net.pl3x.servergui.plugin.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.player.Player;
import net.pl3x.servergui.plugin.ServerGUIBukkit;
import net.pl3x.servergui.plugin.event.HelloEvent;
import net.pl3x.servergui.plugin.player.BukkitPlayer;

public class HelloPacket extends Packet {
    public static final Key CHANNEL = Key.of(ServerGUI.MOD_ID + ":hello");

    public static void send(Player player) {
        ByteArrayDataOutput out = out();
        out.writeInt(ServerGUI.PROTOCOL);
        player.send(CHANNEL, out);
    }

    public static void receive(String channel, org.bukkit.entity.Player bukkit, byte[] bytes) {
        ByteArrayDataInput in = in(bytes);
        int protocol = in.readInt();

        if (protocol != ServerGUI.PROTOCOL) {
            System.out.println(bukkit.getName() + " is using ServerGUI with a different protocol (" + protocol + ")");
            return;
        }

        Player player = new BukkitPlayer(bukkit);
        ServerGUIBukkit.instance().getPlayerManager().add(player);

        System.out.println(player.getName() + " is using ServerGUI with correct protocol (" + protocol + ")");

        // respond to client
        HelloPacket.send(player);

        // tell client about textures
        TexturesPacket.send(player);

        // tell other plugins about this hello
        new HelloEvent(bukkit).callEvent();
    }
}
