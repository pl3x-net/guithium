package net.pl3x.guithium.api.player;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
    private final Map<UUID, Player> players = new ConcurrentHashMap<>();

    public void add(@NotNull Player player) {
        this.players.put(player.getUUID(), player);
    }

    public Player get(@NotNull UUID uuid) {
        return this.players.get(uuid);
    }

    public void remove(@NotNull UUID uuid) {
        this.players.remove(uuid);
    }
}
