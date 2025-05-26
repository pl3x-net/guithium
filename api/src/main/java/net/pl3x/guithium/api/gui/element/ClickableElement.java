package net.pl3x.guithium.api.gui.element;

import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.api.util.TriConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an element that can be clicked, such as a button.
 *
 * @param <T> Type of element
 */
public interface ClickableElement<T extends ClickableElement<T>> extends Element {
    /**
     * Get the action to execute when the button is clicked.
     * <p>
     * If null, no click action will be used.
     *
     * @return OnClick action
     */
    @Nullable
    OnClick<T> onClick();

    /**
     * Set the action to execute when the button is clicked.
     * <p>
     * If null, no click action will be used.
     *
     * @param onClick OnClick action
     * @return This button
     */
    @NotNull
    T onClick(@Nullable OnClick<T> onClick);

    /**
     * Executable functional interface to fire when a button is clicked.
     *
     * @param <T> Type of element
     */
    @FunctionalInterface
    interface OnClick<T extends ClickableElement<T>> extends TriConsumer<Screen, T, WrappedPlayer> {
        /**
         * Called when an element is clicked.
         *
         * @param screen  Active screen where element was clicked
         * @param element Element that was clicked
         * @param player  Player that clicked the element
         */
        void accept(@NotNull Screen screen, @NotNull T element, @NotNull WrappedPlayer player);
    }
}
