package net.pl3x.guithium.api.action.actions;

import net.pl3x.guithium.api.action.RegisteredHandler;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Radio;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Action that fires when a radio button is toggled.
 */
public class RadioToggledAction extends ElementAction implements Cancellable {
    private static final List<RegisteredHandler> handlers = new ArrayList<>();

    private boolean selected;
    private boolean cancelled;

    /**
     * Creates a new action for when a radio button is toggled.
     *
     * @param player   Player that performed the action
     * @param screen   Screen action was performed on
     * @param radio    Radio button action was performed on
     * @param selected New selected state of radio button
     */
    public RadioToggledAction(WrappedPlayer player, Screen screen, Radio radio, boolean selected) {
        super(player, screen, radio);
        this.selected = selected;
    }

    /**
     * Get the tadio button that was toggled.
     *
     * @return Toggled radio button
     */
    @NotNull
    public Radio getElement() {
        return (Radio) super.getElement();
    }

    /**
     * Get the new selected state of this radio button toggle.
     *
     * @return New radio button selected state
     */
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * Set the new selected state of this radio button toggle.
     *
     * @param selected New radio button selected state
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
    public @NotNull List<RegisteredHandler> getHandlers() {
        return handlers;
    }
}
