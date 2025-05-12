package net.pl3x.guithium.api.key;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {
    @Test
    void of() {
        assertEquals("test1", Key.of("test1").toString());
        assertNotEquals("test1", Key.of("test2").toString());
    }

    @Test
    void testEquals() {
        assertEquals(Key.of("test1"), Key.of("test1"));
        assertNotEquals(Key.of("test1"), Key.of("test2"));
    }

    @Test
    void testHashCode() {
        assertEquals(110251487, Key.of("test1").hashCode());
    }

    @Test
    void testToString() {
        assertEquals("test1", Key.of("test1").toString());
    }
}
