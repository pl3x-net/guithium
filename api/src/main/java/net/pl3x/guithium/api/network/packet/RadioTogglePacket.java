package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Radio;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

public class RadioTogglePacket extends Packet {
    public static final Key KEY = Key.of("packet:radio_toggle");

    private final Key screen;
    private final Key radio;
    private final boolean selected;

    public RadioTogglePacket(@NotNull Screen screen, @NotNull Radio radio, boolean selected) {
        this.screen = screen.getKey();
        this.radio = radio.getKey();
        this.selected = selected;
    }

    public RadioTogglePacket(@NotNull ByteArrayDataInput in) {
        this.screen = Key.of(in.readUTF());
        this.radio = Key.of(in.readUTF());
        this.selected = in.readBoolean();
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
    public Key getRadio() {
        return this.radio;
    }

    public boolean getSelected() {
        return this.selected;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleRadioToggle(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(getScreen().toString());
        out.writeUTF(getRadio().toString());
        out.writeBoolean(getSelected());
        return out;
    }
}
