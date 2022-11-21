package net.pl3x.guithium.api.util;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface QuadConsumer<T, U, V, W> {
    void accept(@NotNull T t, @NotNull U u, @NotNull V v, @NotNull W w);
}
