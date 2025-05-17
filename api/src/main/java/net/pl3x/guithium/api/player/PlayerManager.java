package net.pl3x.guithium.api.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a manager of wrapped players.
 */
public abstract class PlayerManager {
    private final Map<UUID, WrappedPlayer> players = new HashMap<>();

    /**
     * Create a new player manager instance
     */
    public PlayerManager() {
        // Empty constructor to pacify javadoc lint
    }

    /**
     * Add and wrap player.
     *
     * @param player Player to add
     * @param <T>    Type of player to wrap
     */
    protected abstract <T> void add(@NotNull T player);

    /**
     * Add wrapped player.
     *
     * @param player Wrapped player to add
     */
    public void add(@NotNull WrappedPlayer player) {
        this.players.put(player.getUUID(), player);
    }

    /**
     * Get a wrapped player.
     *
     * @param player Player to get
     * @param <T>    Type of player
     * @return Wrapped player, or null if not managed
     */
    @Nullable
    public abstract <T> WrappedPlayer get(@NotNull T player);

    /**
     * Get a managed player.
     *
     * @param uuid Unique identifier of player to get
     * @return Wrapped player, or null if not managed
     */
    @Nullable
    public WrappedPlayer get(@NotNull UUID uuid) {
        return this.players.get(uuid);
    }

    /**
     * Remove player from manager.
     *
     * @param player Player to remove
     * @param <T>    Type of wrapped player
     */
    protected abstract <T> void remove(@NotNull T player);

    /**
     * Remove player from manager.
     *
     * @param uuid Unique identifier of player to remove
     */
    public void remove(@NotNull UUID uuid) {
        this.players.remove(uuid);
    }
}
