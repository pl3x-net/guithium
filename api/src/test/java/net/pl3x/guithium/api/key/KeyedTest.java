package net.pl3x.guithium.api.key;

import net.pl3x.guithium.api.gui.element.Rect;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyedTest {
    private static final Key key1 = Key.of("test:keyed1");
    private static final Key key2 = Key.of("test:keyed2");

    @Test
    void getKey() {
        Rect.Builder builder = Rect.builder(key1);
        assertEquals(key1, builder.getKey());
        assertNotEquals(key2, builder.getKey());
        Rect keyed = builder.build();
        assertEquals(key1, keyed.getKey());
        assertNotEquals(key2, keyed.getKey());
    }

    @Test
    void testEquals() {
        assertEquals(Rect.builder(key1).build(), Rect.builder(key1).build());
        assertNotEquals(Rect.builder(key1).build(), Rect.builder(key2).build());
    }

    @Test
    void testHashCode() {
        Rect keyed = Rect.builder(key1).build();
        assertEquals(1585772743, keyed.hashCode());
    }

    @Test
    void testToString() {
        Rect keyed = Rect.builder(key1).build();
        assertEquals("Rect{key=test:keyed1,pos=null,anchor=null,offset=null,size=null,color=[0, 0, 0, 0]}", keyed.toString());
    }
}
