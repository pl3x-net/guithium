package net.pl3x.guithium.api.action.actions.player.screen;

import net.pl3x.guithium.api.action.actions.player.PlayerAction;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Action that fires when a screen action is performed.
 */
public abstract class ScreenAction extends PlayerAction {
    private final Screen screen;

    /**
     * Creates a new action for when a screen action is performed.
     *
     * @param player Player that performed the action
     * @param screen Screen action was performed on
     */
    public ScreenAction(@NotNull WrappedPlayer player, @NotNull Screen screen) {
        super(player);
        this.screen = screen;
    }

    /**
     * Get the screen involved in this action.
     *
     * @return Screen
     */
    @NotNull
    public Screen getScreen() {
        return this.screen;
    }
}
