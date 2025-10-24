package d021248.group.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

import d021248.group.Group;
import d021248.group.GroupAssertions;

class GroupTest {
    static class IntElement implements Element {
        private final int value;

        IntElement(int value) {
            this.value = value;
        }

        @Override
        public Element inverse() {
            // Group is intended to model Z_2 under addition; every element is its own
            // inverse.
            return new IntElement(value);
        }

        int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof IntElement i && i.value == value;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(value);
        }
    }

    static class IntGroup implements Group<IntElement> {
        private final Set<IntElement> elements;
        private final Operation<IntElement> op;

        IntGroup(Set<IntElement> elements, Operation<IntElement> op) {
            this.elements = elements;
            this.op = op;
        }

        @Override
        public Set<IntElement> elements() {
            return elements;
        }

        @Override
        public Operation<IntElement> operation() {
            return op;
        }

        @Override
        public IntElement identity() {
            return new IntElement(0);
        }

    }

    @Test
    void testGroupElementsAndOperation() {
        Set<IntElement> elems = Set.of(new IntElement(0), new IntElement(1));
        Operation<IntElement> op = (l, r) -> new IntElement((l.getValue() + r.getValue()) % 2);
        Group<IntElement> group = new IntGroup(elems, op);
        assertEquals(elems, group.elements());
        assertEquals(0, group.operation().calculate(new IntElement(1), new IntElement(1)).getValue());
        GroupAssertions.assertIdentityLaw(group);
        GroupAssertions.assertInverseLaw(group);
    }
}
