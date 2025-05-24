package net.pl3x.guithium.api.gui.element;

import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.api.util.QuadConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an element that contains a value.
 *
 * @param <T> Type of element
 * @param <V> Type of value
 */
public interface ValueElement<T extends ValueElement<T, V>, V> extends ClickableElement<T> {
    /**
     * Get the value of this element.
     *
     * @return Element's value
     */
    @NotNull
    V getValue();

    /**
     * Set the value of this element.
     *
     * @param value Element's value
     * @return This element
     */
    @NotNull
    T setValue(@NotNull V value);

    /**
     * Get the action to execute when the element's value is changed.
     * <p>
     * If null, no change action will be used.
     *
     * @return OnClick action
     */
    @Nullable
    OnChange<T, V> onChange();

    /**
     * Set the action to execute when the element's value is changed.
     * <p>
     * If null, no change action will be used.
     *
     * @param onChange OnChange action
     * @return This element
     */
    @NotNull
    T onChange(@Nullable OnChange<T, V> onChange);

    /**
     * Executable functional interface to fire when an element's value is changed.
     *
     * @param <T> Type of element
     * @param <V> Type of value
     */
    @FunctionalInterface
    interface OnChange<T extends ValueElement<T, V>, V> extends QuadConsumer<Screen, T, WrappedPlayer, V> {
        /**
         * Called when an element's value is changed.
         *
         * @param screen  Active screen where element was changed
         * @param element Element that was changed
         * @param player  Player that changed the element
         * @param value   New value of the element
         */
        void accept(@NotNull Screen screen, @NotNull T element, @NotNull WrappedPlayer player, @NotNull V value);
    }
}
