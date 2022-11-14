package net.pl3x.servergui.plugin.network.packet;

import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.json.Gson;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class ScreenPacket extends Packet {
    public static final NamespacedKey CHANNEL = new NamespacedKey(ServerGUI.MOD_ID, "screen");

    public static void send(Player player, Screen screen) {
        ByteArrayDataOutput out = out();
        out.writeUTF(Gson.toJson(screen));
        send(player, CHANNEL, out);
    }
}
