package d021248.group.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ElementTest {
    static class DummyElement implements Element {
        private final int value;

        DummyElement(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof DummyElement d && d.value == value;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(value);
        }
    }

    @Test
    void testEqualsAndHashCode() {
        DummyElement e1 = new DummyElement(1);
        DummyElement e2 = new DummyElement(1);
        DummyElement e3 = new DummyElement(2);
        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
