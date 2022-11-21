package net.pl3x.guithium.api.action;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ActionRegistry {
    public void callAction(@NotNull Action action) {
        for (RegisteredHandler handler : action.getHandlers()) {
            try {
                handler.execute(action);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public void register(@NotNull ActionListener listener) {
        for (Method method : listener.getClass().getMethods()) {
            if (method.getDeclaredAnnotation(ActionHandler.class) == null) {
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
