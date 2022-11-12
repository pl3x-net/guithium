package net.pl3x.servergui.plugin.network.packet;

import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.json.Gson;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class ElementPacket extends Packet {
    public static final NamespacedKey CHANNEL = new NamespacedKey(ServerGUI.MOD_ID, "element");

    public static void send(Player player, Element element) {
        ByteArrayDataOutput out = out();
        out.writeUTF(Gson.toJson(element));
        send(player, CHANNEL, out);
    }
}
