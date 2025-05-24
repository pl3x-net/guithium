package net.pl3x.guithium.plugin.player;

import net.pl3x.guithium.api.player.PlayerManager;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PaperPlayerManager extends PlayerManager {
    @Override
    @NotNull
    public <T> WrappedPlayer add(@NotNull T player) {
        if (!(player instanceof Player bukkit)) {
            throw new IllegalArgumentException("player is not a bukkit player");
        }
        return add(new PaperPlayer(bukkit));
    }

    @Override
    @NotNull
    public <T> WrappedPlayer get(@NotNull T player) {
        if (!(player instanceof Player bukkit)) {
            throw new IllegalArgumentException("player is not a bukkit player");
        }
        WrappedPlayer wrappedPlayer = get(bukkit.getUniqueId());
        if (wrappedPlayer != null) {
            return wrappedPlayer;
        }
        return add(new PaperPlayer(bukkit));
    }

    @Override
    @Nullable
    public <T> WrappedPlayer remove(@NotNull T player) {
        if (!(player instanceof Player bukkit)) {
            throw new IllegalArgumentException("player is not a bukkit player");
        }
        return remove(bukkit.getUniqueId());
    }
}
