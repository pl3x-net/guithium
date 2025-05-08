package net.pl3x.guithium.api.gui.element;

import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.api.key.Keyed;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an element to be displayed on a {@link net.pl3x.guithium.api.gui.Screen}.
 */
public abstract class Element extends Keyed {
    /**
     * Create a new element
     *
     * @param key unique identifier
     */
    public Element(@NotNull Key key) {
        super(key);
    }

    @Override
    @NotNull
    public String toString() {
        return "Element {"
                + "key=" + getKey()
                + "}";
    }
}
