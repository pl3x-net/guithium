package net.pl3x.guithium.api.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@FunctionalInterface
public interface QuadConsumer<T, U, V, W> {
    void accept(@NotNull T t, @NotNull U u, @NotNull V v, @NotNull W w);

    @NotNull
    default QuadConsumer<T, U, V, W> andThen(@NotNull QuadConsumer<? super T, ? super U, ? super V, ? super W> after) {
        Objects.requireNonNull(after);
        return (a, b, c, d) -> {
            accept(a, b, c, d);
            after.accept(a, b, c, d);
        };
    }
}
