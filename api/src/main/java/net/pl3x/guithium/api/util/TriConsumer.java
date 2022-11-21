package net.pl3x.guithium.api.util;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void accept(@NotNull T t, @NotNull U u, @NotNull V v);
}
