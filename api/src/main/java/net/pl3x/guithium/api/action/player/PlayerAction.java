package net.pl3x.guithium.api.action.player;

import com.google.common.base.Preconditions;
import net.pl3x.guithium.api.action.Action;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

public abstract class PlayerAction extends Action {
    private final WrappedPlayer player;

    public PlayerAction(@NotNull WrappedPlayer player) {
        Preconditions.checkNotNull(player, "Player cannot be null.");
        this.player = player;
    }

    @NotNull
    public WrappedPlayer getPlayer() {
        return this.player;
    }
}
