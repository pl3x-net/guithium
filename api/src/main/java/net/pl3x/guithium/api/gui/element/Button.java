package net.pl3x.guithium.api.gui.element;

import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.api.util.TriConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a button element.
 */
public class Button extends LabeledRect<Button> {
    /**
     * Create a new button element.
     *
     * @param key Unique identifier for element
     */
    public Button(@NotNull String key) {
        this(Key.of(key));
    }

    /**
     * Create a new button element.
     *
     * @param key Unique identifier for element
     */
    public Button(@NotNull Key key) {
        super(key, Type.BUTTON);
    }

    /**
     * Create a new button element.
     *
     * @param key Unique identifier
     * @return New button element
     */
    @NotNull
    public static Button of(@NotNull String key) {
        return of(Key.of(key));
    }

    /**
     * Create a new button element.
     *
     * @param key Unique identifier
     * @return New button element
     */
    @NotNull
    public static Button of(@NotNull Key key) {
        return new Button(key);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        // pacifies codefactor.io
        return super.hashCode();
    }

    /**
     * Executable functional interface to fire when a button is clicked.
     */
    @FunctionalInterface
    public interface OnClick extends TriConsumer<Screen, Button, WrappedPlayer> {
        /**
         * Called when a button is clicked.
         *
         * @param screen Active screen where button was clicked
         * @param button Button that was clicked
         * @param player Player that clicked the button
         */
        void accept(@NotNull Screen screen, @NotNull Button button, @NotNull WrappedPlayer player);
    }
}
