package net.pl3x.servergui.api.net.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.net.PacketListener;
import org.jetbrains.annotations.NotNull;

public class HelloPacket extends Packet {
    public static final Key KEY = Key.of("packet:hello");

    private final int protocol;

    public HelloPacket() {
        this.protocol = ServerGUI.PROTOCOL;
    }

    public HelloPacket(@NotNull ByteArrayDataInput in) {
        this.protocol = in.readInt();
    }

    @Override
    public Key getKey() {
        return KEY;
    }

    public int getProtocol() {
        return this.protocol;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleHello(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeInt(getProtocol());
        return out;
    }
}
