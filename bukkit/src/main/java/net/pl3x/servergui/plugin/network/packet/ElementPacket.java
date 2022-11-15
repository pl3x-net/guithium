package net.pl3x.servergui.plugin.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.gui.element.Button;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.json.Gson;
import net.pl3x.servergui.api.player.Player;
import net.pl3x.servergui.plugin.ServerGUIBukkit;

public class ElementPacket extends Packet {
    public static final Key CHANNEL = Key.of(ServerGUI.MOD_ID + ":element");

    public static void send(Player player, Element element) {
        ByteArrayDataOutput out = out();
        out.writeUTF(Gson.toJson(element));
        player.send(CHANNEL, out);
    }

    public static void receive(String channel, org.bukkit.entity.Player bukkit, byte[] bytes) {
        ByteArrayDataInput in = in(bytes);
        String action = in.readUTF();
        String payload = in.readUTF();

        Player player = ServerGUIBukkit.instance().getPlayerManager().get(bukkit.getUniqueId());

        switch (action) {
            case "button_click":
                System.out.println("Button clicked: " + payload);
                Screen current = player.getCurrentScreen();
                if (current != null) {
                    Element element = current.getElements().get(Key.of(payload));
                    if (element instanceof Button button) {
                        Button.TriConsumer<Screen, Button, Player> onClick = button.onClick();
                        if (onClick != null) {
                            onClick.accept(current, button, player);
                        }
                    }
                }
                //ScreenPacket.send(player, null);
                break;
        }
    }
}
