package net.pl3x.guithium.api.action.actions;

/**
 * Represents a cancellable state.
 */
public interface Cancellable {
    /**
     * Gets the cancellation state of this action. A cancelled action will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @return True if this action is cancelled
     */
    boolean isCancelled();

    /**
     * Sets the cancellation state of this action. A cancelled action will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancelled True if you wish to cancel this action
     */
    void setCancelled(boolean cancelled);
}
