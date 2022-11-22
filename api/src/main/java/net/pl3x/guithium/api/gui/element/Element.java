package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonObject;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Point;
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
    Point getPos();

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
    void setPos(@Nullable Point pos);

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
     */
    void setAnchor(float x, float y);

    /**
     * Set this element's anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @param anchor Anchor point
     */
    void setAnchor(@Nullable Point anchor);

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
     */
    void setOffset(float x, float y);

    /**
     * Set this element's position offset.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the element's size.
     *
     * @param offset Position offset
     */
    void setOffset(@Nullable Point offset);

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
            case GRADIENT -> Gradient.fromJson(json);
            case IMAGE -> Image.fromJson(json);
            case TEXT -> Text.fromJson(json);
            case TEXTBOX -> Textbox.fromJson(json);
        };
    }

    /**
     * Represents an element type.
     */
    enum Type {
        BUTTON, CHECKBOX, GRADIENT, IMAGE, TEXT, TEXTBOX
    }
}
