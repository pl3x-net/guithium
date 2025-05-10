package net.pl3x.guithium.api.gui.element;

import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.key.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectTest {
    private final int RED = 0xffff0000;
    private final int GREEN = 0xff00ff00;
    private final int BLUE = 0xff0000ff;
    private final int YELLOW = 0xffffff00;

    private static final Key key1 = Key.of("test:rect1");
    private static final Key key2 = Key.of("test:rect2");

    @Test
    void getPos() {
    }

    @Test
    void setPosByFloats() {
    }

    @Test
    void setPosByPoint() {
    }

    @Test
    void getAnchor() {
    }

    @Test
    void setAnchorByFloats() {
    }

    @Test
    void setAnchorByPoint() {
    }

    @Test
    void getOffset() {
    }

    @Test
    void setOffsetByFloats() {
    }

    @Test
    void setOffsetByPoint() {
    }

    @Test
    void getSize() {
    }

    @Test
    void setSizeByFloats() {
    }

    @Test
    void setSizeBySize() {
    }

    @Test
    void getColors() {
        Rect.Builder builder = Rect.builder(key1);
        assertEquals(0, builder.getColors()[0]);
        assertEquals(0, builder.getColors()[1]);
        assertEquals(0, builder.getColors()[2]);
        assertEquals(0, builder.getColors()[3]);

        builder.setColorTopLeft(RED);
        assertEquals(RED, builder.getColors()[0]);
        assertEquals(0, builder.getColors()[1]);
        assertEquals(0, builder.getColors()[2]);
        assertEquals(0, builder.getColors()[3]);

        builder.setColorBottomLeft(GREEN);
        assertEquals(RED, builder.getColors()[0]);
        assertEquals(GREEN, builder.getColors()[1]);
        assertEquals(0, builder.getColors()[2]);
        assertEquals(0, builder.getColors()[3]);

        builder.setColorBottomRight(BLUE);
        assertEquals(RED, builder.getColors()[0]);
        assertEquals(GREEN, builder.getColors()[1]);
        assertEquals(BLUE, builder.getColors()[2]);
        assertEquals(0, builder.getColors()[3]);

        builder.setColorTopRight(YELLOW);
        assertEquals(RED, builder.getColors()[0]);
        assertEquals(GREEN, builder.getColors()[1]);
        assertEquals(BLUE, builder.getColors()[2]);
        assertEquals(YELLOW, builder.getColors()[3]);

        builder.getColors()[0] = YELLOW;
        assertEquals(YELLOW, builder.getColors()[0]);
        assertEquals(GREEN, builder.getColors()[1]);
        assertEquals(BLUE, builder.getColors()[2]);
        assertEquals(YELLOW, builder.getColors()[3]);

        builder.getColors()[1] = BLUE;
        assertEquals(YELLOW, builder.getColors()[0]);
        assertEquals(BLUE, builder.getColors()[1]);
        assertEquals(BLUE, builder.getColors()[2]);
        assertEquals(YELLOW, builder.getColors()[3]);

        builder.getColors()[2] = GREEN;
        assertEquals(YELLOW, builder.getColors()[0]);
        assertEquals(BLUE, builder.getColors()[1]);
        assertEquals(GREEN, builder.getColors()[2]);
        assertEquals(YELLOW, builder.getColors()[3]);

        builder.getColors()[3] = RED;
        assertEquals(YELLOW, builder.getColors()[0]);
        assertEquals(BLUE, builder.getColors()[1]);
        assertEquals(GREEN, builder.getColors()[2]);
        assertEquals(RED, builder.getColors()[3]);

        Rect rect = builder.build().setColorAll(0);
        assertEquals(0, rect.getColors()[0]);
        assertEquals(0, rect.getColors()[1]);
        assertEquals(0, rect.getColors()[2]);
        assertEquals(0, rect.getColors()[3]);

        rect.setColorTopLeft(RED);
        assertEquals(RED, rect.getColors()[0]);
        assertEquals(0, rect.getColors()[1]);
        assertEquals(0, rect.getColors()[2]);
        assertEquals(0, rect.getColors()[3]);

        rect.setColorBottomLeft(GREEN);
        assertEquals(RED, rect.getColors()[0]);
        assertEquals(GREEN, rect.getColors()[1]);
        assertEquals(0, rect.getColors()[2]);
        assertEquals(0, rect.getColors()[3]);

        rect.setColorBottomRight(BLUE);
        assertEquals(RED, rect.getColors()[0]);
        assertEquals(GREEN, rect.getColors()[1]);
        assertEquals(BLUE, rect.getColors()[2]);
        assertEquals(0, rect.getColors()[3]);

        rect.setColorTopRight(YELLOW);
        assertEquals(RED, rect.getColors()[0]);
        assertEquals(GREEN, rect.getColors()[1]);
        assertEquals(BLUE, rect.getColors()[2]);
        assertEquals(YELLOW, rect.getColors()[3]);

        rect.getColors()[0] = YELLOW;
        assertEquals(YELLOW, rect.getColors()[0]);
        assertEquals(GREEN, rect.getColors()[1]);
        assertEquals(BLUE, rect.getColors()[2]);
        assertEquals(YELLOW, rect.getColors()[3]);

        rect.getColors()[1] = BLUE;
        assertEquals(YELLOW, rect.getColors()[0]);
        assertEquals(BLUE, rect.getColors()[1]);
        assertEquals(BLUE, rect.getColors()[2]);
        assertEquals(YELLOW, rect.getColors()[3]);

        rect.getColors()[2] = GREEN;
        assertEquals(YELLOW, rect.getColors()[0]);
        assertEquals(BLUE, rect.getColors()[1]);
        assertEquals(GREEN, rect.getColors()[2]);
        assertEquals(YELLOW, rect.getColors()[3]);

        rect.getColors()[3] = RED;
        assertEquals(YELLOW, rect.getColors()[0]);
        assertEquals(BLUE, rect.getColors()[1]);
        assertEquals(GREEN, rect.getColors()[2]);
        assertEquals(RED, rect.getColors()[3]);
    }

    @Test
    void colorTopLeft() {
        Rect.Builder builder = Rect.builder(key1);
        assertEquals(0, builder.getColorTopLeft());
        assertEquals(0, builder.getColorTopRight());
        assertEquals(0, builder.getColorBottomLeft());
        assertEquals(0, builder.getColorBottomRight());

        assertEquals(RED, builder.setColorTopLeft(RED).getColorTopLeft());
        assertNotEquals(RED, builder.getColorTopRight());
        assertNotEquals(RED, builder.getColorBottomLeft());
        assertNotEquals(RED, builder.getColorBottomRight());

        Rect rect = builder.build();
        assertEquals(RED, rect.getColorTopLeft());
        assertNotEquals(RED, rect.getColorTopRight());
        assertNotEquals(RED, rect.getColorBottomLeft());
        assertNotEquals(RED, rect.getColorBottomRight());

        assertEquals(BLUE, rect.setColorTopLeft(BLUE).getColorTopLeft());
        assertNotEquals(BLUE, rect.getColorTopRight());
        assertNotEquals(BLUE, rect.getColorBottomLeft());
        assertNotEquals(BLUE, rect.getColorBottomRight());
    }

    @Test
    void colorBottomLeft() {
        Rect.Builder builder = Rect.builder(key1);
        assertEquals(0, builder.getColorTopLeft());
        assertEquals(0, builder.getColorTopRight());
        assertEquals(0, builder.getColorBottomLeft());
        assertEquals(0, builder.getColorBottomRight());

        assertNotEquals(GREEN, builder.setColorBottomLeft(GREEN).getColorTopLeft());
        assertNotEquals(GREEN, builder.getColorTopRight());
        assertEquals(GREEN, builder.getColorBottomLeft());
        assertNotEquals(GREEN, builder.getColorBottomRight());

        Rect rect = builder.build();
        assertNotEquals(GREEN, rect.getColorTopLeft());
        assertNotEquals(GREEN, rect.getColorTopRight());
        assertEquals(GREEN, rect.getColorBottomLeft());
        assertNotEquals(GREEN, rect.getColorBottomRight());

        assertNotEquals(BLUE, rect.setColorBottomLeft(BLUE).getColorTopLeft());
        assertNotEquals(BLUE, rect.getColorTopRight());
        assertEquals(BLUE, rect.getColorBottomLeft());
        assertNotEquals(BLUE, rect.getColorBottomRight());
    }

    @Test
    void colorBottomRight() {
        Rect.Builder builder = Rect.builder(key1);
        assertEquals(0, builder.getColorTopLeft());
        assertEquals(0, builder.getColorTopRight());
        assertEquals(0, builder.getColorBottomLeft());
        assertEquals(0, builder.getColorBottomRight());

        assertNotEquals(GREEN, builder.setColorBottomRight(GREEN).getColorTopLeft());
        assertNotEquals(GREEN, builder.getColorTopRight());
        assertNotEquals(GREEN, builder.getColorBottomLeft());
        assertEquals(GREEN, builder.getColorBottomRight());

        Rect rect = builder.build();
        assertNotEquals(GREEN, rect.getColorTopLeft());
        assertNotEquals(GREEN, rect.getColorTopRight());
        assertNotEquals(GREEN, rect.getColorBottomLeft());
        assertEquals(GREEN, rect.getColorBottomRight());

        assertNotEquals(BLUE, rect.setColorBottomRight(BLUE).getColorTopLeft());
        assertNotEquals(BLUE, rect.getColorTopRight());
        assertNotEquals(BLUE, rect.getColorBottomLeft());
        assertEquals(BLUE, rect.getColorBottomRight());
    }

    @Test
    void colorTopRight() {
        Rect.Builder builder = Rect.builder(key1);
        assertEquals(0, builder.getColorTopLeft());
        assertEquals(0, builder.getColorTopRight());
        assertEquals(0, builder.getColorBottomLeft());
        assertEquals(0, builder.getColorBottomRight());

        assertNotEquals(GREEN, builder.setColorTopRight(GREEN).getColorTopLeft());
        assertEquals(GREEN, builder.getColorTopRight());
        assertNotEquals(GREEN, builder.getColorBottomLeft());
        assertNotEquals(GREEN, builder.getColorBottomRight());

        Rect rect = builder.build();
        assertNotEquals(GREEN, rect.getColorTopLeft());
        assertEquals(GREEN, rect.getColorTopRight());
        assertNotEquals(GREEN, rect.getColorBottomLeft());
        assertNotEquals(GREEN, rect.getColorBottomRight());

        assertNotEquals(BLUE, rect.setColorTopRight(BLUE).getColorTopLeft());
        assertEquals(BLUE, rect.getColorTopRight());
        assertNotEquals(BLUE, rect.getColorBottomLeft());
        assertNotEquals(BLUE, rect.getColorBottomRight());
    }

    @Test
    void colorTop() {
        Rect.Builder builder = Rect.builder(key1);
        assertEquals(0, builder.getColorTopLeft());
        assertEquals(0, builder.getColorTopRight());
        assertEquals(0, builder.getColorBottomLeft());
        assertEquals(0, builder.getColorBottomRight());

        assertEquals(GREEN, builder.setColorTop(GREEN).getColorTopLeft());
        assertEquals(GREEN, builder.getColorTopRight());
        assertNotEquals(GREEN, builder.getColorBottomLeft());
        assertNotEquals(GREEN, builder.getColorBottomRight());

        Rect rect = builder.build();
        assertEquals(GREEN, rect.getColorTopLeft());
        assertEquals(GREEN, rect.getColorTopRight());
        assertNotEquals(GREEN, rect.getColorBottomLeft());
        assertNotEquals(GREEN, rect.getColorBottomRight());

        assertEquals(BLUE, rect.setColorTop(BLUE).getColorTopLeft());
        assertEquals(BLUE, rect.getColorTopRight());
        assertNotEquals(BLUE, rect.getColorBottomLeft());
        assertNotEquals(BLUE, rect.getColorBottomRight());
    }

    @Test
    void colorBottom() {
        Rect.Builder builder = Rect.builder(key1);
        assertEquals(0, builder.getColorTopLeft());
        assertEquals(0, builder.getColorTopRight());
        assertEquals(0, builder.getColorBottomLeft());
        assertEquals(0, builder.getColorBottomRight());

        assertNotEquals(GREEN, builder.setColorBottom(GREEN).getColorTopLeft());
        assertNotEquals(GREEN, builder.getColorTopRight());
        assertEquals(GREEN, builder.getColorBottomLeft());
        assertEquals(GREEN, builder.getColorBottomRight());

        Rect rect = builder.build();
        assertNotEquals(GREEN, rect.getColorTopLeft());
        assertNotEquals(GREEN, rect.getColorTopRight());
        assertEquals(GREEN, rect.getColorBottomLeft());
        assertEquals(GREEN, rect.getColorBottomRight());

        assertNotEquals(YELLOW, rect.setColorBottom(YELLOW).getColorTopLeft());
        assertNotEquals(YELLOW, rect.getColorTopRight());
        assertEquals(YELLOW, rect.getColorBottomLeft());
        assertEquals(YELLOW, rect.getColorBottomRight());
    }

    @Test
    void colorLeft() {
        Rect.Builder builder = Rect.builder(key1);
        assertEquals(0, builder.getColorTopLeft());
        assertEquals(0, builder.getColorTopRight());
        assertEquals(0, builder.getColorBottomLeft());
        assertEquals(0, builder.getColorBottomRight());

        assertEquals(GREEN, builder.setColorLeft(GREEN).getColorTopLeft());
        assertNotEquals(GREEN, builder.getColorTopRight());
        assertEquals(GREEN, builder.getColorBottomLeft());
        assertNotEquals(GREEN, builder.getColorBottomRight());

        Rect rect = builder.build();
        assertEquals(GREEN, rect.getColorTopLeft());
        assertNotEquals(GREEN, rect.getColorTopRight());
        assertEquals(GREEN, rect.getColorBottomLeft());
        assertNotEquals(GREEN, rect.getColorBottomRight());

        assertEquals(BLUE, rect.setColorLeft(BLUE).getColorTopLeft());
        assertNotEquals(BLUE, rect.getColorTopRight());
        assertEquals(BLUE, rect.getColorBottomLeft());
        assertNotEquals(BLUE, rect.getColorBottomRight());
    }

    @Test
    void colorRight() {
        Rect.Builder builder = Rect.builder(key1);
        assertEquals(0, builder.getColorTopLeft());
        assertEquals(0, builder.getColorTopRight());
        assertEquals(0, builder.getColorBottomLeft());
        assertEquals(0, builder.getColorBottomRight());

        assertNotEquals(GREEN, builder.setColorRight(GREEN).getColorTopLeft());
        assertEquals(GREEN, builder.getColorTopRight());
        assertNotEquals(GREEN, builder.getColorBottomLeft());
        assertEquals(GREEN, builder.getColorBottomRight());

        Rect rect = builder.build();
        assertNotEquals(GREEN, rect.getColorTopLeft());
        assertEquals(GREEN, rect.getColorTopRight());
        assertNotEquals(GREEN, rect.getColorBottomLeft());
        assertEquals(GREEN, rect.getColorBottomRight());

        assertNotEquals(YELLOW, rect.setColorRight(YELLOW).getColorTopLeft());
        assertEquals(YELLOW, rect.getColorTopRight());
        assertNotEquals(YELLOW, rect.getColorBottomLeft());
        assertEquals(YELLOW, rect.getColorBottomRight());
    }

    @Test
    void colorAll() {
        Rect.Builder builder = Rect.builder(key1);
        assertEquals(0, builder.getColorTopLeft());
        assertEquals(0, builder.getColorTopRight());
        assertEquals(0, builder.getColorBottomLeft());
        assertEquals(0, builder.getColorBottomRight());

        assertEquals(RED, builder.setColorAll(RED).getColorTopLeft());
        assertEquals(RED, builder.getColorTopRight());
        assertEquals(RED, builder.getColorBottomLeft());
        assertEquals(RED, builder.getColorBottomRight());

        Rect rect = builder.build();
        assertEquals(RED, rect.getColorTopLeft());
        assertEquals(RED, rect.getColorTopRight());
        assertEquals(RED, rect.getColorBottomLeft());
        assertEquals(RED, rect.getColorBottomRight());

        assertEquals(YELLOW, rect.setColorAll(YELLOW).getColorTopLeft());
        assertEquals(YELLOW, rect.getColorTopRight());
        assertEquals(YELLOW, rect.getColorBottomLeft());
        assertEquals(YELLOW, rect.getColorBottomRight());
    }

    @Test
    void testEquals() {
        Rect rect1 = Rect.builder(key1).build();
        Rect rect2 = Rect.builder(key1).build();
        Rect rect3 = Rect.builder(key2).build();
        assertEquals(rect1, rect2);
        assertNotEquals(rect1, rect3);
        rect1.setPos(Point.of(1, 1));
        assertNotEquals(rect1, rect2);
        assertNotEquals(rect2, rect3);
    }

    @Test
    void testHashCode() {
        assertEquals(411832125, Rect.builder(key1).build().hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Rect{key=test:rect1,pos=null,anchor=null,offset=null,size=null,color=[0, 0, 0, 0]}", Rect.builder(key1).build().toString());
    }
}
