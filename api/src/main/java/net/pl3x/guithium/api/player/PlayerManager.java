package net.pl3x.guithium.api.player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Represents the player manager.
 */
public abstract class PlayerManager {
    private final Map<UUID, WrappedPlayer> players = new HashMap<>();

    /**
     * Add a new player to manage.
     *
     * @param player Player to manage
     * @param <T>    Type of player
     */
    protected abstract <T> void add(@NotNull T player);

    /**
     * Add a new player to manage.
     *
     * @param player Player to manage
     */
    protected void add(@NotNull WrappedPlayer player) {
        this.players.put(player.getUUID(), player);
    }

    /**
     * Get a managed player.
     *
     * @param player Player to get
     * @param <T>    Type of player
     * @return Managed player, or null if player is not managed
     */
    @Nullable
    public abstract <T> WrappedPlayer get(@NotNull T player);

    /**
     * Get a managed player.
     *
     * @param uuid UUID of player to get
     * @return Managed player, or null if no player with UUID is managed
     */
    @Nullable
    public WrappedPlayer get(@NotNull UUID uuid) {
        return this.players.get(uuid);
    }

    /**
     * Remove player from manager.
     *
     * @param player Player to remove
     * @param <T>    Type of player
     */
    protected abstract <T> void remove(@NotNull T player);

    /**
     * Remove player from manager.
     *
     * @param uuid UUID of player to remove
     */
    protected void remove(@NotNull UUID uuid) {
        this.players.remove(uuid);
    }
}
