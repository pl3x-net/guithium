package net.pl3x.guithium.api.player;

import java.util.UUID;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.network.Connection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A wrapper for a player connected to the server.
 */
public interface WrappedPlayer {
    /**
     * Get the internal player object that is being wrapped.
     *
     * @param <T> Type of player object
     * @return Internal player object
     */
    @NotNull
    <T> T unwrap();

    /**
     * Get the player's name.
     *
     * @return Player's name
     */
    @NotNull
    String getName();

    /**
     * Get player's unique identifier.
     *
     * @return Unique identifier
     */
    @NotNull
    UUID getUUID();

    /**
     * Get the player's connection
     *
     * @return Player connection
     */
    @NotNull
    Connection getConnection();

    /**
     * Get the current screen the player is looking at.
     *
     * @return The current screen
     */
    @Nullable
    Screen getCurrentScreen();

    /**
     * Set the screen the player should be looking at.
     *
     * @param screen The screen
     */
    void setCurrentScreen(@Nullable Screen screen);

    /**
     * Check if player has Guithium client mod installed.
     *
     * @return {@code true} if player has Guithium mod, otherwise {@code false}
     */
    boolean hasGuithium();
}
