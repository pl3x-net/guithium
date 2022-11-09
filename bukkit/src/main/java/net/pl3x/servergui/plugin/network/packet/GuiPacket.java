package net.pl3x.servergui.plugin.network.packet;

import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.gui.Gui;
import org.bukkit.entity.Player;

public class GuiPacket extends Packet {
    public static final String CHANNEL = "servergui:gui";

    public void send(Player player, Gui gui) {
        String json = ServerGUI.api().gson().toJson(gui);

        ByteArrayDataOutput out = out();
        out.writeUTF("add");
        out.writeUTF(json);
        send(player, CHANNEL, out);
    }
}
