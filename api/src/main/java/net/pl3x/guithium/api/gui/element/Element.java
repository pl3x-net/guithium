package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Locale;
import net.pl3x.guithium.api.Unsafe;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.json.JsonSerializable;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an element.
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
     * Get this element's position from the anchor position.
     *
     * @return Position from anchor
     */
    @NotNull
    Vec2 getPos();

    /**
     * Set this element's position from the anchor position.
     *
     * @param x X (horizontal) position
     * @param y Y (vertical) position
     * @return This element
     */
    @NotNull
    Element setPos(float x, float y);

    /**
     * Set this element's position from the anchor position.
     *
     * @param pos Position
     * @return This element
     */
    @NotNull
    Element setPos(@Nullable Vec2 pos);

    /**
     * Get this element's anchor position on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @return Anchor position
     */
    @NotNull
    Vec2 getAnchor();

    /**
     * Set this element's anchor position on the screen.
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
     * Set this element's anchor position on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @param anchor Anchor position
     * @return This element
     */
    @NotNull
    Element setAnchor(@Nullable Vec2 anchor);

    /**
     * Get this element's offset position on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the element's size.
     *
     * @return Position offset
     */
    @NotNull
    Vec2 getOffset();

    /**
     * Set this element's offset position on the screen.
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
     * Set this element's offset position on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the element's size.
     *
     * @param offset Position offset
     * @return This element
     */
    @NotNull
    Element setOffset(@Nullable Vec2 offset);

    /**
     * Get this element's rotation in degrees.
     *
     * @return Degrees of rotation
     */
    @Nullable
    Float getRotation();

    /**
     * Set this element's rotation in degrees.
     * <p>
     * If null, default rotation <code>0.0</code> will be used.
     *
     * @param degrees Degrees of rotation
     * @return This element
     */
    @NotNull
    Element setRotation(@Nullable Float degrees);

    /**
     * Get this element's scale.
     * <p>
     * If null, default scale <code>1.0</code> will be used.
     *
     * @return Element's scale
     */
    @Nullable
    Float getScale();

    /**
     * Set this element's scale.
     * <p>
     * If null, default scale <code>1.0</code> will be used.
     *
     * @param scale Element's scale
     * @return This element
     */
    @NotNull
    Element setScale(@Nullable Float scale);

    /**
     * Represents an element type.
     */
    enum Type {
        /**
         * Represents a button element.
         */
        BUTTON(Button.class),

        /**
         * Represents a checkbox element.
         */
        CHECKBOX(Checkbox.class),

        /**
         * Represents a circle element.
         */
        CIRCLE(Circle.class),

        /**
         * Represent a gradient element.
         */
        GRADIENT(Rect.class),

        /**
         * Represents an image element.
         */
        IMAGE(Image.class),

        /**
         * Represents a line element.
         */
        LINE(Line.class),

        /**
         * Represents a radio box element.
         */
        RADIO(Radio.class),

        /**
         * Represents a slider element.
         */
        SLIDER(Slider.class),

        /**
         * Represents a text element.
         */
        TEXT(Text.class),

        /**
         * Represents a textbox element.
         */
        TEXTBOX(Textbox.class),

        ;

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
         * Create new element from JSON representation.
         *
         * @param json JSON representation of element
         * @param <T>  Type of element
         * @return New element
         * @throws JsonSyntaxException if JSON is not a valid representation for an element
         */
        @NotNull
        public static <T extends Element> T createElement(@NotNull JsonObject json) {
            try {
                if (!json.has("type")) {
                    throw new IllegalArgumentException("JSON must have 'type' field");
                }
                String name = json.get("type").getAsString().toUpperCase(Locale.ROOT);
                return Unsafe.cast(JsonSerializable.GSON.fromJson(json, valueOf(name).clazz));
            } catch (Throwable e) {
                throw new JsonSyntaxException(e);
            }
        }
    }
}
