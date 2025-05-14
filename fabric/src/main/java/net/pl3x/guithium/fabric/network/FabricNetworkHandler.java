package net.pl3x.guithium.fabric.network;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.guithium.api.network.NetworkHandler;
import net.pl3x.guithium.api.network.packet.Packet;
import org.jetbrains.annotations.NotNull;

public class FabricNetworkHandler extends NetworkHandler {
    private final FabricConnection connection;

    public FabricNetworkHandler() {
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
                (payload, ctx) -> receive(getConnection(), payload.data())
        );
    }

    // Wrap our data into this packet that mimics paper's DiscardedPayload packet
    // I'm not sure why Fabric's is so different (it's missing the 'data' field)
    record Payload(@NotNull ResourceLocation id, byte[] data) implements CustomPacketPayload {
        private static final Type<Payload> TYPE = new Type<>(ResourceLocation.parse(NetworkHandler.CHANNEL));
        private static final StreamCodec<ByteBuf, Payload> CODEC = StreamCodec.ofMember(Payload::serialize, Payload::new);

        Payload(@NotNull Packet packet) {
            this(TYPE.id(), packet.write().toByteArray());
        }

        private Payload(@NotNull ByteBuf buf) {
            this(TYPE.id(), new byte[buf.readableBytes()]);
            buf.readBytes(this.data);
        }

        @NotNull
        public Type<Payload> type() {
            return TYPE;
        }

        private static void serialize(@NotNull Payload value, @NotNull ByteBuf output) {
            output.writeBytes(value.data);
        }
    }
}
