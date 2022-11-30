package net.pl3x.guithium.api.action.actions;

import net.pl3x.guithium.api.action.RegisteredHandler;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Slider;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Action that fires when a slider is changed.
 */
public class SliderChangedAction extends ElementAction implements Cancellable {
    private static final List<RegisteredHandler> handlers = new ArrayList<>();

    private double value;
    private boolean cancelled;

    /**
     * Creates a new action for when a checkbox is toggled.
     *
     * @param player Player that performed the action
     * @param screen Screen action was performed on
     * @param slider Slider action was performed on
     * @param value  New value of slider
     */
    public SliderChangedAction(WrappedPlayer player, Screen screen, Slider slider, double value) {
        super(player, screen, slider);
        this.value = value;
    }

    /**
     * Get the slider that was changed.
     *
     * @return Changed slider
     */
    @NotNull
    public Slider getElement() {
        return (Slider) super.getElement();
    }

    /**
     * Get the new value of this slider change.
     *
     * @return New slider value
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Set the new value of this slider change.
     *
     * @param value New slider value
     */
    public void setValue(double value) {
        this.value = value;
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
