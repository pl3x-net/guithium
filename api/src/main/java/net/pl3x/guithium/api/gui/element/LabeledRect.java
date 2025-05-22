package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.pl3x.guithium.api.Unsafe;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a labeled rectangle element with an optional tooltip.
 *
 * @param <T> Type of labeled rectangle element
 */
public abstract class LabeledRect<T extends LabeledRect<T>> extends Rect<T> {
    private Component label;
    private Component tooltip;

    /**
     * Create a new checkbox element.
     *
     * @param key Unique identifier
     */
    protected LabeledRect(@NotNull Key key) {
        super(key);
    }

    /**
     * Get the text label.
     * <p>
     * If null, default empty label will be used.
     *
     * @return Text label
     */
    @Nullable
    public Component getLabel() {
        return this.label;
    }

    /**
     * Set the text label.
     * <p>
     * If null, default empty label will be used.
     *
     * @param label Text label to set
     * @return This element
     */
    @NotNull
    public T setLabel(@Nullable String label) {
        return setLabel(label == null ? null : Component.translatable(label));
    }

    /**
     * Set the text label.
     * <p>
     * If null, default empty label will be used.
     *
     * @param label Text label to set
     * @return This element
     */
    @NotNull
    public T setLabel(@Nullable Component label) {
        this.label = label;
        return Unsafe.cast(this);
    }

    /**
     * Get the text for hover tooltip.
     * <p>
     * If null, no tooltip will be used.
     *
     * @return Tooltip text
     */
    @Nullable
    public Component getTooltip() {
        return this.tooltip;
    }

    /**
     * Set the text for hover tooltip.
     * <p>
     * If null, no tooltip will be used.
     *
     * @param tooltip Tooltip text to set
     * @return This element
     */
    @NotNull
    public T setTooltip(@Nullable String tooltip) {
        return setTooltip(tooltip == null ? null : Component.translatable(tooltip));
    }

    /**
     * Set the text for hover tooltip.
     * <p>
     * If null, no tooltip will be used.
     *
     * @param tooltip Tooltip text to set
     * @return This element
     */
    @NotNull
    public T setTooltip(@Nullable Component tooltip) {
        this.tooltip = tooltip;
        return Unsafe.cast(this);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        T other = Unsafe.cast(obj);
        return Objects.equals(getLabel(), other.getLabel())
                && Objects.equals(getTooltip(), other.getTooltip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getLabel(),
                getTooltip()
        );
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
     * Populate a labeled rectangle from Json.
     *
     * @param element A labeled rectangle to populate
     * @param json    Json representation of a labeled rectangle
     */
    public static void fromJson(@NotNull LabeledRect<?> element, @NotNull JsonObject json) {
        Rect.fromJson(element, json);
        element.setLabel(!json.has("label") ? null : GsonComponentSerializer.gson().deserialize(json.get("label").getAsString()));
        element.setTooltip(!json.has("tooltip") ? null : GsonComponentSerializer.gson().deserialize(json.get("tooltip").getAsString()));
    }
}
