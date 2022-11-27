package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Slider;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet containing slider change information.
 */
public class SliderChangePacket extends Packet {
    /**
     * Unique identifying key
     */
    public static final Key KEY = Key.of("packet:slider_change");

    private final Key screen;
    private final Key slider;
    private final double value;

    /**
     * Creates a new slider change packet.
     *
     * @param screen Screen slider was changed on
     * @param slider Slider that was changed
     * @param value  New value of slider
     */
    public SliderChangePacket(@NotNull Screen screen, @NotNull Slider slider, double value) {
        super(KEY);
        this.screen = screen.getKey();
        this.slider = slider.getKey();
        this.value = value;
    }

    /**
     * Creates a new slider change packet.
     *
     * @param in Input byte array
     */
    public SliderChangePacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.screen = Key.of(in.readUTF());
        this.slider = Key.of(in.readUTF());
        this.value = in.readDouble();
    }

    /**
     * Get the screen the slider was changed on.
     *
     * @return Slider's screen
     */
    @NotNull
    public Key getScreen() {
        return this.screen;
    }

    /**
     * Get the slider that was changed.
     *
     * @return Changed slider
     */
    @NotNull
    public Key getSlider() {
        return this.slider;
    }

    /**
     * Get the new value of the slider.
     *
     * @return Slider's new value
     */
    public double getValue() {
        return this.value;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleSliderChange(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(getScreen().toString());
        out.writeUTF(getSlider().toString());
        out.writeDouble(getValue());
        return out;
    }
}
