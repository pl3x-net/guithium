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
 * Represents a radio box element.
 */
public class Radio extends LabeledRect<Radio> implements ValueElement<Radio, Boolean> {
    private Key group;
    private Boolean value;

    private OnChange<Radio, Boolean> onChange = (screen, radio, player, value) -> {
    };

    /**
     * Create a new radio box element.
     *
     * @param key Unique identifier
     */
    public Radio(@NotNull String key) {
        this(Key.of(key));
    }

    /**
     * Create a new radio box element.
     *
     * @param key Unique identifier
     */
    public Radio(@NotNull Key key) {
        super(key);
    }

    /**
     * Create a new radio box element.
     *
     * @param key Unique identifier
     * @return New radio box element
     */
    @NotNull
    public static Radio of(@NotNull String key) {
        return of(Key.of(key));
    }

    /**
     * Create a new radio box element.
     *
     * @param key Unique identifier
     * @return New radio box element
     */
    @NotNull
    public static Radio of(@NotNull Key key) {
        return new Radio(key);
    }

    /**
     * Get the group this radio button belongs to.
     * <p>
     * Only one radio button can be selected in a group at any given time.
     * <p>
     * If null, no group will be used.
     *
     * @return This radio button's group
     */
    @Nullable
    public Key getGroup() {
        return this.group;
    }

    /**
     * Set the group this radio button belongs to.
     * <p>
     * Only one radio button can be selected in a group at any given time.
     * <p>
     * If null, no group will be used.
     *
     * @param group This radio button's group
     * @return This radio button
     */
    @NotNull
    public Radio setGroup(@Nullable Key group) {
        this.group = group;
        return this;
    }

    @Override
    @NotNull
    public Boolean getValue() {
        return this.value;
    }

    @Override
    @NotNull
    public Radio setValue(@NotNull Boolean value) {
        this.value = value;
        return this;
    }

    @Override
    @Nullable
    public OnChange<Radio, Boolean> onChange() {
        return this.onChange;
    }

    @Override
    @NotNull
    public Radio onChange(@Nullable OnChange<Radio, Boolean> onChange) {
        this.onChange = onChange;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Radio other = (Radio) obj;
        return Objects.equals(getGroup(), other.getGroup())
                && Objects.equals(getValue(), other.getValue())
                && Objects.equals(onChange(), other.onChange());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getGroup(),
                getValue(),
                onChange()
        );
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("group", getGroup());
        json.addProperty("value", getValue());
        return json.getJsonObject();
    }

    /**
     * Create a new radio button from Json.
     *
     * @param json Json representation of a radio button
     * @return A new radio button
     */
    @NotNull
    public static Radio fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        Radio radio = new Radio(Key.of(json.get("key").getAsString()));
        LabeledRect.fromJson(radio, json);
        radio.setGroup(!json.has("group") ? null : Key.of(json.get("group").getAsString()));
        radio.setValue(json.has("value") && json.get("value").getAsBoolean());
        return radio;
    }
}
