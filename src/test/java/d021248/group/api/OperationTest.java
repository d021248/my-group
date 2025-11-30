package d021248.group.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OperationTest {
    static class AddElement implements Element {
        private final int value;

        AddElement(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof AddElement a && a.value == value;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(value);
        }
    }

    @Test
    void testCalculate() {
        Operation<AddElement> add = (l, r) -> new AddElement(l.getValue() + r.getValue());
        AddElement a = new AddElement(2);
        AddElement b = new AddElement(3);
        assertEquals(new AddElement(5), add.calculate(a, b));
    }
}
