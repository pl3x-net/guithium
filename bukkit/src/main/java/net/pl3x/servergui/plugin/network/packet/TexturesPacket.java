package net.pl3x.servergui.plugin.network.packet;

import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.json.Gson;
import net.pl3x.servergui.api.player.Player;

import java.util.Map;

public class TexturesPacket extends Packet {
    public static final Key CHANNEL = Key.of(ServerGUI.MOD_ID + ":textures");

    public static void send(Player player) {
        Map<String, String> textures = ServerGUI.api().getTextureManager().get();
        if (textures.isEmpty()) {
            return;
        }

        ByteArrayDataOutput out = out();
        out.writeUTF(Gson.toJson(textures));
        player.send(CHANNEL, out);
    }
}
