package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonObject;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.json.JsonSerializable;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.network.packet.ElementPacket;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an element.
 */
public interface Element extends JsonSerializable {
    /**
     * Get identifying key.
     *
     * @return Identifying key
     */
    @NotNull
    Key getKey();

    /**
     * Get this element's position from the anchor position.
     * <p>
     * This is represented in pixels that scale with the client's settings.
     *
     * @return Position from anchor
     */
    @NotNull
    Vec2 getPos();

    /**
     * Set this element's position from the anchor position.
     * <p>
     * This is represented in pixels that scale with the client's settings.
     *
     * @param x X (horizontal) position
     * @param y Y (vertical) position
     * @return This element
     */
    @NotNull
    Element setPos(float x, float y);

    /**
     * Set this element's position from the anchor position.
     * <p>
     * This is represented in pixels that scale with the client's settings.
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
     * <p>
     * If null, default rotation <code>0.0</code> will be used.
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
     * Send this element to a player.
     * <p>
     * If the player already has this element, it will be updated on the screen. Otherwise, it will be ignored.
     *
     * @param player Player to send to
     * @param <T>    Native player type
     */
    default <T> void send(@NotNull T player) {
        send(Guithium.api().getPlayerManager().get(player));
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
        String type = json.get("type").getAsString();
        return switch (type) {
            case "Button" -> Button.fromJson(json);
            case "Checkbox" -> Checkbox.fromJson(json);
            case "Circle" -> Circle.fromJson(json);
            case "Gradient" -> Gradient.fromJson(json);
            case "Image" -> Image.fromJson(json);
            case "Line" -> Line.fromJson(json);
            case "Radio" -> Radio.fromJson(json);
            case "Slider" -> Slider.fromJson(json);
            case "Text" -> Text.fromJson(json);
            case "Textbox" -> Textbox.fromJson(json);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
