package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.pl3x.guithium.api.Unsafe;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
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
    private Vec2 pos;
    private Vec2 anchor;
    private Vec2 offset;
    private Float rotation;
    private Float scale;

    /**
     * Create a new keyed element.
     *
     * @param key Unique identifier for element
     */
    protected AbstractElement(@NotNull Key key) {
        super(key);
    }

    @Override
    @NotNull
    public Vec2 getPos() {
        return this.pos == null ? Vec2.ZERO : this.pos;
    }

    @Override
    @NotNull
    public T setPos(float x, float y) {
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
    public T setAnchor(float x, float y) {
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
    public T setOffset(float x, float y) {
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
        return Objects.equals(getPos(), other.getPos())
                && Objects.equals(getAnchor(), other.getAnchor())
                && Objects.equals(getOffset(), other.getOffset())
                && Objects.equals(getRotation(), other.getRotation())
                && Objects.equals(getScale(), other.getScale());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getPos(),
                getAnchor(),
                getOffset(),
                getRotation(),
                getScale()
        );
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("type", getClass().getSimpleName());
        json.addProperty("pos", getPos());
        json.addProperty("anchor", getAnchor());
        json.addProperty("offset", getOffset());
        json.addProperty("rotation", getRotation());
        json.addProperty("scale", getScale());
        return json.getJsonObject();
    }

    /**
     * Populate an abstract element from Json.
     *
     * @param element An abstract element to populate
     * @param json    Json representation of an abstract element
     */
    public static void fromJson(@NotNull AbstractElement<?> element, @NotNull JsonObject json) {
        element.setPos(!json.has("pos") ? null : Vec2.fromJson(json.get("pos").getAsJsonObject()));
        element.setAnchor(!json.has("anchor") ? null : Vec2.fromJson(json.get("anchor").getAsJsonObject()));
        element.setOffset(!json.has("offset") ? null : Vec2.fromJson(json.get("offset").getAsJsonObject()));
        element.setRotation(!json.has("rotation") ? null : json.get("rotation").getAsFloat());
        element.setScale(!json.has("scale") ? null : json.get("scale").getAsFloat());
    }
}
