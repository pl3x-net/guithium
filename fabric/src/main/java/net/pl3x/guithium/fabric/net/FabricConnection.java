package net.pl3x.guithium.fabric.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.pl3x.guithium.api.network.Connection;
import net.pl3x.guithium.api.network.PacketListener;
import net.pl3x.guithium.api.network.packet.Packet;
import org.jetbrains.annotations.NotNull;

public class FabricConnection implements Connection {
    private final PacketListener packetListener;

    public FabricConnection() {
        this.packetListener = new FabricPacketListener();
    }

    @Override
    @NotNull
    public PacketListener getPacketListener() {
        return this.packetListener;
    }

    @Override
    public void send(@NotNull Packet packet) {
        send(packet, false);
    }

    @Override
    public void send(@NotNull Packet packet, boolean force) {
        if (!force && Minecraft.getInstance().getConnection() == null) {
            return;
        }
        ByteBuf buf = Unpooled.wrappedBuffer(packet.write().toByteArray());
        ClientPlayNetworking.send(
            FabricNetworkHandler.RESOURCE_LOCATION,
            new FriendlyByteBuf(buf)
        );
    }
}
