package net.pl3x.servergui.fabric.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.json.Gson;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.screen.AbstractScreen;
import net.pl3x.servergui.fabric.gui.screen.RenderableScreen;

public class ScreenPacket extends Packet {
    public static final ResourceLocation CHANNEL = new ResourceLocation(ServerGUI.MOD_ID, "screen");

    public static void send(Key screen) {
        ByteArrayDataOutput out = out();
        if (screen != null) {
            out.writeUTF("closed");
            out.writeUTF(screen.toString());
        }
        send(CHANNEL, out);
    }

    public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender sender) {
        ByteArrayDataInput in = in(buf.accessByteBufWithCorrectSize());

        String action = in.readUTF();

        if (action.equals("close")) {
            if (ServerGUIFabric.client.screen instanceof AbstractScreen screen) {
                screen.onClose();
            }
            return;
        }

        String payload = in.readUTF();

        Screen screen = Gson.fromJson(payload, Screen.class);
        if (screen == null) {
            return;
        }

        RenderableScreen renderableScreen = new RenderableScreen(screen);

        if (screen.getType() == Screen.Type.HUD) {
            ServerGUIFabric.instance().getScreenManager().add(renderableScreen);
        } else {
            renderableScreen.open();
        }
    }
}
