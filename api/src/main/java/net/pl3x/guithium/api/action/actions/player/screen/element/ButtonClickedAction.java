package net.pl3x.guithium.api.action.actions.player.screen.element;

import java.util.ArrayList;
import java.util.List;
import net.pl3x.guithium.api.action.RegisteredHandler;
import net.pl3x.guithium.api.action.actions.Cancellable;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Action that fires when a button is clicked.
 */
public class ButtonClickedAction extends ElementAction implements Cancellable {
    private static final List<RegisteredHandler> handlers = new ArrayList<>();

    private boolean cancelled;

    /**
     * Create a new action for when a button is clicked.
     *
     * @param player Player that performed the action
     * @param screen Screen action was performed on
     * @param button Button action was performed on
     */
    public ButtonClickedAction(@NotNull WrappedPlayer player, @NotNull Screen screen, @NotNull Button button) {
        super(player, screen, button);
    }

    /**
     * Get the button that was clicked.
     *
     * @return Clicked button
     */
    @NotNull
    public Button getElement() {
        return (Button) super.getElement();
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
