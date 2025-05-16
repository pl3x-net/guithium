package net.pl3x.guithium.fabric.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.pl3x.guithium.api.network.Connection;
import net.pl3x.guithium.api.network.packet.Packet;
import org.jetbrains.annotations.NotNull;

public class FabricConnection implements Connection {
    private final FabricPacketListener packetListener;

    public FabricConnection() {
        this.packetListener = new FabricPacketListener();
    }

    @Override
    @NotNull
    public FabricPacketListener getPacketListener() {
        return this.packetListener;
    }

    @Override
    public void send(@NotNull Packet packet) {
        send(packet, false);
    }

    @Override
    public void send(@NotNull Packet packet, boolean force) {
        if (force || Minecraft.getInstance().getConnection() != null) {
            ClientPlayNetworking.send(new FabricNetworkHandler.Payload(packet));
        }
    }
}
