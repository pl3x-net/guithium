package net.pl3x.guithium.api.action.actions;

import net.pl3x.guithium.api.action.RegisteredHandler;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Action that fires when a screen is closed.
 */
public class CloseScreenAction extends ScreenAction {
    private static final List<RegisteredHandler> handlers = new ArrayList<>();

    /**
     * Creates a new action for when a screen is closed.
     *
     * @param player Player that performed the action
     * @param screen Screen action was performed on
     */
    public CloseScreenAction(WrappedPlayer player, Screen screen) {
        super(player, screen);
    }

    @Override
    public @NotNull List<RegisteredHandler> getHandlers() {
        return handlers;
    }
}
