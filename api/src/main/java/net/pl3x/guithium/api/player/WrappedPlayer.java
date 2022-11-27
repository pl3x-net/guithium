package net.pl3x.guithium.api.player;

import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.network.Connection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a wrapped player.
 */
public interface WrappedPlayer {
    /**
     * Unwrap the player to its native type.
     *
     * @param <T> Player's native type
     * @return Native player object
     */
    @NotNull <T> T unwrap();

    /**
     * Get player's name.
     *
     * @return Player's name
     */
    @NotNull
    String getName();

    /**
     * Get player's UUID.
     *
     * @return Player's UUID
     */
    @NotNull
    UUID getUUID();

    /**
     * Get player's connection.
     *
     * @return Player's connection
     */
    @NotNull
    Connection getConnection();

    /**
     * Get player's current screen.
     * <p>
     * Note: {@link Screen.Type#HUD} type screens will not show here.
     *
     * @return Player's current screen, or null if not displaying a screen
     */
    @Nullable
    Screen getCurrentScreen();

    /**
     * Set player's current screen.
     * <p>
     * Note: {@link Screen.Type#HUD} type screens will not show here.
     *
     * @param screen New current screen displayed to player
     */
    void setCurrentScreen(@Nullable Screen screen);

    /**
     * Get if player has Guithium mod installed on their client.
     *
     * @return True if player has Guithium mod installed
     */
    boolean hasGuithium();
}
