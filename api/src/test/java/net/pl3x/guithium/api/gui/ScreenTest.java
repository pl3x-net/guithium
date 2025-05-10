package net.pl3x.guithium.api.gui;

import java.util.List;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Rect;
import net.pl3x.guithium.api.key.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScreenTest {
    private static final Key key1 = Key.of("test:rect1");
    private static final Key key2 = Key.of("test:rect2");
    private static final Key key3 = Key.of("test:rect3");
    private static final Key key4 = Key.of("test:rect4");
    private static final Key key5 = Key.of("test:rect5");

    private static final Rect rect1 = Rect.builder(key1).build();
    private static final Rect rect2 = Rect.builder(key2).build();
    private static final Rect rect3 = Rect.builder(key3).build();
    private static final Rect rect4 = Rect.builder(key4).build();
    private static final Rect rect5 = Rect.builder(key5).build();

    private static Screen screen() {
        return screen("test:screen");
    }

    private static Screen screen(String key) {
        Screen screen = new Screen(key);
        screen.addElements(List.of(rect1, rect2, rect3));
        return screen;
    }

    @Test
    void getElements() {
        List<Element> elements = screen().getElements();
        assertEquals(3, elements.size());
        assertEquals(rect1, elements.getFirst());
        assertEquals(rect2, elements.get(1));
        assertEquals(rect3, elements.getLast());
        assertThrows(UnsupportedOperationException.class, elements::removeFirst);
    }

    @Test
    void getElementByString() {
        Screen screen = screen();
        assertEquals(rect1, screen.getElement(key1.toString()));
        assertEquals(rect2, screen.getElement(key2.toString()));
        assertEquals(rect3, screen.getElement(key3.toString()));
        assertNull(screen.getElement(key4.toString()));
    }

    @Test
    void getElementByKey() {
        Screen screen = screen();
        assertEquals(rect1, screen.getElement(key1));
        assertEquals(rect2, screen.getElement(key2));
        assertEquals(rect3, screen.getElement(key3));
        assertNull(screen.getElement(key4));
    }

    @Test
    void addElements() {
        Screen screen = screen();
        assertTrue(screen.addElements(List.of(rect4, rect5)));
        assertThrows(IllegalArgumentException.class, () -> screen.addElements(List.of(rect1, rect2)));
    }

    @Test
    void addElement() {
        Screen screen = screen();
        assertTrue(screen.addElement(rect4));
        assertThrows(IllegalArgumentException.class, () -> screen.addElement(rect1));
    }

    @Test
    void removeElement() {
        Screen screen = screen();
        assertTrue(screen.removeElement(rect1));
        assertTrue(screen.removeElement(rect3));
        assertTrue(screen.removeElement(rect2));
    }

    @Test
    void removeElementByString() {
        assertTrue(screen().removeElement(key1.toString()));
    }

    @Test
    void removeElementByKey() {
        assertTrue(screen().removeElement(key1));
    }

    @Test
    void hasElement() {
        Screen screen = screen();
        assertTrue(screen.hasElement(rect1));
        assertFalse(screen.hasElement(rect4));
    }

    @Test
    void hasElementByString() {
        Screen screen = screen();
        assertTrue(screen.hasElement(key1.toString()));
        assertFalse(screen.hasElement(key4.toString()));
    }

    @Test
    void hasElementByKey() {
        Screen screen = screen();
        assertTrue(screen.hasElement(key1));
        assertFalse(screen.hasElement(key4));
    }

    @Test
    void testEquals() {
        Screen screen1 = screen();
        Screen screen2 = screen();
        Screen screen3 = new Screen(Key.of("test:different-key"));
        screen3.addElements(List.of(rect1, rect2, rect3));
        assertEquals(screen1, screen2);
        assertNotEquals(screen1, screen3);
        screen1.removeElement(rect1);
        assertNotEquals(screen1, screen2);
        assertNotEquals(screen2, screen3);
    }

    @Test
    void testHashCode() {
        assertEquals(1508285992, screen().hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Screen{key=test:screen,elements=[Rect{key=test:rect1,pos=null,anchor=null,offset=null,size=null,color=[0, 0, 0, 0]}, Rect{key=test:rect2,pos=null,anchor=null,offset=null,size=null,color=[0, 0, 0, 0]}, Rect{key=test:rect3,pos=null,anchor=null,offset=null,size=null,color=[0, 0, 0, 0]}]}", screen().toString());
    }
}
