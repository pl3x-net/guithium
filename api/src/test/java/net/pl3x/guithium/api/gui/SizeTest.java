package net.pl3x.guithium.api.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SizeTest {
    @Test
    void of() {
    }

    @Test
    void width() {
    }

    @Test
    void height() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
        assertEquals(-268435456, Size.of(1, 1).hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Size{width=1.0,height=1.0}", Size.of(1, 1).toString());
    }
}
