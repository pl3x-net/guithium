package net.pl3x.guithium.fabric.network;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.guithium.api.network.NetworkHandler;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import net.pl3x.guithium.api.network.packet.Packet;
import net.pl3x.guithium.fabric.GuithiumMod;
import org.jetbrains.annotations.NotNull;

public class FabricNetworkHandler extends NetworkHandler {
    public static final ResourceLocation CHANNEL_ID = ResourceLocation.parse(NetworkHandler.CHANNEL.toString());

    private final GuithiumMod mod;
    private final FabricConnection connection;

    public FabricNetworkHandler(@NotNull GuithiumMod mod) {
        this.mod = mod;
        this.connection = new FabricConnection();
    }

    @NotNull
    public FabricConnection getConnection() {
        return this.connection;
    }

    @Override
    public void registerListeners() {
        PayloadTypeRegistry.playC2S().register(Payload.TYPE, Payload.CODEC);
        PayloadTypeRegistry.playS2C().register(Payload.TYPE, Payload.CODEC);

        ClientPlayNetworking.registerGlobalReceiver(Payload.TYPE,
                (payload, context) -> receive(
                        getConnection().getPacketListener(),
                        Packet.in(payload.data())
                )
        );

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            // ensure we are not connecting to a single player game
            if (!client.isLocalServer()) {
                // send hello on first client tick to ensure everything is ready to receive a reply
                this.mod.getScheduler().addTask(() -> this.connection.send(new HelloPacket()));
            }

        });
    }

    public record Payload(ResourceLocation id, byte[] data) implements CustomPacketPayload {
        public static final Type<Payload> TYPE = new Type<>(CHANNEL_ID);
        public static final StreamCodec<ByteBuf, Payload> CODEC = CustomPacketPayload.codec(
                (value, output) -> output.writeBytes(value.data),
                (buffer) -> {
                    int i = buffer.readableBytes();
                    byte[] data = new byte[i];
                    buffer.readBytes(data);
                    return new Payload(CHANNEL_ID, data);
                });

        @NotNull
        public Type<Payload> type() {
            return TYPE;
        }
    }
}
