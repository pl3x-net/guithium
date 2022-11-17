// Copyright (c) 2014 Daniel S. Dickstein
// https://github.com/ddickstein/Java-Library/blob/master/java8/function/TriConsumer.java

package net.pl3x.guithium.api.util;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void accept(T t, U u, V v);

    default TriConsumer<T, U, V> andThen(TriConsumer<? super T, ? super U, ? super V> after) {
        Objects.requireNonNull(after);
        return (a, b, c) -> {
            accept(a, b, c);
            after.accept(a, b, c);
        };
    }
}
