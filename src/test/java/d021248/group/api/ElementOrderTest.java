package d021248.group.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralElement;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.dihedral.Flip;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Test element order computation across different group types.
 */
class ElementOrderTest {

    @Test
    void testCyclicElementOrder() {
        CyclicGroup z12 = new CyclicGroup(12);

        // order of 1 is 12 (generates full group)
        assertEquals(12, z12.order(new CyclicElement(1, 12)));

        // order of 4 is 3 (4+4+4 ≡ 0 mod 12)
        assertEquals(3, z12.order(new CyclicElement(4, 12)));

        // order of 6 is 2 (6+6 ≡ 0 mod 12)
        assertEquals(2, z12.order(new CyclicElement(6, 12)));

        // identity has order 1
        assertEquals(1, z12.order(new CyclicElement(0, 12)));
    }

    @Test
    void testPermutationOrder() {
        SymmetricGroup s5 = new SymmetricGroup(5);

        // identity has order 1
        Permutation id = Permutation.identity(5);
        assertEquals(1, s5.order(id));

        // transposition has order 2
        Permutation transposition = Permutation.transposition(1, 2, 5);
        assertEquals(2, s5.order(transposition));

        // 3-cycle has order 3 - use fromCycles to specify size
        Permutation threeCycle = Permutation.fromCycles(5, java.util.List.of(java.util.List.of(1, 2, 3)));
        assertEquals(3, s5.order(threeCycle));

        // Product of disjoint cycles: (1 2)(3 4 5) has order lcm(2,3) = 6
        Permutation product = Permutation.fromCycles(5, java.util.List.of(
                java.util.List.of(1, 2),
                java.util.List.of(3, 4, 5)));
        assertEquals(6, s5.order(product));
    }

    @Test
    void testDihedralElementOrder() {
        DihedralGroup d6 = new DihedralGroup(6);

        // rotation by 60° (360/6) has order 6
        DihedralElement rotation = new DihedralElement(1, Flip.ROTATION, 6);
        assertEquals(6, d6.order(rotation));

        // rotation by 120° has order 3
        DihedralElement rotation120 = new DihedralElement(2, Flip.ROTATION, 6);
        assertEquals(3, d6.order(rotation120));

        // all reflections have order 2
        DihedralElement reflection = new DihedralElement(0, Flip.REFLECTION, 6);
        assertEquals(2, d6.order(reflection));

        // identity has order 1
        assertEquals(1, d6.order(d6.identity()));
    }

    @Test
    void testOrderDividesGroupOrder() {
        // By Lagrange's theorem, element order divides group order
        CyclicGroup z20 = new CyclicGroup(20);
        for (int i = 0; i < 20; i++) {
            CyclicElement e = new CyclicElement(i, 20);
            int order = z20.order(e);
            assertEquals(0, 20 % order,
                    "Order " + order + " of element " + i + " should divide group order 20");
        }
    }
}
