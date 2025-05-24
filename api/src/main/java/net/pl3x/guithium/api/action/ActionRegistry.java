package net.pl3x.guithium.api.action;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.action.actions.Action;
import org.jetbrains.annotations.NotNull;

/**
 * The action registry.
 */
public class ActionRegistry {
    /**
     * Create a new action registry
     */
    public ActionRegistry() {
        // Empty constructor to pacify javadoc lint
    }

    /**
     * Call an action for plugins to listen to.
     *
     * @param action The action to call
     */
    public void callAction(@NotNull Action action) {
        for (RegisteredHandler handler : action.getHandlers()) {
            try {
                handler.execute(action);
            } catch (Throwable t) {
                Guithium.logger.error(t.getMessage(), t);
            }
        }
    }

    /**
     * Registers all the actions in the given listener class.
     *
     * @param listener Action listener to register
     */
    public void register(@NotNull ActionListener listener) {
        for (Method method : listener.getClass().getMethods()) {
            if (method.getDeclaredAnnotation(ActionListener.ActionHandler.class) == null) {
                continue;
            }
            Class<?>[] params = method.getParameterTypes();
            if (params.length == 0) {
                continue;
            }
            Class<?> action = params[0];
            if (!Action.class.isAssignableFrom(action)) {
                continue;
            }
            try {
                Field handlers = action.getDeclaredField("handlers");
                handlers.setAccessible(true);

                @SuppressWarnings("unchecked")
                List<RegisteredHandler> list = (List<RegisteredHandler>) handlers.get(action);

                RegisteredHandler handler = new RegisteredHandler(listener, method);
                list.add(handler);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
