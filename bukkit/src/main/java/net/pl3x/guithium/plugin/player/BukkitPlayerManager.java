package net.pl3x.guithium.plugin.player;

import net.pl3x.guithium.api.player.PlayerManager;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitPlayerManager extends PlayerManager {
    @Override
    public <T> void add(@NotNull T player) {
        if (player instanceof Player bukkit) {
            add(new BukkitPlayer(bukkit));
        }
    }

    @Override
    @Nullable
    public <T> WrappedPlayer get(@NotNull T player) {
        return player instanceof Player bukkit ? get(bukkit.getUniqueId()) : null;
    }

    @Override
    public <T> void remove(@NotNull T player) {
        if (player instanceof Player bukkit) {
            remove(bukkit.getUniqueId());
        }
    }
}
