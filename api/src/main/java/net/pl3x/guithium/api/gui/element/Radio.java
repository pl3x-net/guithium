package net.pl3x.guithium.api.gui.element;

import java.util.Objects;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.api.util.QuadConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a radio box element.
 */
public class Radio extends LabeledRect<Radio> {
    private Key group;
    private Boolean selected;
    private Boolean showLabel;
    private OnToggled onToggled = (screen, radio, player, selected) -> {
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
        super(key, Type.RADIO);
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

    /**
     * Get the selected state.
     * <p>
     * Only one radio can have a <code>true</code> selected state in a group.
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
     * Setting this to true, will set all other radios in the same group to false.
     * <p>
     * If null, default <code>false</code> state will be used.
     *
     * @param selected Selected state
     * @return This radio button
     */
    @NotNull
    public Radio setSelected(@Nullable Boolean selected) {
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
     * @return This radio button
     */
    @NotNull
    public Radio setShowLabel(@Nullable Boolean showLabel) {
        this.showLabel = showLabel;
        return this;
    }

    /**
     * Get the action to execute when the radio button is toggled.
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
     * Set the action to execute when the radio button is toggled.
     * <p>
     * If null, no toggle action will be used.
     *
     * @param onToggled Toggled action
     * @return This radio button
     */
    @NotNull
    public Radio onToggled(@Nullable OnToggled onToggled) {
        this.onToggled = onToggled;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Radio other = (Radio) obj;
        return Objects.equals(getGroup(), other.getGroup())
                && Objects.equals(isSelected(), other.isSelected())
                && Objects.equals(isShowLabel(), other.isShowLabel())
                && Objects.equals(onToggled(), other.onToggled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getGroup(),
                isSelected(),
                isShowLabel(),
                onToggled()
        );
    }

    /**
     * Executable functional interface to fire when a radio button is toggled.
     */
    @FunctionalInterface
    public interface OnToggled extends QuadConsumer<Screen, Radio, WrappedPlayer, Boolean> {
        /**
         * Called when a radio button is toggled.
         *
         * @param screen   Active screen where radio button was toggled
         * @param radio    Radio button that was toggled
         * @param player   Player that toggled the radio button
         * @param selected New selected state of the radio button
         */
        void accept(@NotNull Screen screen, @NotNull Radio radio, @NotNull WrappedPlayer player, @NotNull Boolean selected);
    }
}
