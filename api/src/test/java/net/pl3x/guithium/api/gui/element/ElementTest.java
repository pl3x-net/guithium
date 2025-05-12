package net.pl3x.guithium.api.gui.element;

import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.key.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementTest {
    Key key1 = Key.of("test:key1");
    Key key2 = Key.of("test:key2");

    @Test
    void getKey() {
        assertEquals(key1, Rect.builder(key1).getKey());
        assertNotEquals(key1, Rect.builder(key2).getKey());
    }

    @Test
    void getPos() {
        Rect element = Rect.builder(key1).build();
        assertNull(element.getPos());
        element.setPos(Point.ZERO);
        assertEquals(Point.ZERO, element.getPos());
    }

    @Test
    void setPos() {
        Point pos = Point.of(1, 2);

        Rect.Builder builder = Rect.builder(key1);
        assertNull(builder.getPos());
        builder.setPos(1, 2);
        assertEquals(pos, builder.getPos());

        Rect element = builder.build();
        assertEquals(pos, element.getPos());
    }

    @Test
    void setPosByPoint() {
        Rect.Builder builder = Rect.builder(key1);
        assertNull(builder.getPos());
        builder.setPos(Point.ZERO);
        assertEquals(Point.ZERO, builder.getPos());

        Rect element = builder.build();
        assertEquals(Point.ZERO, element.getPos());
        element.setPos(null);
        assertNull(element.getPos());
    }

    @Test
    void getAnchor() {
        Rect element = Rect.builder(key1).build();
        assertNull(element.getAnchor());
        element.setAnchor(Point.ZERO);
        assertEquals(Point.ZERO, element.getAnchor());
    }

    @Test
    void setAnchor() {
        Point anchor = Point.of(1, 2);

        Rect.Builder builder = Rect.builder(key1);
        assertNull(builder.getAnchor());
        builder.setAnchor(1, 2);
        assertEquals(anchor, builder.getAnchor());

        Rect element = builder.build();
        assertEquals(anchor, element.getAnchor());
    }

    @Test
    void setAnchorByPoint() {
        Rect.Builder builder = Rect.builder(key1);
        assertNull(builder.getAnchor());
        builder.setAnchor(Point.ZERO);
        assertEquals(Point.ZERO, builder.getAnchor());

        Rect element = builder.build();
        assertEquals(Point.ZERO, element.getAnchor());
        element.setAnchor(null);
        assertNull(element.getAnchor());
    }

    @Test
    void getOffset() {
        Rect element = Rect.builder(key1).build();
        assertNull(element.getOffset());
        element.setOffset(Point.ZERO);
        assertEquals(Point.ZERO, element.getOffset());
    }

    @Test
    void setOffset() {
        Point offset = Point.of(1, 2);

        Rect.Builder builder = Rect.builder(key1);
        assertNull(builder.getOffset());
        builder.setOffset(1, 2);
        assertEquals(offset, builder.getOffset());

        Rect element = builder.build();
        assertEquals(offset, element.getOffset());
    }

    @Test
    void setOffsetByPoint() {
        Rect.Builder builder = Rect.builder(key1);
        assertNull(builder.getOffset());
        builder.setOffset(Point.ZERO);
        assertEquals(Point.ZERO, builder.getOffset());

        Rect element = builder.build();
        assertEquals(Point.ZERO, element.getOffset());
        element.setOffset(null);
        assertNull(element.getOffset());
    }

    @Test
    void testEquals() {
        assertEquals(Rect.builder(key1).build(), Rect.builder(key1).build());
        assertNotEquals(Rect.builder(key1).build(), Rect.builder(key2).build());
    }

    @Test
    void testHashCode() {
        Rect element = Rect.builder(key1).build();
        assertEquals(2132768104, element.hashCode());
    }

    @Test
    void testToString() {
        Rect element = Rect.builder(key1).build();
        assertEquals("Rect{key=test:key1,pos=null,anchor=null,offset=null,size=null,color=[0, 0, 0, 0]}", element.toString());
    }
}
