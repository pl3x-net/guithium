package net.pl3x.guithium.api.action;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RegisteredHandler {
    private final ActionListener listener;
    private final Method method;

    public RegisteredHandler(@NotNull ActionListener listener, @NotNull Method method) {
        this.listener = listener;
        this.method = method;
    }

    @NotNull
    public ActionListener getListener() {
        return this.listener;
    }

    @NotNull
    public Method getMethod() {
        return this.method;
    }

    public void execute(@NotNull Action action) throws InvocationTargetException, IllegalAccessException {
        getMethod().setAccessible(true);
        getMethod().invoke(getListener(), action);
    }

    @Override
    @NotNull
    public String toString() {
        return "Handler{" +
                ",listener=" + getListener().getClass().getName() +
                ",method=" + getMethod().getName() + "}";
    }
}
