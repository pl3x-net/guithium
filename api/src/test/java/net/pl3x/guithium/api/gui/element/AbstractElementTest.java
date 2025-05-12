package net.pl3x.guithium.api.gui.element;

import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractElementTest {
    Key key1 = Key.of("test:key1");
    Key key2 = Key.of("test:key2");

    @Test
    void getPos() {
        ElementImpl element = ElementImpl.builder(key1).build();
        assertNull(element.getPos());
        element.setPos(Point.ZERO);
        assertEquals(Point.ZERO, element.getPos());
    }

    @Test
    void setPos() {
        Point pos = Point.of(1, 2);

        ElementImpl.Builder builder = ElementImpl.builder(key1);
        assertNull(builder.getPos());
        builder.setPos(1, 2);
        assertEquals(pos, builder.getPos());

        ElementImpl element = builder.build();
        assertEquals(pos, element.getPos());
    }

    @Test
    void setPosByPoint() {
        ElementImpl.Builder builder = ElementImpl.builder(key1);
        assertNull(builder.getPos());
        builder.setPos(Point.ZERO);
        assertEquals(Point.ZERO, builder.getPos());

        ElementImpl element = builder.build();
        assertEquals(Point.ZERO, element.getPos());
        element.setPos(null);
        assertNull(element.getPos());
    }

    @Test
    void getAnchor() {
        ElementImpl element = ElementImpl.builder(key1).build();
        assertNull(element.getAnchor());
        element.setAnchor(Point.ZERO);
        assertEquals(Point.ZERO, element.getAnchor());
    }

    @Test
    void setAnchor() {
        Point anchor = Point.of(1, 2);

        ElementImpl.Builder builder = ElementImpl.builder(key1);
        assertNull(builder.getAnchor());
        builder.setAnchor(1, 2);
        assertEquals(anchor, builder.getAnchor());

        ElementImpl element = builder.build();
        assertEquals(anchor, element.getAnchor());
    }

    @Test
    void setAnchorByPoint() {
        ElementImpl.Builder builder = ElementImpl.builder(key1);
        assertNull(builder.getAnchor());
        builder.setAnchor(Point.ZERO);
        assertEquals(Point.ZERO, builder.getAnchor());

        ElementImpl element = builder.build();
        assertEquals(Point.ZERO, element.getAnchor());
        element.setAnchor(null);
        assertNull(element.getAnchor());
    }

    @Test
    void getOffset() {
        ElementImpl element = ElementImpl.builder(key1).build();
        assertNull(element.getOffset());
        element.setOffset(Point.ZERO);
        assertEquals(Point.ZERO, element.getOffset());
    }

    @Test
    void setOffset() {
        Point offset = Point.of(1, 2);

        ElementImpl.Builder builder = ElementImpl.builder(key1);
        assertNull(builder.getOffset());
        builder.setOffset(1, 2);
        assertEquals(offset, builder.getOffset());

        ElementImpl element = builder.build();
        assertEquals(offset, element.getOffset());
    }

    @Test
    void setOffsetByPoint() {
        ElementImpl.Builder builder = ElementImpl.builder(key1);
        assertNull(builder.getOffset());
        builder.setOffset(Point.ZERO);
        assertEquals(Point.ZERO, builder.getOffset());

        ElementImpl element = builder.build();
        assertEquals(Point.ZERO, element.getOffset());
        element.setOffset(null);
        assertNull(element.getOffset());
    }

    @Test
    void testEquals() {
        assertEquals(ElementImpl.builder(key1).build(), ElementImpl.builder(key1).build());
        assertNotEquals(ElementImpl.builder(key1).build(), ElementImpl.builder(key2).build());

    }

    @Test
    void testHashCode() {
        ElementImpl element = ElementImpl.builder(key1).build();
        assertEquals(2132767143, element.hashCode());
    }

    @Test
    void testToString() {
        ElementImpl element = ElementImpl.builder(key1).build();
        assertEquals("AbstractElement{key=test:key1,pos=null,anchor=null,offset=null}", element.toString());
    }

    private static class ElementImpl extends AbstractElement<ElementImpl> {
        public ElementImpl(Key key, Point pos, Point anchor, Point offset) {
            super(key, pos, anchor, offset);
        }

        public static Builder builder(@NotNull Key key) {
            return new Builder(key);
        }

        private static class Builder extends AbstractBuilder<Builder> {
            public Builder(Key key) {
                super(key);
            }

            @Override
            public @NotNull ElementImpl build() {
                return new ElementImpl(getKey(), getPos(), getAnchor(), getOffset());
            }
        }
    }
}
