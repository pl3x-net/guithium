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
 * Represents a slider element.
 */
public class Slider extends LabeledRect<Slider> implements ValueElement<Slider, Double> {
    private Double value;
    private Double min;
    private Double max;
    private String decimal;

    private OnChange<Slider, Double> onChange = (screen, slider, player, value) -> {
    };

    /**
     * Create a new slider element.
     *
     * @param key Unique identifier
     */
    public Slider(@NotNull String key) {
        this(Key.of(key));
    }

    /**
     * Create a new slider element.
     *
     * @param key Unique identifier
     */
    public Slider(@NotNull Key key) {
        super(key);
    }

    /**
     * Create a new slider element.
     *
     * @param key Unique identifier
     * @return New slider element
     */
    @NotNull
    public static Slider of(@NotNull String key) {
        return of(Key.of(key));
    }

    /**
     * Create a new slider element.
     *
     * @param key Unique identifier
     * @return New slider element
     */
    @NotNull
    public static Slider of(@NotNull Key key) {
        return new Slider(key);
    }

    /**
     * Get the minimum value of this slider.
     * <p>
     * If null, default minimum of <code>0.0</code> will be used.
     *
     * @return Slider's minimum value
     */
    @Nullable
    public Double getMin() {
        return this.min;
    }

    /**
     * Set the minimum value of this slider.
     * <p>
     * If null, default minimum of <code>0.0</code> will be used.
     *
     * @param min Slider's minimum value
     * @return This slider
     */
    @NotNull
    public Slider setMin(@Nullable Double min) {
        this.min = min;
        return this;
    }

    /**
     * Get the maximum value of this slider.
     * <p>
     * If null, default maximum of <code>1.0</code> will be used.
     *
     * @return Slider's maximum value
     */
    @Nullable
    public Double getMax() {
        return this.max;
    }

    /**
     * Set the maximum value of this slider.
     * <p>
     * If null, default maximum of <code>1.0</code> will be used.
     *
     * @param max Slider's maximum value
     * @return This slider
     */
    @NotNull
    public Slider setMax(@Nullable Double max) {
        this.max = max;
        return this;
    }

    /**
     * Get the decimal format for this slider's value.
     * <p>
     * If null, no decimal format will be used.
     *
     * @return Decimal format
     * @see java.text.DecimalFormat
     */
    @Nullable
    public String getDecimalFormat() {
        return this.decimal;
    }

    /**
     * Set the decimal format for this slider's value.
     * <p>
     * If null, no decimal format will be used.
     *
     * @param decimal Decimal format
     * @return This slider
     * @see java.text.DecimalFormat
     */
    @NotNull
    public Slider setDecimalFormat(@Nullable String decimal) {
        this.decimal = decimal;
        return this;
    }

    @Override
    @NotNull
    public Double getValue() {
        return this.value == null ? 0 : this.value;
    }

    @Override
    @NotNull
    public Slider setValue(@NotNull Double value) {
        this.value = value;
        return this;
    }

    @Override
    @Nullable
    public OnChange<Slider, Double> onChange() {
        return this.onChange;
    }

    @Override
    @NotNull
    public Slider onChange(@Nullable OnChange<Slider, Double> onChange) {
        this.onChange = onChange;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Slider other = (Slider) obj;
        return Objects.equals(getValue(), other.getValue())
                && Objects.equals(getMin(), other.getMin())
                && Objects.equals(getMax(), other.getMax())
                && Objects.equals(getDecimalFormat(), other.getDecimalFormat())
                && Objects.equals(onChange(), other.onChange());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getValue(),
                getMin(),
                getMax(),
                getDecimalFormat(),
                onChange()
        );
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("value", getValue());
        json.addProperty("min", getMin());
        json.addProperty("max", getMax());
        json.addProperty("decimal", getDecimalFormat());
        return json.getJsonObject();
    }

    /**
     * Create a new slider from Json.
     *
     * @param json Json representation of a slider
     * @return A new slider
     */
    @NotNull
    public static Slider fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        Slider slider = new Slider(Key.of(json.get("key").getAsString()));
        LabeledRect.fromJson(slider, json);
        slider.setValue(!json.has("value") ? 0D : json.get("value").getAsDouble());
        slider.setMin(!json.has("min") ? 0D : json.get("min").getAsDouble());
        slider.setMax(!json.has("max") ? 1D : json.get("max").getAsDouble());
        slider.setDecimalFormat(!json.has("decimal") ? null : json.get("decimal").getAsString());
        return slider;
    }
}
