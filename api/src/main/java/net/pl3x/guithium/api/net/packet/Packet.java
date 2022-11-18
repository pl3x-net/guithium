package net.pl3x.guithium.api.net.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.net.PacketListener;
import net.pl3x.guithium.api.Key;
import org.jetbrains.annotations.NotNull;

public abstract class Packet {
    @NotNull
    public abstract Key getKey();

    public abstract <T extends PacketListener> void handle(@NotNull T listener);

    @NotNull
    public abstract ByteArrayDataOutput write();

    @NotNull
    @SuppressWarnings("UnstableApiUsage")
    public static ByteArrayDataOutput out(@NotNull Packet packet) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeInt(Guithium.PROTOCOL);
        out.writeUTF(packet.getKey().toString());
        return out;
    }

    @NotNull
    @SuppressWarnings("UnstableApiUsage")
    public static ByteArrayDataInput in(byte[] bytes) {
        return ByteStreams.newDataInput(bytes);
    }
}
