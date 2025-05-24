package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.api.util.QuadConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a checkbox element.
 */
public class Checkbox extends LabeledRect<Checkbox> {
    private Boolean selected;

    private OnToggled onToggled = (screen, checkbox, player, selected) -> {
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

    /**
     * Get the selected state.
     * <p>
     * If null, default <code>false</code> state will be used.
     *
     * @return True if selected
     */
    @Nullable
    public Boolean isSelected() {
        return this.selected;
    }

    /**
     * Set the selected state.
     * <p>
     * If null, default <code>false</code> state will be used.
     *
     * @param selected Selected state
     * @return This checkbox
     */
    @NotNull
    public Checkbox setSelected(@Nullable Boolean selected) {
        this.selected = selected;
        return this;
    }

    /**
     * Get the action to execute when the checkbox is toggled.
     * <p>
     * If null, no toggle action will be used.
     *
     * @return Toggled action
     */
    @Nullable
    public OnToggled onToggled() {
        return this.onToggled;
    }

    /**
     * Set the action to execute when the checkbox is toggled.
     * <p>
     * If null, no toggle action will be used.
     *
     * @param onToggled Toggled action
     * @return This checkbox
     */
    @NotNull
    public Checkbox onToggled(@Nullable OnToggled onToggled) {
        this.onToggled = onToggled;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Checkbox other = (Checkbox) obj;
        return Objects.equals(isSelected(), other.isSelected())
                && Objects.equals(onToggled(), other.onToggled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                isSelected(),
                onToggled()
        );
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("label", getLabel());
        json.addProperty("tooltip", getTooltip());
        json.addProperty("selected", isSelected());
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
        checkbox.setSelected(!json.has("selected") ? null : json.get("selected").getAsBoolean());
        return checkbox;
    }

    /**
     * Executable functional interface to fire when a checkbox is toggled.
     */
    @FunctionalInterface
    public interface OnToggled extends QuadConsumer<Screen, Checkbox, WrappedPlayer, Boolean> {
        /**
         * Called when a checkbox is toggled.
         *
         * @param screen   Active screen where checkbox was toggled
         * @param checkbox Checkbox that was toggled
         * @param player   Player that toggled the checkbox
         * @param selected New selected state of the checkbox
         */
        void accept(@NotNull Screen screen, @NotNull Checkbox checkbox, @NotNull WrappedPlayer player, @NotNull Boolean selected);
    }
}
