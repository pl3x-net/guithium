package net.pl3x.guithium.api.action;

import net.pl3x.guithium.api.Guithium;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents an action. This is similar to Bukkit events.
 */
public abstract class Action {
    /**
     * Call this action for plugins to listen to.
     */
    public void callAction() {
        Guithium.api().getActionRegistry().callAction(this);
    }

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
