package net.pl3x.guithium.api.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.pl3x.guithium.api.action.actions.Action;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a registered action handler.
 */
public class RegisteredHandler {
    private final ActionListener listener;
    private final Method method;

    /**
     * Creates a new registered action handler.
     *
     * @param listener Action listener being registered
     * @param method   Method being registered
     */
    public RegisteredHandler(@NotNull ActionListener listener, @NotNull Method method) {
        this.listener = listener;
        this.method = method;
    }

    /**
     * Gets the registered action listener.
     *
     * @return Action listener
     */
    @NotNull
    public ActionListener getListener() {
        return this.listener;
    }

    /**
     * Gets the registered method.
     *
     * @return Action method
     */
    @NotNull
    public Method getMethod() {
        return this.method;
    }

    /**
     * Execute the method for the given action.
     *
     * @param action Action to call method on
     * @throws InvocationTargetException if the method throws an exception
     * @throws IllegalAccessException    if this Method object is enforcing Java language access
     *                                   control and the underlying method is inaccessible
     */
    public void execute(@NotNull Action action) throws InvocationTargetException, IllegalAccessException {
        getMethod().setAccessible(true);
        getMethod().invoke(getListener(), action);
    }

    @Override
    @NotNull
    public String toString() {
        return "RegisteredHandler{" +
                ",listener=" + getListener().getClass().getName() +
                ",method=" + getMethod().getName() + "}";
    }
}
