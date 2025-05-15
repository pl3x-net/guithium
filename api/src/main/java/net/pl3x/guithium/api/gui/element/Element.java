package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonObject;
import java.util.Locale;
import net.pl3x.guithium.api.Unsafe;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.json.JsonSerializable;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an element to be displayed on a {@link Screen}.
 */
public interface Element {
    /**
     * Get identifying key.
     *
     * @return Identifying key
     */
    @NotNull
    Key getKey();

    /**
     * Get this element's type.
     *
     * @return Type of element
     */
    @NotNull
    Type getType();

    /**
     * Get this element's position from the anchor point.
     *
     * @return Position from anchor
     */
    @Nullable
    Point getPos();

    /**
     * Set this element's position from the anchor point.
     *
     * @param x X (horizontal) position
     * @param y Y (vertical) position
     * @return This element
     */
    @NotNull
    Element setPos(float x, float y);

    /**
     * Set this element's position from the anchor point.
     *
     * @param pos Position
     * @return This element
     */
    @NotNull
    Element setPos(@Nullable Point pos);

    /**
     * Get this element's anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @return Anchor point
     */
    @Nullable
    Point getAnchor();

    /**
     * Set this element's anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @param x X (horizontal) anchor
     * @param y Y (vertical) anchor
     * @return This element
     */
    @NotNull
    Element setAnchor(float x, float y);

    /**
     * Set this element's anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @param anchor Anchor point
     * @return This element
     */
    @NotNull
    Element setAnchor(@Nullable Point anchor);

    /**
     * Get this element's position offset.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the element's size.
     *
     * @return Position offset
     */
    @Nullable
    Point getOffset();

    /**
     * Set this element's position offset.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the element's size.
     *
     * @param x X (horizontal) position offset
     * @param y Y (vertical) position offset
     * @return This element
     */
    @NotNull
    Element setOffset(float x, float y);

    /**
     * Set this element's position offset.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the element's size.
     *
     * @param offset Position offset
     * @return This element
     */
    @NotNull
    Element setOffset(@Nullable Point offset);

    /**
     * Represents an element type.
     */
    enum Type {
        /**
         * Represent a rectangle element.
         */
        RECT(Rect.class),

        /**
         * Represents a text element.
         */
        TEXT(Text.class);

        private final Class<? extends Element> clazz;

        /**
         * Create new element type
         *
         * @param clazz Class of element
         */
        Type(@NotNull Class<? extends Element> clazz) {
            this.clazz = clazz;
        }

        /**
         * Create element of this type from JSON representation.
         *
         * @param json JSON representation of element
         * @param <T>  Type of element
         * @return New element of type
         */
        @NotNull
        public static <T extends Element> T createElement(@NotNull JsonObject json) {
            String name = json.get("type").getAsString().toUpperCase(Locale.ROOT);
            return Unsafe.cast(JsonSerializable.GSON.fromJson(json, Type.valueOf(name).clazz));
        }
    }
}
