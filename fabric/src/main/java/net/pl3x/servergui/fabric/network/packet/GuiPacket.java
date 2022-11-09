package net.pl3x.servergui.fabric.network.packet;

import com.google.common.io.ByteArrayDataInput;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.pl3x.servergui.api.gui.GuiManager;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.RenderableGui;

public class GuiPacket extends Packet {
    public static final Identifier CHANNEL = new Identifier(ServerGUIFabric.MOD_ID, "gui");

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        ByteArrayDataInput in = in(buf.getWrittenBytes());
        String action = in.readUTF();
        String payload = in.readUTF();

        System.out.println(action + ": " + payload);

        GuiManager guiManager = ServerGUIFabric.instance().getGuiManager();
        switch (action) {
            case "add" -> guiManager.add(ServerGUIFabric.instance().gson().fromJson(payload, RenderableGui.class));
            case "remove" -> guiManager.remove(payload);
        }
    }
}
