package net.pl3x.servergui.fabric.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.servergui.api.ServerGUI;

public class HelloPacket extends Packet {
    public static final ResourceLocation CHANNEL = new ResourceLocation(ServerGUI.MOD_ID, "hello");

    public static void send() {
        ByteArrayDataOutput out = out();
        out.writeInt(ServerGUI.PROTOCOL);
        send(CHANNEL, out);
    }

    public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender sender) {
        ByteArrayDataInput in = in(buf.accessByteBufWithCorrectSize());
        int protocol = in.readInt();
        if (protocol == ServerGUI.PROTOCOL) {
            System.out.println("Server responded with correct protocol (" + protocol + ")");
        } else {
            System.out.println("Server responded with a different protocol (" + protocol + ")");
        }
    }
}
