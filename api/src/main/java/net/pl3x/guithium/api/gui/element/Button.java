package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.api.util.TriConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a button element.
 */
public class Button extends LabeledRect<Button> {
    private OnClick onClick = (screen, button, player) -> {
    };

    /**
     * Create a new button element.
     *
     * @param key Unique identifier for element
     */
    public Button(@NotNull String key) {
        this(Key.of(key));
    }

    /**
     * Create a new button element.
     *
     * @param key Unique identifier for element
     */
    public Button(@NotNull Key key) {
        super(key);
    }

    /**
     * Create a new button element.
     *
     * @param key Unique identifier
     * @return New button element
     */
    @NotNull
    public static Button of(@NotNull String key) {
        return of(Key.of(key));
    }

    /**
     * Create a new button element.
     *
     * @param key Unique identifier
     * @return New button element
     */
    @NotNull
    public static Button of(@NotNull Key key) {
        return new Button(key);
    }

    /**
     * Get the action to execute when the button is clicked.
     * <p>
     * If null, no click action will be used.
     *
     * @return OnClick action
     */
    @Nullable
    public OnClick onClick() {
        return this.onClick;
    }

    /**
     * Set the action to execute when the button is clicked.
     * <p>
     * If null, no click action will be used.
     *
     * @param onClick OnClick action
     * @return This button
     */
    @NotNull
    public Button onClick(@Nullable OnClick onClick) {
        this.onClick = onClick;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Button other = (Button) obj;
        return Objects.equals(onClick(), other.onClick());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), onClick());
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("label", getLabel());
        json.addProperty("tooltip", getTooltip());
        return json.getJsonObject();
    }

    /**
     * Create a new button from Json.
     *
     * @param json Json representation of a button
     * @return A new button
     */
    @NotNull
    public static Button fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        Button button = new Button(Key.of(json.get("key").getAsString()));
        LabeledRect.fromJson(button, json);
        button.setPos(!json.has("pos") ? null : Vec2.fromJson(json.get("pos").getAsJsonObject()));
        button.setAnchor(!json.has("anchor") ? null : Vec2.fromJson(json.get("anchor").getAsJsonObject()));
        button.setOffset(!json.has("offset") ? null : Vec2.fromJson(json.get("offset").getAsJsonObject()));
        button.setRotation(!json.has("rotation") ? null : json.get("rotation").getAsFloat());
        button.setScale(!json.has("scale") ? null : json.get("scale").getAsFloat());
        button.setSize(!json.has("size") ? null : Vec2.fromJson(json.get("size").getAsJsonObject()));
        button.setLabel(!json.has("label") ? null : GsonComponentSerializer.gson().deserialize(json.get("label").getAsString()));
        button.setTooltip(!json.has("tooltip") ? null : GsonComponentSerializer.gson().deserialize(json.get("tooltip").getAsString()));
        return button;
    }

    /**
     * Executable functional interface to fire when a button is clicked.
     */
    @FunctionalInterface
    public interface OnClick extends TriConsumer<Screen, Button, WrappedPlayer> {
        /**
         * Called when a button is clicked.
         *
         * @param screen Active screen where button was clicked
         * @param button Button that was clicked
         * @param player Player that clicked the button
         */
        void accept(@NotNull Screen screen, @NotNull Button button, @NotNull WrappedPlayer player);
    }
}
