package net.pl3x.guithium.api.player;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    private final Map<UUID, WrappedPlayer> players = new HashMap<>();

    public void add(@NotNull WrappedPlayer player) {
        this.players.put(player.getUUID(), player);
    }

    public WrappedPlayer get(@NotNull UUID uuid) {
        return this.players.get(uuid);
    }

    public void remove(@NotNull UUID uuid) {
        this.players.remove(uuid);
    }
}
