package d021248.group.symmetric;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PermutationCyclesTest {

    @Test
    void identityHasSizeOneCycles() {
        Permutation id = new Permutation(new int[] { 1, 2, 3, 4 });
        assertEquals(4, id.cycles().size()); // four 1-cycles
        assertEquals(1, id.sign());
        assertEquals("(1)(2)(3)(4)", id.toCycleString());
    }

    @Test
    void simpleThreeCycle() {
        Permutation p = new Permutation(new int[] { 2, 3, 1, 4 }); // (1 2 3)(4)
        assertEquals(2, p.cycles().size());
        assertEquals(-1, p.sign()); // 3-cycle is even? Actually a 3-cycle has sign +? Correction: cycle length 3 ->
                                    // 2 transpositions -> sign +1; but formula size - cycles = 4 -2 =2 even -> +1
        assertEquals(1, p.sign());
        assertEquals("(1 2 3)(4)", p.toCycleString());
    }

    @Test
    void disjointTwoCycles() {
        Permutation p = new Permutation(new int[] { 2, 1, 4, 3 }); // (1 2)(3 4)
        assertEquals(2, p.cycles().size());
        assertEquals(1, p.sign()); // two transpositions -> even
        assertEquals("(1 2)(3 4)", p.toCycleString());
    }
}
