package d021248.group.symmetric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class PermutationTest {
    @Test
    void testNormalizeSimpleCycle() {
        List<List<Integer>> cycles = List.of(List.of(1, 2, 3, 4));
        Permutation p = new Permutation(cycles);
        // (1 2 3 4) -> (1 4)(1 3)(1 2)
        List<List<Integer>> expected = List.of(
                List.of(1, 4),
                List.of(1, 3),
                List.of(1, 2));
        assertEquals(expected, p.cycles());
    }

    @Test
    void testNormalizeMultipleCycles() {
        List<List<Integer>> cycles = List.of(List.of(1, 2, 3), List.of(4, 5));
        Permutation p = new Permutation(cycles);
        // (1 2 3) -> (1 3)(1 2), (4 5) -> (4 5)
        List<List<Integer>> expected = List.of(
                List.of(1, 3),
                List.of(1, 2),
                List.of(4, 5));
        assertEquals(expected, p.cycles());
    }

    @Test
    void testNormalizeSingletonCycle() {
        List<List<Integer>> cycles = List.of(List.of(1));
        Permutation p = new Permutation(cycles);
        // Singleton cycles should be ignored
        assertTrue(p.cycles().isEmpty());
    }

    @Test
    void testToString() {
        List<List<Integer>> cycles = List.of(List.of(1, 2, 3));
        Permutation p = new Permutation(cycles);
        assertEquals("[[1, 3], [1, 2]]", p.toString());
    }

    @Test
    void testDuplicateInSameCycleThrows() {
        List<List<Integer>> cycles = List.of(List.of(1, 2, 2));
        try {
            new Permutation(cycles);
            assertTrue(false, "Expected exception for duplicate in same cycle");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Duplicate element in a cycle"));
        }
    }

    @Test
    void testDuplicateAcrossCyclesAllowed() {
        List<List<Integer>> cycles = List.of(List.of(1, 2), List.of(2, 3));
        // Should not throw
        Permutation p = new Permutation(cycles);
        List<List<Integer>> expected = List.of(
                List.of(1, 2),
                List.of(2, 3));
        assertEquals(expected, p.cycles());
    }

    @Test
    void testNullElementThrows() {
        List<Integer> cycleWithNull = new java.util.ArrayList<>();
        cycleWithNull.add(1);
        cycleWithNull.add(null);
        List<List<Integer>> cycles = java.util.Collections.singletonList(cycleWithNull);
        try {
            new Permutation(cycles);
            assertTrue(false, "Expected exception for null element");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Cycle elements must be non-null"));
        }
    }

    @Test
    void testNullOrEmptyCycleThrows() {
        List<List<Integer>> cyclesWithNull = new java.util.ArrayList<>();
        cyclesWithNull.add(null);
        try {
            new Permutation(cyclesWithNull);
            assertTrue(false, "Expected exception for null cycle");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Each cycle must be non-null and non-empty"));
        }
        List<List<Integer>> cyclesWithEmpty = new java.util.ArrayList<>();
        cyclesWithEmpty.add(new java.util.ArrayList<>());
        try {
            new Permutation(cyclesWithEmpty);
            assertTrue(false, "Expected exception for empty cycle");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Each cycle must be non-null and non-empty"));
        }
    }
}
