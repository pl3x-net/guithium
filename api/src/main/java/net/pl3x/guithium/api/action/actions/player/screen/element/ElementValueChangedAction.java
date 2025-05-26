package net.pl3x.guithium.api.action.actions.player.screen.element;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import net.pl3x.guithium.api.action.RegisteredHandler;
import net.pl3x.guithium.api.action.actions.Cancellable;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.ValueElement;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Action that fires when an element's value is changed.
 *
 * @param <T> Type of element
 * @param <V> Type of value
 */
public class ElementValueChangedAction<T extends ValueElement<T, V>, V> extends ElementClickedAction<T> implements Cancellable {
    private static final List<RegisteredHandler> handlers = new ArrayList<>();

    private V value;

    /**
     * Create a new action for when an element's value is changed.
     *
     * @param player  Player that performed the action
     * @param screen  Screen action was performed on
     * @param element Element action was performed on
     * @param value   New value of element
     */
    public ElementValueChangedAction(@NotNull WrappedPlayer player, @NotNull Screen screen, @NotNull T element, @NotNull V value) {
        super(player, screen, element);
        setValue(value);
    }

    /**
     * Get the new value of this element.
     *
     * @return New element value
     */
    @NotNull
    public V getValue() {
        return this.value;
    }

    /**
     * Set the new value of this element.
     *
     * @param value New element value
     */
    public void setValue(@NotNull V value) {
        Preconditions.checkNotNull(value, "value cannot be null");
        this.value = value;
    }

    @Override
    @NotNull
    public List<RegisteredHandler> getHandlers() {
        return handlers;
    }
}
