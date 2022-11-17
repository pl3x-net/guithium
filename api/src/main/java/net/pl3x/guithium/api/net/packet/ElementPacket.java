package net.pl3x.guithium.api.net.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.json.Gson;
import net.pl3x.guithium.api.net.PacketListener;
import org.jetbrains.annotations.NotNull;

public class ElementPacket extends Packet {
    public static final Key KEY = Key.of("packet:element");

    private final Element element;

    public ElementPacket(Element element) {
        this.element = element;
    }

    public ElementPacket(@NotNull ByteArrayDataInput in) {
        this.element = Gson.fromJson(in.readUTF(), Element.class);
    }

    @Override
    public Key getKey() {
        return KEY;
    }

    public Element getElement() {
        return this.element;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleElement(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(Gson.toJson(getElement()));
        return out;
    }
}
