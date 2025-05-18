package net.pl3x.guithium.api.gui.element;

import java.util.Objects;
import net.pl3x.guithium.api.gui.Screen;
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
    private Boolean showLabel;
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
        super(key, Type.CHECKBOX);
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
     * Get if we should show text label.
     * <p>
     * If null, text label will be shown.
     *
     * @return True to show text label
     */
    @Nullable
    public Boolean isShowLabel() {
        return this.showLabel;
    }

    /**
     * Set if we should show text label.
     * <p>
     * If null, text label will be shown.
     *
     * @param showLabel True to show text label
     * @return This checkbox
     */
    @NotNull
    public Checkbox setShowLabel(@Nullable Boolean showLabel) {
        this.showLabel = showLabel;
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
     */
    public void onToggled(@Nullable OnToggled onToggled) {
        this.onToggled = onToggled;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Checkbox other = (Checkbox) obj;
        return Objects.equals(isSelected(), other.isSelected())
                && Objects.equals(isShowLabel(), other.isShowLabel());
    }

    @Override
    public int hashCode() {
        // pacifies codefactor.io
        return super.hashCode();
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
