package net.pl3x.guithium.api.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a manager of wrapped players.
 */
public class PlayerManager {
    private final Map<UUID, WrappedPlayer> players = new HashMap<>();

    /**
     * Create a new player manager instance
     */
    public PlayerManager() {
        // Empty constructor to make javadoc lint happy
    }

    /**
     * Add player to manager.
     *
     * @param player Wrapped player to add
     */
    public void add(@NotNull WrappedPlayer player) {
        this.players.put(player.getUUID(), player);
    }

    /**
     * Get player by specified unique identifier.
     *
     * @param uuid Unique identifier
     * @return Wrapped player, or null if not found
     */
    @Nullable
    public WrappedPlayer get(@NotNull UUID uuid) {
        return this.players.get(uuid);
    }

    /**
     * Remove player from manager.
     *
     * @param uuid Unique identifier of player to remove
     */
    public void remove(@NotNull UUID uuid) {
        this.players.remove(uuid);
    }
}
