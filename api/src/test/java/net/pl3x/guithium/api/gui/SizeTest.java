package net.pl3x.guithium.api.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SizeTest {
    @Test
    void of() {
        assertEquals(new Size(1, 2), Size.of(1, 2));
        assertNotEquals(new Size(1, 2), Size.of(1, 3));
    }

    @Test
    void width() {
        assertEquals(0, Size.of(0, 1).width());
        assertNotEquals(3, Size.of(2, 3).width());
    }

    @Test
    void height() {
        assertEquals(1, Size.of(0, 1).height());
        assertNotEquals(2, Size.of(2, 3).height());
    }

    @Test
    void testEquals() {
        assertEquals(new Size(1, 2), new Size(1, 2));
        assertNotEquals(new Size(1, 2), new Size(1, 3));
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
