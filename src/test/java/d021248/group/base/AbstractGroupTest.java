package d021248.group.base;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

import d021248.group.api.Element;

class AbstractGroupTest {
    static class Mod2Element implements Element {
        private final int value;

        Mod2Element(int value) {
            this.value = value % 2;
        }

        int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Mod2Element m && m.value == value;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(value);
        }
    }

    static class Mod2Group extends AbstractGroup<Mod2Element> {
        Mod2Group() {
            super(Set.of(new Mod2Element(1)), (l, r) -> new Mod2Element((l.getValue() + r.getValue()) % 2));
        }

        @Override
        protected Mod2Element computeIdentity() {
            return new Mod2Element(0);
        }

        @Override
        protected Mod2Element computeInverse(Mod2Element e) {
            return new Mod2Element(e.getValue());
        }
    }

    @Test
    void testElementsAndOperation() {
        Mod2Group group = new Mod2Group();
        assertEquals(Set.of(new Mod2Element(0), new Mod2Element(1)), group.elements());
        assertEquals(new Mod2Element(0), group.operation().calculate(new Mod2Element(1), new Mod2Element(1)));
    }
}
