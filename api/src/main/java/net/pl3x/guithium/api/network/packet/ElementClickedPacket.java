package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.ClickableElement;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet containing element click information.
 *
 * @param <T> Type of element clicked
 */
public class ElementClickedPacket<T extends ClickableElement<T>> extends Packet {
    /**
     * Unique identifying key
     */
    public static final Key KEY = Key.of("packet:element_click");

    private final Key screen;
    private final Key element;

    /**
     * Create a new element click packet.
     *
     * @param screen  Screen element was clicked on
     * @param element Element that was clicked
     */
    public ElementClickedPacket(@NotNull Screen screen, @NotNull ClickableElement<T> element) {
        super(KEY);
        this.screen = screen.getKey();
        this.element = element.getKey();
    }

    /**
     * Create a new element click packet.
     *
     * @param in Input byte array
     */
    public ElementClickedPacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.screen = Key.of(in.readUTF());
        this.element = Key.of(in.readUTF());
    }

    /**
     * Get the screen the element was clicked on.
     *
     * @return Element's screen
     */
    @NotNull
    public Key getScreen() {
        return this.screen;
    }

    /**
     * Get the element that was clicked.
     *
     * @return Clicked element
     */
    @NotNull
    public Key getElement() {
        return this.element;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleElementClick(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(getScreen().toString());
        out.writeUTF(getElement().toString());
        return out;
    }
}
