package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonObject;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.json.JsonSerializable;
import net.pl3x.guithium.api.network.packet.ElementPacket;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

/**
 * Represents an element to be displayed on a {@link net.pl3x.guithium.api.gui.Screen}.
 */
public interface Element extends JsonSerializable {
    /**
     * Get this element's identifying key.
     *
     * @return Identifying key
     */
    @NotNull
    Key getKey();

    /**
     * Get this element's type.
     *
     * @return Type
     */
    @NotNull
    Type getType();

    /**
     * Get this element's position from the anchor point.
     *
     * @return Position from anchor
     */
    @Nullable
    Vec2 getPos();

    /**
     * Set this element's position from the anchor point.
     *
     * @param x X (horizontal) position
     * @param y Y (vertical) position
     */
    void setPos(float x, float y);

    /**
     * Set this element's position from the anchor point.
     *
     * @param pos Position
     */
    void setPos(@Nullable Vec2 pos);

    /**
     * Get this element's anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @return Anchor point
     */
    @Nullable
    Vec2 getAnchor();

    /**
     * Set this element's anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @param x X (horizontal) anchor
     * @param y Y (vertical) anchor
     */
    void setAnchor(float x, float y);

    /**
     * Set this element's anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @param anchor Anchor point
     */
    void setAnchor(@Nullable Vec2 anchor);

    /**
     * Get this element's position offset.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the element's size.
     *
     * @return Position offset
     */
    @Nullable
    Vec2 getOffset();

    /**
     * Set this element's position offset.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the element's size.
     *
     * @param x X (horizontal) position offset
     * @param y Y (vertical) position offset
     */
    void setOffset(float x, float y);

    /**
     * Set this element's position offset.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the element's size.
     *
     * @param offset Position offset
     */
    void setOffset(@Nullable Vec2 offset);

    /**
     * Get this element's rotation in degrees.
     *
     * @return Degrees of rotation
     */
    @Nullable
    Float getRotation();

    /**
     * Set this element's rotation in degrees.
     *
     * @param degrees Degrees of rotation
     */
    void setRotation(@Nullable Float degrees);

    /**
     * Get this element's scale.
     *
     * @return Element's scale
     */
    @Nullable
    Float getScale();

    /**
     * Set this element's scale.
     *
     * @param scale Element's scale
     */
    void setScale(@Nullable Float scale);

    /**
     * Send this element to a player.
     * <p>
     * If the player already has this element, it will be updated on the screen. Otherwise, it will be ignored.
     *
     * @param player Player to send to
     */
    default void send(@NotNull WrappedPlayer player) {
        player.getConnection().send(new ElementPacket(this));
    }

    /**
     * Create an element object from json representation.
     *
     * @param json Json representation
     * @return A new element object, or null if invalid type
     */
    @Nullable
    static Element fromJson(@NotNull JsonObject json) {
        if (!json.has("type")) {
            return null;
        }
        return switch (Type.valueOf(json.get("type").getAsString().toUpperCase(Locale.ROOT))) {
            case BUTTON -> Button.fromJson(json);
            case CHECKBOX -> Checkbox.fromJson(json);
            case CIRCLE -> Circle.fromJson(json);
            case GRADIENT -> Gradient.fromJson(json);
            case IMAGE -> Image.fromJson(json);
            case LINE -> Line.fromJson(json);
            case RADIO -> Radio.fromJson(json);
            case TEXT -> Text.fromJson(json);
            case TEXTBOX -> Textbox.fromJson(json);
        };
    }

    /**
     * Represents an element type.
     */
    enum Type {
        /**
         * Clickable buttons.
         */
        BUTTON,

        /**
         * Toggleable checkboxes.
         */
        CHECKBOX,

        /**
         * Round circles. (oh my!)
         */
        CIRCLE,

        /**
         * Gradient rectangles.
         */
        GRADIENT,

        /**
         * Textured images.
         */
        IMAGE,

        /**
         * Straight lines.
         */
        LINE,

        /**
         * Radio buttons.
         */
        RADIO,

        /**
         * Renderable text.
         */
        TEXT,

        /**
         * Interactable textbox for user input.
         */
        TEXTBOX
    }
}
