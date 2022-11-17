package net.pl3x.servergui.fabric.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.pl3x.servergui.api.net.packet.Packet;
import net.pl3x.servergui.fabric.ServerGUI;
import org.jetbrains.annotations.NotNull;

public class Connection implements net.pl3x.servergui.api.net.Connection {
    private final PacketListener packetListener;

    public Connection() {
        this.packetListener = new PacketListener();
    }

    @Override
    public net.pl3x.servergui.api.net.PacketListener getPacketListener() {
        return this.packetListener;
    }

    @Override
    public void send(@NotNull Packet packet) {
        if (ServerGUI.client.getConnection() == null) {
            return;
        }
        ByteBuf buf = Unpooled.wrappedBuffer(packet.write().toByteArray());
        ClientPlayNetworking.send(
            NetworkHandler.RESOURCE_LOCATION,
            new FriendlyByteBuf(buf)
        );
    }
}
