package net.pl3x.guithium.api.net.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Checkbox;
import net.pl3x.guithium.api.net.PacketListener;
import org.jetbrains.annotations.NotNull;

public class CheckboxTogglePacket extends Packet {
    public static final Key KEY = Key.of("packet:checkbox_toggle");

    private final Key screen;
    private final Key checkbox;
    private final boolean checked;

    public CheckboxTogglePacket(@NotNull Screen screen, @NotNull Checkbox checkbox, boolean checked) {
        this.screen = screen.getKey();
        this.checkbox = checkbox.getKey();
        this.checked = checked;
    }

    public CheckboxTogglePacket(@NotNull ByteArrayDataInput in) {
        this.screen = Key.of(in.readUTF());
        this.checkbox = Key.of(in.readUTF());
        this.checked = in.readBoolean();
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
    public Key getCheckbox() {
        return this.checkbox;
    }

    public boolean getChecked() {
        return this.checked;
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
        out.writeBoolean(getChecked());
        return out;
    }
}
