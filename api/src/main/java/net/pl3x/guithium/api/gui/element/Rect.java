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
     * @param key  Unique identifier for element
     * @param type Type of rect
     */
    protected Rect(@NotNull Key key, @NotNull Type type) {
        super(key, type);
    }

    /**
     * Get the size of this element.
     *
     * @return Size of element
     */
    @Nullable
    public Vec2 getSize() {
        return this.size;
    }

    /**
     * Set the size of this element.
     *
     * @param width  Width to set
     * @param height Height to set
     * @return This rect
     */
    @NotNull
    public T setSize(float width, float height) {
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
        this.size = size;
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
        // pacifies codefactor.io
        return super.hashCode();
    }
}
