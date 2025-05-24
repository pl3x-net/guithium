package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.json.Gson;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an update element packet.
 */
public class ElementPacket extends Packet {
    /**
     * Unique identifier for element packets.
     */
    public static final Key KEY = Key.of("packet:element");

    private final Element element;

    /**
     * Create a new update element packet.
     *
     * @param element Element to update
     */
    public ElementPacket(@NotNull Element element) {
        super(KEY);
        this.element = element;
    }

    /**
     * Create a new update element packet.
     *
     * @param in Input byte array
     */
    public ElementPacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.element = Gson.fromJson(in.readUTF(), Element.class);
    }

    /**
     * Get the element to update.
     *
     * @return Element to update
     */
    @NotNull
    public Element getElement() {
        return this.element;
    }

    @Override
    public <T extends PacketListener> void handle(@NotNull T listener) {
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
