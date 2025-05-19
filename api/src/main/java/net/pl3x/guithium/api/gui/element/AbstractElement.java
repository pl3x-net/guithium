package net.pl3x.guithium.api.gui.element;

import java.util.Objects;
import net.pl3x.guithium.api.Unsafe;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.key.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an abstract keyed element.
 *
 * @param <T> The type of element
 */
public abstract class AbstractElement<T extends AbstractElement<T>> extends Keyed implements Element {
    private final Type type;

    private Vec2 pos;
    private Vec2 anchor;
    private Vec2 offset;
    private Float rotation;
    private Float scale;

    /**
     * Create a new keyed element.
     *
     * @param key  Unique identifier for element
     * @param type Type of element
     */
    protected AbstractElement(@NotNull Key key, @NotNull Type type) {
        super(key);
        this.type = type;
    }

    @Override
    @NotNull
    public Type getType() {
        return this.type;
    }

    @Override
    @NotNull
    public Vec2 getPos() {
        return this.pos == null ? Vec2.ZERO : this.pos;
    }

    @Override
    @NotNull
    public T setPos(int x, int y) {
        return setPos(Vec2.of(x, y));
    }

    @Override
    @NotNull
    public T setPos(@Nullable Vec2 pos) {
        this.pos = pos == Vec2.ZERO ? null : pos;
        return Unsafe.cast(this);
    }

    @Override
    @NotNull
    public Vec2 getAnchor() {
        return this.anchor == null ? Vec2.ZERO : this.anchor;
    }

    @Override
    @NotNull
    public T setAnchor(int x, int y) {
        return setAnchor(Vec2.of(x, y));
    }

    @Override
    @NotNull
    public T setAnchor(@Nullable Vec2 anchor) {
        this.anchor = anchor == Vec2.ZERO ? null : anchor;
        return Unsafe.cast(this);
    }

    @Override
    @NotNull
    public Vec2 getOffset() {
        return this.offset == null ? Vec2.ZERO : this.offset;
    }

    @Override
    @NotNull
    public T setOffset(int x, int y) {
        return setOffset(Vec2.of(x, y));
    }

    @Override
    @NotNull
    public T setOffset(@Nullable Vec2 offset) {
        this.offset = offset == Vec2.ZERO ? null : offset;
        return Unsafe.cast(this);
    }

    @Override
    @Nullable
    public Float getRotation() {
        return this.rotation;
    }

    @Override
    @NotNull
    public T setRotation(@Nullable Float degrees) {
        this.rotation = degrees;
        return Unsafe.cast(this);
    }

    @Override
    @Nullable
    public Float getScale() {
        return this.scale;
    }

    @Override
    @NotNull
    public T setScale(@Nullable Float scale) {
        this.scale = scale;
        return Unsafe.cast(this);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        T other = Unsafe.cast(obj);
        return Objects.equals(getType(), other.getType())
                && Objects.equals(getPos(), other.getPos())
                && Objects.equals(getAnchor(), other.getAnchor())
                && Objects.equals(getOffset(), other.getOffset())
                && Objects.equals(getRotation(), other.getRotation())
                && Objects.equals(getScale(), other.getScale());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getType(),
                getPos(),
                getAnchor(),
                getOffset(),
                getRotation(),
                getScale()
        );
    }
}
