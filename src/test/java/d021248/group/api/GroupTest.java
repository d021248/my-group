package d021248.group.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.function.BinaryOperator;

import org.junit.jupiter.api.Test;

class GroupTest {
    @Test
    void testGroupElementsAndOperation() {
        Set<Integer> elems = Set.of(0, 1);
    BinaryOperator<Integer> op = (l, r) -> (l + r) % 2;
    SimpleGroup<Integer> group = new SimpleGroup<>(elems, op);
        assertEquals(elems, group.elements());
        assertEquals(0, group.operate(1, 1));
    }
}
