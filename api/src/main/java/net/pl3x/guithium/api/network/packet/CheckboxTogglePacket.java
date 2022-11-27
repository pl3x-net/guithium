package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Checkbox;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet containing checkbox toggle information.
 */
public class CheckboxTogglePacket extends Packet {
    /**
     * Unique identifying key
     */
    public static final Key KEY = Key.of("packet:checkbox_toggle");

    private final Key screen;
    private final Key checkbox;
    private final boolean selected;

    /**
     * Creates a new checkbox toggle packet.
     *
     * @param screen   Screen checkbox was toggled on
     * @param checkbox Checkbox that was toggled
     * @param selected New checkbox selected state
     */
    public CheckboxTogglePacket(@NotNull Screen screen, @NotNull Checkbox checkbox, boolean selected) {
        super(KEY);
        this.screen = screen.getKey();
        this.checkbox = checkbox.getKey();
        this.selected = selected;
    }

    /**
     * Creates a new checkbox toggle packet.
     *
     * @param in Input byte array
     */
    public CheckboxTogglePacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.screen = Key.of(in.readUTF());
        this.checkbox = Key.of(in.readUTF());
        this.selected = in.readBoolean();
    }

    /**
     * Get the screen the checkbox was toggled on.
     *
     * @return Checkbox's screen
     */
    @NotNull
    public Key getScreen() {
        return this.screen;
    }

    /**
     * Get the checkbox that was toggled.
     *
     * @return Toggled checkbox
     */
    @NotNull
    public Key getCheckbox() {
        return this.checkbox;
    }

    /**
     * Get the new selected state of the checkbox.
     *
     * @return Checkbox's new selected state
     */
    public boolean getSelected() {
        return this.selected;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleCheckboxToggle(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(getScreen().toString());
        out.writeUTF(getCheckbox().toString());
        out.writeBoolean(getSelected());
        return out;
    }
}
