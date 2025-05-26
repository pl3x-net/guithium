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
 * Action that fires when a clickable element is clicked.
 *
 * @param <T> Type of element
 */
public class ElementClickedAction<T extends Element> extends ElementAction<T> implements Cancellable {
    private static final List<RegisteredHandler> handlers = new ArrayList<>();

    private boolean cancelled;

    /**
     * Create a new action for when a clickable element is clicked.
     *
     * @param player  Player that performed the action
     * @param screen  Screen action was performed on
     * @param element Element action was performed on
     */
    public ElementClickedAction(@NotNull WrappedPlayer player, @NotNull Screen screen, @NotNull T element) {
        super(player, screen, element);
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    @NotNull
    public List<RegisteredHandler> getHandlers() {
        return handlers;
    }
}
