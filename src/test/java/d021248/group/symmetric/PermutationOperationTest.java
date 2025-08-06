package d021248.group.symmetric;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class PermutationOperationTest {
    @Test
    void testCalculate() {
        PermutationOperation op = new PermutationOperation();
        Permutation p1 = new Permutation(new int[] { 2, 3, 1 });
        Permutation p2 = new Permutation(new int[] { 3, 1, 2 });
        Permutation result = op.calculate(p1, p2);
        assertArrayEquals(new int[] { 1, 2, 3 }, result.mapping());
    }
}
