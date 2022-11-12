package net.pl3x.servergui.plugin.network.packet;

import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.json.Gson;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.Map;

public class TexturesPacket extends Packet {
    public static final NamespacedKey CHANNEL = new NamespacedKey(ServerGUI.MOD_ID, "textures");

    public static void send(Player player) {
        Map<String, String> textures = ServerGUI.api().getTextureManager().get();
        if (textures.isEmpty()) {
            return;
        }

        ByteArrayDataOutput out = out();
        out.writeUTF(Gson.toJson(textures));
        send(player, CHANNEL, out);
    }
}
