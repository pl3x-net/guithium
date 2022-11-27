package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.api.util.QuadConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a slider control.
 */
public class Slider extends Rect {
    private String label;
    private Component tooltip;
    private Double value;
    private Double min;
    private Double max;
    private String decimal;
    private OnChange onChange = (screen, slider, player, value) -> {
    };

    /**
     * Creates a new slider control.
     *
     * @param key      Unique identifier for slider
     * @param pos      Position of slider
     * @param anchor   Anchor for slider
     * @param offset   Offset of slider
     * @param rotation Rotation in degrees
     * @param scale    Scale of slider
     * @param size     Size of slider
     * @param label    Text label
     * @param tooltip  Text on hover tooltip
     * @param value    Value of slider
     * @param min      Minimum value
     * @param max      Maximum value
     * @param decimal  Decimal format
     */
    protected Slider(@NotNull Key key, @Nullable Vec2 pos, @Nullable Vec2 anchor, @Nullable Vec2 offset, @Nullable Float rotation, @Nullable Float scale, @Nullable Vec2 size, @Nullable String label, @Nullable Component tooltip, @NotNull Double value, @Nullable Double min, @Nullable Double max, @Nullable String decimal) {
        super(key, Type.SLIDER, pos, anchor, offset, rotation, scale, size);
        setLabel(label);
        setTooltip(tooltip);
        setValue(value);
        setMin(min);
        setMax(max);
        setDecimalFormat(decimal);
    }

    /**
     * Get the text label.
     * <p>
     * Note: You can use variables wrapped in squiggly braces:
     * <ul>
     * <li><code>{value}</code> - shows the current value</li>
     * <li><code>{min}</code> - shows the minimum value</li>
     * <li><code>{max}</code> - shows the maximum value</li>
     * </ul>
     * <p>
     * If null, default empty label will be used.
     *
     * @return Text label
     */
    @Nullable
    public String getLabel() {
        return this.label;
    }

    /**
     * Set the text label.
     * <p>
     * If null, default empty label will be used.
     *
     * @param label Text label
     */
    public void setLabel(@Nullable String label) {
        this.label = label;
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
     */
    public void setTooltip(@Nullable Component tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Get the value of this slider.
     *
     * @return Slider's value
     */
    @NotNull
    public Double getValue() {
        return this.value;
    }

    /**
     * Set the value of this slider.
     *
     * @param value Slider's value
     */
    public void setValue(@NotNull Double value) {
        Preconditions.checkNotNull(value, "Value cannot be null");
        this.value = value;
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
     */
    public void setMin(@Nullable Double min) {
        this.min = min;
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
     */
    public void setMax(@Nullable Double max) {
        this.max = max;
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
     * @see java.text.DecimalFormat
     */
    public void setDecimalFormat(@Nullable String decimal) {
        this.decimal = decimal;
    }

    /**
     * Get the action to execute when the slider is changed.
     * <p>
     * If null, no change action will be used.
     *
     * @return OnClick action
     */
    @Nullable
    public OnChange onChange() {
        return this.onChange;
    }

    /**
     * Set the action to execute when the slider is changed.
     * <p>
     * If null, no change action will be used.
     *
     * @param onChange OnChange action
     */
    public void onClick(@Nullable OnChange onChange) {
        this.onChange = onChange;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("text", getLabel());
        json.addProperty("tooltip", getTooltip());
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
        return new Slider(
            Key.of(json.get("key").getAsString()),
            !json.has("pos") ? null : Vec2.fromJson(json.get("pos").getAsJsonObject()),
            !json.has("anchor") ? null : Vec2.fromJson(json.get("anchor").getAsJsonObject()),
            !json.has("offset") ? null : Vec2.fromJson(json.get("offset").getAsJsonObject()),
            !json.has("rotation") ? null : json.get("rotation").getAsFloat(),
            !json.has("scale") ? null : json.get("scale").getAsFloat(),
            !json.has("size") ? null : Vec2.fromJson(json.get("size").getAsJsonObject()),
            !json.has("text") ? null : json.get("text").getAsString(),
            !json.has("tooltip") ? null : GsonComponentSerializer.gson().deserialize(json.get("tooltip").getAsString()),
            !json.has("value") ? 0D : json.get("value").getAsDouble(),
            !json.has("min") ? 0D : json.get("min").getAsDouble(),
            !json.has("max") ? 1D : json.get("max").getAsDouble(),
            !json.has("decimal") ? null : json.get("decimal").getAsString()
        );
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Slider other = (Slider) o;
        return Objects.equals(getLabel(), other.getLabel())
            && Objects.equals(getTooltip(), other.getTooltip())
            && Objects.equals(getValue(), other.getValue())
            && Objects.equals(getMin(), other.getMin())
            && Objects.equals(getMax(), other.getMax())
            && Objects.equals(getDecimalFormat(), other.getDecimalFormat())
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabel(), getTooltip(), getValue(), getMin(), getMax(), getDecimalFormat(), super.hashCode());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("Slider{%s}", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",text=" + getLabel()
            + ",tooltip=" + getTooltip()
            + ",value=" + getValue()
            + ",min=" + getMin()
            + ",max=" + getMax()
            + ",decimal=" + getDecimalFormat();
    }

    /**
     * Create a new slider builder.
     *
     * @param key Unique identifying key for the slider
     * @return New slider builder
     */
    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    /**
     * Create a new slider builder.
     *
     * @param key Unique identifying key for the slider
     * @return New slider builder
     */
    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    /**
     * Builder for sliders.
     */
    public static class Builder extends Rect.Builder<Builder> {
        private String text;
        private Component tooltip;
        private Double value;
        private Double min;
        private Double max;
        private String decimal;
        private OnChange onChange = (screen, slider, player, value) -> {
        };

        /**
         * Create a new slider builder.
         *
         * @param key Unique identifying key for the slider
         */
        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        /**
         * Create a new slider builder.
         *
         * @param key Unique identifying key for the slider
         */
        public Builder(@NotNull Key key) {
            super(key);
        }

        /**
         * Get the text label on the slider.
         * <p>
         * If null, default empty label will be used.
         *
         * @return Text on face
         */
        @Nullable
        public String getLabel() {
            return text;
        }

        /**
         * Set the text label on the slider.
         * <p>
         * If null, default empty label will be used.
         *
         * @param text Text to set
         * @return This builder
         */
        @NotNull
        public Builder setLabel(@Nullable String text) {
            this.text = text;
            return this;
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
            return tooltip;
        }

        /**
         * Set the text for hover tooltip.
         * <p>
         * If null, no tooltip will be used.
         *
         * @param tooltip Tooltip text to set
         * @return This builder
         */
        @NotNull
        public Builder setTooltip(@Nullable Component tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        /**
         * Get the value of this slider.
         *
         * @return Slider's value
         */
        @NotNull
        public Double getValue() {
            return this.value;
        }

        /**
         * Set the value of this slider.
         *
         * @param value Slider's value
         * @return This builder
         */
        @NotNull
        public Builder setValue(@NotNull Double value) {
            Preconditions.checkNotNull(value, "Value cannot be null");
            this.value = value;
            return this;
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
         * @return This builder
         */
        public Builder setMin(@Nullable Double min) {
            this.min = min;
            return this;
        }

        /**
         * Get the maximum value of this slider.
         * <p>
         * If null, default maximum <code>1.0</code> will be used.
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
         * @return This builder
         */
        public Builder setMax(@Nullable Double max) {
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
         * @return This builder
         * @see java.text.DecimalFormat
         */
        public Builder setDecimalFormat(@Nullable String decimal) {
            this.decimal = decimal;
            return this;
        }

        /**
         * Get the action to execute when the slider is changed.
         * <p>
         * If null, no change action will be used.
         *
         * @return OnChanged action
         */
        @Nullable
        public OnChange onChange() {
            return this.onChange;
        }

        /**
         * Set the action to execute when the slider is changed.
         * <p>
         * If null, no change action will be used.
         *
         * @param onChange OnChange action
         * @return This builder
         */
        @NotNull
        public Builder onChange(@Nullable OnChange onChange) {
            this.onChange = onChange;
            return this;
        }

        /**
         * Build a new slider from the current properties in this builder.
         *
         * @return New slider
         */
        @Override
        @NotNull
        public Slider build() {
            Slider slider = new Slider(getKey(), getPos(), getAnchor(), getOffset(), getRotation(), getScale(), getSize(), getLabel(), getTooltip(), getValue(), getMin(), getMax(), getDecimalFormat());
            slider.onClick(this.onChange);
            return slider;
        }
    }

    /**
     * Executable functional interface to fire when a slider is changed.
     */
    @FunctionalInterface
    public interface OnChange extends QuadConsumer<Screen, Slider, WrappedPlayer, Double> {
        /**
         * Called when a slider is changed.
         *
         * @param screen Active screen where slider was changed
         * @param slider Slider that was changed
         * @param player Player that changed the slider
         * @param value  New value of the slider
         */
        void accept(@NotNull Screen screen, @NotNull Slider slider, @NotNull WrappedPlayer player, @NotNull Double value);
    }
}
