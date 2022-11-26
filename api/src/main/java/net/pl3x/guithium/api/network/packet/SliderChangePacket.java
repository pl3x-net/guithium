package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Slider;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

public class SliderChangePacket extends Packet {
    public static final Key KEY = Key.of("packet:slider_change");

    private final Key screen;
    private final Key slider;
    private final double value;

    public SliderChangePacket(@NotNull Screen screen, @NotNull Slider slider, double value) {
        this.screen = screen.getKey();
        this.slider = slider.getKey();
        this.value = value;
    }

    public SliderChangePacket(@NotNull ByteArrayDataInput in) {
        this.screen = Key.of(in.readUTF());
        this.slider = Key.of(in.readUTF());
        this.value = in.readDouble();
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
    public Key getSlider() {
        return this.slider;
    }

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
