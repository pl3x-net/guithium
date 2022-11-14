package net.pl3x.servergui.fabric.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.pl3x.servergui.api.ServerGUI;

public class HelloPacket extends Packet {
    public static final Identifier CHANNEL = new Identifier(ServerGUI.MOD_ID, "hello");

    public void send() {
        ByteArrayDataOutput out = out();
        out.writeInt(ServerGUI.PROTOCOL);
        send(CHANNEL, out);
    }

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        ByteArrayDataInput in = in(buf.getWrittenBytes());
        int protocol = in.readInt();
        if (protocol == ServerGUI.PROTOCOL) {
            System.out.println("Server responded with correct protocol (" + protocol + ")");
        } else {
            System.out.println("Server responded with a different protocol (" + protocol + ")");
        }
    }
}
