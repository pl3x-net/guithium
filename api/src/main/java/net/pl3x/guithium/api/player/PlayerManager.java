package net.pl3x.guithium.api.player;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Represents the player manager.
 */
public class PlayerManager {
    private final Map<UUID, WrappedPlayer> players = new HashMap<>();

    /**
     * Add a new player to manage.
     *
     * @param player Player to manage
     */
    public void add(@NotNull WrappedPlayer player) {
        this.players.put(player.getUUID(), player);
    }

    /**
     * Get a managed player.
     *
     * @param uuid UUID of player to get
     * @return Managed player, or null if no player with UUID is managed
     */
    public WrappedPlayer get(@NotNull UUID uuid) {
        return this.players.get(uuid);
    }

    /**
     * Remove player from manager.
     *
     * @param uuid UUID of player to remove
     */
    public void remove(@NotNull UUID uuid) {
        this.players.remove(uuid);
    }
}
