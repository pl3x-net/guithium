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
    @Nullable
    public Vec2 getPos() {
        return this.pos;
    }

    @Override
    @NotNull
    public T setPos(float x, float y) {
        setPos(Vec2.of(x, y));
        return Unsafe.cast(this);
    }

    @Override
    @NotNull
    public T setPos(@Nullable Vec2 pos) {
        this.pos = pos;
        return Unsafe.cast(this);
    }

    @Override
    @Nullable
    public Vec2 getAnchor() {
        return this.anchor;
    }

    @Override
    @NotNull
    public T setAnchor(float x, float y) {
        setAnchor(Vec2.of(x, y));
        return Unsafe.cast(this);
    }

    @Override
    @NotNull
    public T setAnchor(@Nullable Vec2 anchor) {
        this.anchor = anchor;
        return Unsafe.cast(this);
    }

    @Override
    @Nullable
    public Vec2 getOffset() {
        return this.offset;
    }

    @Override
    @NotNull
    public T setOffset(float x, float y) {
        setOffset(Vec2.of(x, y));
        return Unsafe.cast(this);
    }

    @Override
    @NotNull
    public T setOffset(@Nullable Vec2 offset) {
        this.offset = offset;
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
        // pacifies codefactor.io
        return super.hashCode();
    }
}
