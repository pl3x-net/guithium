package net.pl3x.guithium.api.key;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyedTest {
    private static final Key key1 = Key.of("test:keyed1");
    private static final Key key2 = Key.of("test:keyed2");

    @Test
    void getKey() {
        Keyed keyed = new KeyedImpl(key1);
        assertEquals(key1, keyed.getKey());
        assertNotEquals(key2, keyed.getKey());
    }

    @Test
    void testEquals() {
        assertEquals(new KeyedImpl(key1), new KeyedImpl(key1));
        assertNotEquals(new KeyedImpl(key1), new KeyedImpl(key2));
    }

    @Test
    void testHashCode() {
        assertEquals(1038653595, new KeyedImpl(key1).hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Keyed{key=test:keyed1}", new KeyedImpl(key1).toString());
    }

    private static class KeyedImpl extends Keyed {
        public KeyedImpl(@NotNull Key key) {
            super(key);
        }
    }
}
