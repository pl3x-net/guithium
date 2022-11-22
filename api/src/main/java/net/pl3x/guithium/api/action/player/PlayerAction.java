package net.pl3x.guithium.api.action.player;

import com.google.common.base.Preconditions;
import net.pl3x.guithium.api.action.Action;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an action performed by a player.
 */
public abstract class PlayerAction extends Action {
    private final WrappedPlayer player;

    /**
     * Creates a new player action.
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
