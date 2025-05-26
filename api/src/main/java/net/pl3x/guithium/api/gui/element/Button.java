package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a button element.
 */
public class Button extends LabeledRect<Button> implements ClickableElement<Button> {
    private OnClick<Button> onClick = (screen, button, player) -> {
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

    @Override
    @Nullable
    public OnClick<Button> onClick() {
        return this.onClick;
    }

    @Override
    @NotNull
    public Button onClick(@Nullable OnClick<Button> onClick) {
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
        return button;
    }
}
