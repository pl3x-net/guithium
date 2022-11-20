package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

public class ButtonClickPacket extends Packet {
    public static final Key KEY = Key.of("packet:button_click");

    private final Key screen;
    private final Key button;

    public ButtonClickPacket(@NotNull Screen screen, @NotNull Button button) {
        this.screen = screen.getKey();
        this.button = button.getKey();
    }

    public ButtonClickPacket(@NotNull ByteArrayDataInput in) {
        this.screen = Key.of(in.readUTF());
        this.button = Key.of(in.readUTF());
    }

    @Override
    @NotNull
    public Key getKey() {
        return KEY;
    }

    @NotNull
    public Key getScreen() {
        return this.screen;
    }

    @NotNull
    public Key getButton() {
        return this.button;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleButtonClick(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(getScreen().toString());
        out.writeUTF(getButton().toString());
        return out;
    }
}
