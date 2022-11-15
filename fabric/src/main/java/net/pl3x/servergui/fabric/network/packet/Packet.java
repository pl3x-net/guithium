package net.pl3x.servergui.fabric.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public abstract class Packet {
    protected static void send(ResourceLocation channel, ByteArrayDataOutput out) {
        if (Minecraft.getInstance().getConnection() != null) {
            ClientPlayNetworking.send(channel, new FriendlyByteBuf(Unpooled.wrappedBuffer(out.toByteArray())));
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    protected static ByteArrayDataOutput out() {
        return ByteStreams.newDataOutput();
    }

    @SuppressWarnings("UnstableApiUsage")
    protected static ByteArrayDataInput in(byte[] bytes) {
        return ByteStreams.newDataInput(bytes);
    }
}
