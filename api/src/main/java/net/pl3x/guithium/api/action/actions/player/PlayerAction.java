package net.pl3x.guithium.api.action.actions.player;

import com.google.common.base.Preconditions;
import net.pl3x.guithium.api.action.actions.Action;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Action that fires when a player performs an action.
 */
public abstract class PlayerAction extends Action {
    private final WrappedPlayer player;

    /**
     * Create a new action for when a player performs an action.
     *
     * @param player Player that performed the action
     */
    public PlayerAction(@NotNull WrappedPlayer player) {
        Preconditions.checkNotNull(player, "Player cannot be null.");
        this.player = player;
    }

    /**
     * Get the player that performed this action.
     *
     * @return Player
     */
    @NotNull
    public WrappedPlayer getPlayer() {
        return this.player;
    }
}
