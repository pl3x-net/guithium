package net.pl3x.guithium.api.action.actions.player.screen.element;

import java.util.ArrayList;
import java.util.List;
import net.pl3x.guithium.api.action.RegisteredHandler;
import net.pl3x.guithium.api.action.actions.Cancellable;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Action that fires when a checkbox is toggled.
 *
 * @param <T> Type of element
 */
public class ElementToggledAction<T extends Element> extends ElementClickedAction<T> implements Cancellable {
    private static final List<RegisteredHandler> handlers = new ArrayList<>();

    private boolean selected;

    /**
     * Create a new action for when a togglable element is toggled.
     *
     * @param player   Player that performed the action
     * @param screen   Screen action was performed on
     * @param element  Element action was performed on
     * @param selected New selected state of element
     */
    public ElementToggledAction(@NotNull WrappedPlayer player, @NotNull Screen screen, @NotNull T element, boolean selected) {
        super(player, screen, element);
        this.selected = selected;
    }

    /**
     * Get the new selected state of this element.
     *
     * @return New element selected state
     */
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * Set the new selected state of this element.
     *
     * @param selected New element selected state
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    @NotNull
    public List<RegisteredHandler> getHandlers() {
        return handlers;
    }
}
