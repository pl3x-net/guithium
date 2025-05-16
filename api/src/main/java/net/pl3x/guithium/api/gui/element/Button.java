package net.pl3x.guithium.api.gui.element;

import net.pl3x.guithium.api.key.Key;
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
}
