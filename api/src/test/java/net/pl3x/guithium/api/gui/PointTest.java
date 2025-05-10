package net.pl3x.guithium.api.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    @Test
    void of() {
    }

    @Test
    void x() {
    }

    @Test
    void y() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
        assertEquals(-268435456, Point.of(1, 1).hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Point{x=1.0,y=1.0}", Point.of(1, 1).toString());
    }
}
