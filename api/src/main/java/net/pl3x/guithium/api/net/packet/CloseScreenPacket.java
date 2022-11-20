package net.pl3x.guithium.api.net.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.net.PacketListener;
import org.jetbrains.annotations.NotNull;

public class CloseScreenPacket extends Packet {
    public static final Key KEY = Key.of("packet:close_screen");

    private final Key screenKey;

    public CloseScreenPacket(@NotNull Key screenKey) {
        this.screenKey = screenKey;
    }

    public CloseScreenPacket(@NotNull ByteArrayDataInput in) {
        this.screenKey = Key.of(in.readUTF());
    }

    @Override
    @NotNull
    public Key getKey() {
        return KEY;
    }

    @NotNull
    public Key getScreenKey() {
        return this.screenKey;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleCloseScreen(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(getScreenKey().toString());
        return out;
    }
}
