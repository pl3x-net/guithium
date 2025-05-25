package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.Unsafe;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet containing information about an elements value being changed.
 *
 * @param <V> Type of value
 */
public class ElementChangedValuePacket<V> extends Packet {
    /**
     * Unique identifying key
     */
    public static final Key KEY = Key.of(Guithium.MOD_ID + ":packet-element_changed_value");

    private final Key screen;
    private final Key element;
    private final DataType<V> type;
    private final V value;

    /**
     * Create a new element changed value packet.
     *
     * @param screen  Screen element was changed on
     * @param element Element that was changed
     * @param value   New value of element
     */
    public ElementChangedValuePacket(@NotNull Screen screen, @NotNull Element element, @NotNull V value) {
        super(KEY);
        this.screen = screen.getKey();
        this.element = element.getKey();
        this.type = DataType.get(value);
        this.value = value;
    }

    /**
     * Create a new element changed value packet.
     *
     * @param in Input byte array
     */
    public ElementChangedValuePacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.screen = Key.of(in.readUTF());
        this.element = Key.of(in.readUTF());
        this.type = DataType.get(in.readUnsignedByte());
        this.value = this.type.get(in);
    }

    /**
     * Get the screen the element was changed on.
     *
     * @return Element's screen
     */
    @NotNull
    public Key getScreen() {
        return this.screen;
    }

    /**
     * Get the element that was changed.
     *
     * @return Changed element
     */
    @NotNull
    public Key getElement() {
        return this.element;
    }

    /**
     * Get the new value of the element.
     *
     * @return Element's new value
     */
    @NotNull
    public V getValue() {
        return this.value;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleElementChangedValue(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(getScreen().toString());
        out.writeUTF(getElement().toString());
        out.writeByte(this.type.ordinal);
        this.type.put(out, getValue());
        return out;
    }

    private static final class DataType<T> {
        private final int ordinal;
        private Function<ByteArrayDataInput, T> in;
        private BiConsumer<ByteArrayDataOutput, T> out;

        private DataType(Function<ByteArrayDataInput, T> in, BiConsumer<ByteArrayDataOutput, T> out, int ordinal) {
            this.ordinal = ordinal;
            in(in);
            out(out);
        }

        private Function<ByteArrayDataInput, T> in() {
            return this.in;
        }

        private void in(Function<ByteArrayDataInput, T> in) {
            this.in = in;
        }

        private BiConsumer<ByteArrayDataOutput, T> out() {
            return this.out;
        }

        private void out(BiConsumer<ByteArrayDataOutput, T> out) {
            this.out = out;
        }

        private T get(ByteArrayDataInput in) {
            return in().apply(in);
        }

        private void put(ByteArrayDataOutput out, T value) {
            out().accept(out, value);
        }

        private static final Map<String, DataType<?>> byType = new HashMap<>();
        private static final Map<Integer, DataType<?>> byOrdinal = new HashMap<>();

        static {
            int ordinal = 0;
            // not sure why some of these need to be lambdas...
            create(Boolean.class, ByteArrayDataInput::readBoolean, ByteArrayDataOutput::writeBoolean, ordinal++);
            create(Byte.class, ByteArrayDataInput::readByte, (out, value) -> out.writeByte(value), ordinal++);
            create(Character.class, ByteArrayDataInput::readChar, (out, value) -> out.writeChar(value), ordinal++);
            create(Double.class, ByteArrayDataInput::readDouble, ByteArrayDataOutput::writeDouble, ordinal++);
            create(Float.class, ByteArrayDataInput::readFloat, ByteArrayDataOutput::writeFloat, ordinal++);
            create(Integer.class, ByteArrayDataInput::readInt, ByteArrayDataOutput::writeInt, ordinal++);
            create(Long.class, ByteArrayDataInput::readLong, ByteArrayDataOutput::writeLong, ordinal++);
            create(Short.class, ByteArrayDataInput::readShort, (out, value) -> out.writeShort(value), ordinal++);
            create(String.class, ByteArrayDataInput::readUTF, ByteArrayDataOutput::writeUTF, ordinal);
        }

        private static <V> void create(Class<V> clazz, Function<ByteArrayDataInput, V> in, BiConsumer<ByteArrayDataOutput, V> out, int ordinal) {
            DataType<V> type = new DataType<>(in, out, ordinal);
            byType.put(clazz.getTypeName(), type);
            byOrdinal.put(ordinal, type);
        }

        private static <V> DataType<V> get(V value) {
            return Unsafe.cast(byType.get(value.getClass().getTypeName()));
        }

        private static <V> DataType<V> get(int ordinal) {
            return Unsafe.cast(byOrdinal.get(ordinal));
        }
    }
}
