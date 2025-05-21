package net.pl3x.guithium.api.gui.element;

import java.util.Objects;
import net.pl3x.guithium.api.Unsafe;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a rectangle element
 *
 * @param <T> Type of rectangle element
 */
public abstract class Rect<T extends Rect<T>> extends AbstractElement<T> {
    private Vec2 size;

    /**
     * Create a new rect-type element.
     *
     * @param key Unique identifier for element
     */
    protected Rect(@NotNull Key key) {
        super(key);
    }

    /**
     * Get the size of this element.
     *
     * @return Size of element
     */
    @NotNull
    public Vec2 getSize() {
        return this.size == null ? Vec2.ZERO : this.size;
    }

    /**
     * Set the size of this element.
     *
     * @param width  Width to set
     * @param height Height to set
     * @return This rect
     */
    @NotNull
    public T setSize(int width, int height) {
        return setSize(Vec2.of(width, height));
    }

    /**
     * Set the size of this element.
     *
     * @param size Size to set
     * @return This rect
     */
    @NotNull
    public T setSize(@Nullable Vec2 size) {
        this.size = size == Vec2.ZERO ? null : size;
        return Unsafe.cast(this);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Rect<T> other = Unsafe.cast(obj);
        return Objects.equals(getSize(), other.getSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getSize()
        );
    }
}
