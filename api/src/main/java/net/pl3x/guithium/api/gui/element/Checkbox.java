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
 * Represents a checkbox element.
 */
public class Checkbox extends LabeledRect<Checkbox> implements ValueElement<Checkbox, Boolean> {
    private Boolean value;

    private OnChange<Checkbox, Boolean> onChange = (screen, checkbox, player, value) -> {
    };

    /**
     * Create a new checkbox element.
     *
     * @param key Unique identifier
     */
    public Checkbox(@NotNull String key) {
        this(Key.of(key));
    }

    /**
     * Create a new checkbox element.
     *
     * @param key Unique identifier
     */
    public Checkbox(@NotNull Key key) {
        super(key);
    }

    /**
     * Create a new checkbox element.
     *
     * @param key Unique identifier
     * @return New checkbox element
     */
    @NotNull
    public static Checkbox of(@NotNull String key) {
        return of(Key.of(key));
    }

    /**
     * Create a new checkbox element.
     *
     * @param key Unique identifier
     * @return New checkbox element
     */
    @NotNull
    public static Checkbox of(@NotNull Key key) {
        return new Checkbox(key);
    }

    @Override
    @NotNull
    public Boolean getValue() {
        return this.value;
    }

    @Override
    @NotNull
    public Checkbox setValue(@NotNull Boolean value) {
        this.value = value;
        return this;
    }

    @Override
    @Nullable
    public OnChange<Checkbox, Boolean> onChange() {
        return this.onChange;
    }

    @Override
    @NotNull
    public Checkbox onChange(@Nullable OnChange<Checkbox, Boolean> onChange) {
        this.onChange = onChange;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Checkbox other = (Checkbox) obj;
        return Objects.equals(getValue(), other.getValue())
                && Objects.equals(onChange(), other.onChange());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getValue(),
                onChange()
        );
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("label", getLabel());
        json.addProperty("tooltip", getTooltip());
        json.addProperty("value", getValue());
        return json.getJsonObject();
    }

    /**
     * Create a new checkbox from Json.
     *
     * @param json Json representation of a checkbox
     * @return A new checkbox
     */
    @NotNull
    public static Checkbox fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        Checkbox checkbox = new Checkbox(Key.of(json.get("key").getAsString()));
        LabeledRect.fromJson(checkbox, json);
        checkbox.setValue(json.has("value") && json.get("value").getAsBoolean());
        return checkbox;
    }
}
