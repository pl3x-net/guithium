package net.pl3x.guithium.api.gui.element;

import java.util.Objects;
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
    private OnClick onClick = (screen, button, player) -> {
    };

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

    /**
     * Get the action to execute when the button is clicked.
     * <p>
     * If null, no click action will be used.
     *
     * @return OnClick action
     */
    @Nullable
    public OnClick onClick() {
        return this.onClick;
    }

    /**
     * Set the action to execute when the button is clicked.
     * <p>
     * If null, no click action will be used.
     *
     * @param onClick OnClick action
     * @return This button
     */
    @NotNull
    public Button onClick(@Nullable OnClick onClick) {
        this.onClick = onClick;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Button other = (Button) obj;
        return Objects.equals(onClick(), other.onClick());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), onClick());
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
