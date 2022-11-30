package net.pl3x.guithium.api.action.actions;

import net.pl3x.guithium.api.action.RegisteredHandler;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Action that fires when a player joins the server.
 * <p>
 * This is different from Bukkit's PlayerJoinEvent because with this action
 * the player is guaranteed to be ready to receive packets from the server.
 */
public class PlayerJoinedAction extends PlayerAction {
    private static final List<RegisteredHandler> handlers = new ArrayList<>();

    /**
     * Creates a new action for when a player joins the server.
     *
     * @param player Player that performed the action
     */
    public PlayerJoinedAction(@NotNull WrappedPlayer player) {
        super(player);
    }

    @Override
    @NotNull
    public List<RegisteredHandler> getHandlers() {
        return handlers;
    }
}
