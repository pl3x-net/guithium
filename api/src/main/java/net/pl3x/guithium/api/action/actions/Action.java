package net.pl3x.guithium.api.action.actions;

import net.pl3x.guithium.api.action.RegisteredHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents an action. This is similar to Bukkit events.
 */
public abstract class Action {
    /**
     * Get a list of all the registered handlers for this action.
     * <p>
     * For internal use.
     *
     * @return List of registered handlers
     */
    @NotNull
    public abstract List<RegisteredHandler> getHandlers();
}
