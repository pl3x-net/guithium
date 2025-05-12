package net.pl3x.guithium.api.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    @Test
    void of() {
        assertEquals(new Point(1, 2), Point.of(1, 2));
        assertNotEquals(new Point(1, 2), Point.of(1, 3));
    }

    @Test
    void x() {
        assertEquals(0, Point.of(0, 1).x());
        assertNotEquals(3, Point.of(2, 3).x());
    }

    @Test
    void y() {
        assertEquals(1, Point.of(0, 1).y());
        assertNotEquals(2, Point.of(2, 3).y());
    }

    @Test
    void testEquals() {
        assertEquals(new Point(1, 2), new Point(1, 2));
        assertNotEquals(new Point(1, 2), new Point(1, 3));
    }

    @Test
    void testHashCode() {
        assertEquals(-268435456, Point.of(1, 1).hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Point{x=0.0,y=0.0}", Point.ZERO.toString());
    }
}
