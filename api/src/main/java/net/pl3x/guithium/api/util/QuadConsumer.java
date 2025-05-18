package net.pl3x.guithium.api.util;

import org.jetbrains.annotations.NotNull;

/**
 * This is a functional interface and can therefore be used as the assignment target for a lambda expression or method reference.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @param <V> the type of the third argument to the operation
 * @param <W> the type of the forth argument to the operation
 */
@FunctionalInterface
public interface QuadConsumer<T, U, V, W> {
    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @param v the third input argument
     * @param w the forth input argument
     */
    void accept(@NotNull T t, @NotNull U u, @NotNull V v, @NotNull W w);
}
