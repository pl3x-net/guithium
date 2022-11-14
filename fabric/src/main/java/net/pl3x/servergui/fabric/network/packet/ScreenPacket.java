package net.pl3x.servergui.fabric.network.packet;

import com.google.common.io.ByteArrayDataInput;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.json.Gson;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.screen.RenderableScreen;

public class ScreenPacket extends Packet {
    public static final Identifier CHANNEL = new Identifier(ServerGUI.MOD_ID, "screen");

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        ByteArrayDataInput in = in(buf.getWrittenBytes());
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
