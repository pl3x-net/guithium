package net.pl3x.servergui.fabric.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.json.Gson;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.element.RenderableElement;
import net.pl3x.servergui.fabric.gui.screen.RenderableScreen;

public class ElementPacket extends Packet {
    public static final Identifier CHANNEL = new Identifier(ServerGUI.MOD_ID, "element");

    public static void send(String action, String payload) {
        ByteArrayDataOutput out = out();
        out.writeUTF(action);
        out.writeUTF(payload);
        send(CHANNEL, out);
    }

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        ByteArrayDataInput in = in(buf.getWrittenBytes());
        String payload = in.readUTF();

        Element element = Gson.fromJson(payload, Element.class);

        if (ServerGUIFabric.client.currentScreen instanceof RenderableScreen currentScreen) {
            RenderableElement renderableElement = currentScreen.getElements().get(element.getKey());
            if (renderableElement != null) {
                renderableElement.setElement(element);
                return;
            }
        }

        for (RenderableScreen renderableScreen : ServerGUIFabric.instance().getScreenManager().getAll().values()) {
            RenderableElement renderableElement = renderableScreen.getElements().get(element.getKey());
            if (renderableElement != null) {
                renderableElement.setElement(element);
            }
        }
    }
}
