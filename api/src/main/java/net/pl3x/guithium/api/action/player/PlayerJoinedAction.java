package net.pl3x.guithium.api.action.player;

import net.pl3x.guithium.api.action.RegisteredHandler;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlayerJoinedAction extends PlayerAction {
    private static final List<RegisteredHandler> handlers = new ArrayList<>();

    public PlayerJoinedAction(@NotNull WrappedPlayer player) {
        super(player);
    }

    @Override
    @NotNull
    public List<RegisteredHandler> getHandlers() {
        return handlers;
    }
}
