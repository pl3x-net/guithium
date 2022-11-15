package net.pl3x.servergui.plugin.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.json.Gson;
import net.pl3x.servergui.api.player.Player;
import net.pl3x.servergui.plugin.ServerGUIBukkit;

public class ScreenPacket extends Packet {
    public static final Key CHANNEL = Key.of(ServerGUI.MOD_ID + ":screen");

    public static void send(Player player, Screen screen) {
        ByteArrayDataOutput out = out();
        if (screen == null) {
            out.writeUTF("close");
            player.setCurrentScreen(null);
        } else {
            out.writeUTF("open");
            out.writeUTF(Gson.toJson(screen));
            if (screen.getType() != Screen.Type.HUD) {
                player.setCurrentScreen(screen);
            }
        }
        player.send(CHANNEL, out);
    }

    public static void receive(String channel, org.bukkit.entity.Player bukkit, byte[] bytes) {
        ByteArrayDataInput in = in(bytes);
        String action = in.readUTF();
        String payload = in.readUTF();

        Player player = ServerGUIBukkit.instance().getPlayerManager().get(bukkit.getUniqueId());

        switch (action) {
            case "closed":
                System.out.println("Screen closed: " + payload);
                Thread.dumpStack();
                player.setCurrentScreen(null);
                break;
        }
    }
}
