package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet containing button click information.
 */
public class ButtonClickPacket extends Packet {
    /**
     * Unique identifying key
     */
    public static final Key KEY = Key.of("packet:button_click");

    private final Key screen;
    private final Key button;

    /**
     * Create a new button click packet.
     *
     * @param screen Screen button was clicked on
     * @param button Button that was clicked
     */
    public ButtonClickPacket(@NotNull Screen screen, @NotNull Button button) {
        super(KEY);
        this.screen = screen.getKey();
        this.button = button.getKey();
    }

    /**
     * Create a new button click packet.
     *
     * @param in Input byte array
     */
    public ButtonClickPacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.screen = Key.of(in.readUTF());
        this.button = Key.of(in.readUTF());
    }

    /**
     * Get the screen the button was clicked on.
     *
     * @return Button's screen
     */
    @NotNull
    public Key getScreen() {
        return this.screen;
    }

    /**
     * Get the button that was clicked.
     *
     * @return Clicked button
     */
    @NotNull
    public Key getButton() {
        return this.button;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        //listener.handleButtonClick(this);
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
