package d021248.group.symmetric;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class PermutationFactoryTest {

    @Test
    void testIdentity() {
        Permutation id3 = PermutationFactory.identity(3);
        assertArrayEquals(new int[] { 1, 2, 3 }, id3.mapping());

        Permutation id5 = PermutationFactory.identity(5);
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, id5.mapping());
    }

    @Test
    void testIdentityInvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> PermutationFactory.identity(0));
        assertThrows(IllegalArgumentException.class, () -> PermutationFactory.identity(-1));
    }

    @Test
    void testCycle() {
        Permutation cycle132 = PermutationFactory.cycle(1, 3, 2);
        assertArrayEquals(new int[] { 3, 1, 2 }, cycle132.mapping());

        Permutation cycle1234 = PermutationFactory.cycle(1, 2, 3, 4);
        assertArrayEquals(new int[] { 2, 3, 4, 1 }, cycle1234.mapping());
    }

    @Test
    void testTransposition() {
        Permutation swap12 = PermutationFactory.transposition(1, 2, 3);
        assertArrayEquals(new int[] { 2, 1, 3 }, swap12.mapping());

        Permutation swap25 = PermutationFactory.transposition(2, 5, 6);
        assertArrayEquals(new int[] { 1, 5, 3, 4, 2, 6 }, swap25.mapping());
    }

    @Test
    void testFromCycles() {
        // (1 3 2)(4 5) in S_6
        Permutation p = PermutationFactory.fromCycles(6,
                List.of(List.of(1, 3, 2), List.of(4, 5)));
        assertArrayEquals(new int[] { 3, 1, 2, 5, 4, 6 }, p.mapping());
    }

    @Test
    void testFromCyclesWithEmptyCycles() {
        // Should handle empty cycles gracefully
        Permutation p = PermutationFactory.fromCycles(4,
                List.of(List.of(), List.of(1, 2), List.of()));
        assertArrayEquals(new int[] { 2, 1, 3, 4 }, p.mapping());
    }

    @Test
    void testBackwardCompatibility() {
        // Verify that static methods on Permutation still work (delegating to factory)
        Permutation id = Permutation.identity(3);
        assertArrayEquals(new int[] { 1, 2, 3 }, id.mapping());

        Permutation cycle = Permutation.cycle(1, 2, 3);
        assertArrayEquals(new int[] { 2, 3, 1 }, cycle.mapping());

        Permutation trans = Permutation.transposition(1, 2, 4);
        assertArrayEquals(new int[] { 2, 1, 3, 4 }, trans.mapping());

        Permutation fromCycles = Permutation.fromCycles(3, List.of(List.of(1, 2)));
        assertArrayEquals(new int[] { 2, 1, 3 }, fromCycles.mapping());
    }
}
