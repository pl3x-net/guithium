package net.pl3x.guithium.api.action;

import net.pl3x.guithium.api.Guithium;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Action {
    public void callAction() {
        Guithium.api().getActionRegistry().callAction(this);
    }

    @NotNull
    public abstract List<RegisteredHandler> getHandlers();
}
