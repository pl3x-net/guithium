package net.pl3x.guithium.api.gui.element;

import java.util.Objects;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.api.util.QuadConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a slider element.
 */
public class Slider extends LabeledRect<Slider> {
    private Double value;
    private Double min;
    private Double max;
    private String decimal;

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
        super(key, Type.CHECKBOX);
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
     * Get the value of this slider.
     * <p>
     * If null, default value <code>0</code> will be used.
     *
     * @return Slider's value
     */
    @Nullable
    public Double getValue() {
        return this.value;
    }

    /**
     * Set the value of this slider.
     * <p>
     * If null, default value <code>0</code> will be used.
     *
     * @param value Slider's value
     * @return This slider
     */
    @NotNull
    public Slider setValue(@Nullable Double value) {
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
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Slider other = (Slider) obj;
        return Objects.equals(getValue(), other.getValue())
                && Objects.equals(getMin(), other.getMin())
                && Objects.equals(getMax(), other.getMax())
                && Objects.equals(getDecimalFormat(), other.getDecimalFormat());
    }

    @Override
    public int hashCode() {
        // pacifies codefactor.io
        return super.hashCode();
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
