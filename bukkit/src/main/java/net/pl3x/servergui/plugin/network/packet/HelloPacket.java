package net.pl3x.servergui.plugin.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.plugin.event.HelloEvent;
import org.bukkit.entity.Player;

public class HelloPacket extends Packet {
    public static final String CHANNEL = "servergui:hello";

    public void send(Player player) {
        ByteArrayDataOutput out = out();
        out.writeInt(ServerGUI.PROTOCOL);
        send(player, CHANNEL, out);
    }

    public static void receive(String channel, Player player, byte[] bytes) {
        ByteArrayDataInput in = in(bytes);
        int protocol = in.readInt();

        System.out.println(player.getName() + " is using ServerGUI with protocol " + protocol);

        new HelloPacket().send(player);
        new TexturesPacket().send(player);

        ServerGUI.api().getGuiManager().getAll().forEach(gui ->
            new GuiPacket().send(player, gui)
        );

        new HelloEvent(player).callEvent();
    }
}
