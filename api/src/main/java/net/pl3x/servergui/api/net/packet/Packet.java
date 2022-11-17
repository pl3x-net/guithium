package net.pl3x.servergui.api.net.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.net.PacketListener;
import org.jetbrains.annotations.NotNull;

public abstract class Packet {
    public abstract Key getKey();

    public abstract <T extends PacketListener> void handle(@NotNull T listener);

    @NotNull
    public abstract ByteArrayDataOutput write();

    @NotNull
    @SuppressWarnings("UnstableApiUsage")
    public static ByteArrayDataOutput out(Packet packet) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeInt(ServerGUI.PROTOCOL);
        out.writeUTF(packet.getKey().toString());
        return out;
    }

    @NotNull
    @SuppressWarnings("UnstableApiUsage")
    public static ByteArrayDataInput in(byte[] bytes) {
        return ByteStreams.newDataInput(bytes);
    }
}
