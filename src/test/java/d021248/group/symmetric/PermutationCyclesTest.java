package d021248.group.symmetric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PermutationCyclesTest {

    @Test
    void identityHasSizeOneCycles() {
        Permutation id = new Permutation(new int[] { 1, 2, 3, 4 });
        assertEquals(4, id.cycles().size()); // four 1-cycles
        assertEquals(1, id.sign());
        assertEquals("(1)(2)(3)(4)", id.toCycleString());
        assertEquals("(1)(2)(3)(4)", id.toCanonicalCycleString());
    }

    @Test
    void simpleThreeCycle() {
        Permutation p = new Permutation(new int[] { 2, 3, 1, 4 }); // (1 2 3)(4)
        assertEquals(2, p.cycles().size());
        // Parity: a 3-cycle decomposes into 2 transpositions -> even -> sign +1
        assertEquals(1, p.sign());
        assertEquals("(1 2 3)(4)", p.toCycleString());
        assertEquals("(1 2 3)(4)", p.toCanonicalCycleString());
    }

    @Test
    void disjointTwoCycles() {
        Permutation p = new Permutation(new int[] { 2, 1, 4, 3 }); // (1 2)(3 4)
        assertEquals(2, p.cycles().size());
        assertEquals(1, p.sign()); // two transpositions -> even
        assertEquals("(1 2)(3 4)", p.toCycleString());
        assertEquals("(1 2)(3 4)", p.toCanonicalCycleString());
    }

    @Test
    void rotatedCycleCanonical() {
        Permutation p = new Permutation(new int[] { 4, 5, 1, 3, 2 });
        String canonical = p.toCanonicalCycleString();
        assertTrue(canonical.startsWith("(1 "));
        assertTrue(canonical.endsWith(")"));
        // This permutation has cycles (1 4 3)(2 5) -> total transpositions =
        // (3-1)+(2-1)=3 -> odd -> sign -1
        assertEquals(-1, p.sign());
    }
}
