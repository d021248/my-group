package d021248.group.cyclic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CyclicElementTest {
    @Test
    void testInverse() {
        CyclicElement e = new CyclicElement(2, 5);
        assertEquals(new CyclicElement(3, 5), e.inverse());
        assertEquals(e, e.inverse().inverse());
    }

    @Test
    void testToString() {
        CyclicElement e = new CyclicElement(4, 7);
        assertEquals("4 (mod 7)", e.toString());
    }
}
