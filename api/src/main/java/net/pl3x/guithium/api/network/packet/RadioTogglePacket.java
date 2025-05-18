package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Radio;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet containing radio button toggle information.
 */
public class RadioTogglePacket extends Packet {
    /**
     * Unique identifying key
     */
    public static final Key KEY = Key.of("packet:radio_toggle");

    private final Key screen;
    private final Key radio;
    private final boolean selected;

    /**
     * Create a new radio toggle packet.
     *
     * @param screen   Screen radio button was toggled on
     * @param radio    Radio button that was toggled
     * @param selected New selected state of radio button
     */
    public RadioTogglePacket(@NotNull Screen screen, @NotNull Radio radio, boolean selected) {
        super(KEY);
        this.screen = screen.getKey();
        this.radio = radio.getKey();
        this.selected = selected;
    }

    /**
     * Create a new radio toggle packet.
     *
     * @param in Input byte array
     */
    public RadioTogglePacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.screen = Key.of(in.readUTF());
        this.radio = Key.of(in.readUTF());
        this.selected = in.readBoolean();
    }

    /**
     * Get the screen the radio button was toggled on.
     *
     * @return Radio button's screen
     */
    @NotNull
    public Key getScreen() {
        return this.screen;
    }

    /**
     * Get the radio button that was toggled.
     *
     * @return Toggled radio button
     */
    @NotNull
    public Key getRadio() {
        return this.radio;
    }

    /**
     * Get the new selected state of the radio button.
     *
     * @return Radio button's new selected state
     */
    public boolean isSelected() {
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
        out.writeBoolean(isSelected());
        return out;
    }
}
