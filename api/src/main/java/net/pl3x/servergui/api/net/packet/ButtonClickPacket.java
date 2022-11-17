package net.pl3x.servergui.api.net.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.gui.element.Button;
import net.pl3x.servergui.api.net.PacketListener;
import org.jetbrains.annotations.NotNull;

public class ButtonClickPacket extends Packet {
    public static final Key KEY = Key.of("packet:button_click");

    private final String screenId;
    private final String buttonId;

    public ButtonClickPacket(Screen screen, Button button) {
        this.screenId = screen.getKey().toString();
        this.buttonId = button.getKey().toString();
    }

    public ButtonClickPacket(@NotNull ByteArrayDataInput in) {
        this.screenId = in.readUTF();
        this.buttonId = in.readUTF();
    }

    @Override
    public Key getKey() {
        return KEY;
    }

    public String getScreenId() {
        return this.screenId;
    }

    public String getButtonId() {
        return this.buttonId;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleButtonClick(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(getScreenId());
        out.writeUTF(getButtonId());
        return out;
    }
}
