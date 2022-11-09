package net.pl3x.servergui.plugin.network.packet;

import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.ServerGUI;
import org.bukkit.entity.Player;

import java.util.Map;

public class TexturesPacket extends Packet {
    public static final String CHANNEL = "servergui:textures";

    public void send(Player player) {
        ByteArrayDataOutput out = out();
        Map<String, String> textures = ServerGUI.api().getTextureManager().get();
        System.out.println(textures);
        out.writeUTF(ServerGUI.api().gson().toJson(textures));
        send(player, CHANNEL, out);
    }
}
