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
     * @return the wrapped player that was added
     * @throws IllegalArgumentException if player is not correct type for the environment
     */
    @NotNull
    public abstract <T> WrappedPlayer add(@NotNull T player);

    /**
     * Add wrapped player.
     *
     * @param player Wrapped player to add
     * @return the wrapped player that was added
     */
    @NotNull
    public WrappedPlayer add(@NotNull WrappedPlayer player) {
        this.players.put(player.getUUID(), player);
        return player;
    }

    /**
     * Get a wrapped player.
     * <p>
     * If player is not currently in the manager, they will be wrapped and added.
     *
     * @param player Player to get
     * @param <T>    Type of player
     * @return Wrapped player, or null if not managed
     * @throws IllegalArgumentException if player is not correct type for the environment
     */
    @NotNull
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
     * @return the wrapped player that was removed, or null if there was no player to remove
     * @throws IllegalArgumentException if player is not correct type for the environment
     */
    @Nullable
    public abstract <T> WrappedPlayer remove(@NotNull T player);

    /**
     * Remove player from manager.
     *
     * @param uuid Unique identifier of player to remove
     * @return the wrapped player that was removed, or null if there was no player to remove
     */
    @Nullable
    public WrappedPlayer remove(@NotNull UUID uuid) {
        return this.players.remove(uuid);
    }
}
