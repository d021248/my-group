package d021248.group.base;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

import d021248.group.api.Element;
import d021248.group.api.Operation;

class GeneratorTest {
    static class Mod3Element implements Element {
        private final int value;

        Mod3Element(int value) {
            this.value = (value % 3 + 3) % 3;
        }

        @Override
        public Element inverse() {
            return new Mod3Element(3 - value);
        }

        int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Mod3Element m && m.value == value;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(value);
        }
    }

    @Test
    void testGenerateCyclicGroup() {
        Operation<Mod3Element> op = (l, r) -> new Mod3Element(l.getValue() + r.getValue());
        Set<Mod3Element> gens = Set.of(new Mod3Element(1));
        Set<Mod3Element> all = Generator.generate(gens, op);
        assertEquals(Set.of(new Mod3Element(0), new Mod3Element(1), new Mod3Element(2)), all);
    }
}
