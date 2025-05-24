package net.pl3x.guithium.api.action.actions.player.screen.element;

import java.util.ArrayList;
import java.util.List;
import net.pl3x.guithium.api.action.RegisteredHandler;
import net.pl3x.guithium.api.action.actions.Cancellable;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Checkbox;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Action that fires when a checkbox is toggled.
 */
public class CheckboxToggledAction extends ElementAction implements Cancellable {
    private static final List<RegisteredHandler> handlers = new ArrayList<>();

    private boolean selected;
    private boolean cancelled;

    /**
     * Create a new action for when a checkbox is toggled.
     *
     * @param player   Player that performed the action
     * @param screen   Screen action was performed on
     * @param checkbox Checkbox action was performed on
     * @param selected New selected state of checkbox
     */
    public CheckboxToggledAction(@NotNull WrappedPlayer player, @NotNull Screen screen, @NotNull Checkbox checkbox, boolean selected) {
        super(player, screen, checkbox);
        this.selected = selected;
    }

    /**
     * Get the checkbox that was toggled.
     *
     * @return Toggled checkbox
     */
    @NotNull
    public Checkbox getElement() {
        return (Checkbox) super.getElement();
    }

    /**
     * Get the new selected state of this checkbox toggle.
     *
     * @return New checkbox selected state
     */
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * Set the new selected state of this checkbox toggle.
     *
     * @param selected New checkbox selected state
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    @NotNull
    public List<RegisteredHandler> getHandlers() {
        return handlers;
    }
}
