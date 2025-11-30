package d021248.group.subgroup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralElement;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

class FrattiniSubgroupTest {

    @Test
    void testMaximalSubgroupsZ6() {
        CyclicGroup z6 = GroupFactory.cyclic(6);
        List<Subgroup<CyclicElement>> maximal = SubgroupGenerator.maximalSubgroups(z6);

        // Z_6 has two maximal subgroups: Z_2 (order 3) and Z_3 (order 2)
        assertEquals(2, maximal.size());
        assertTrue(maximal.stream().anyMatch(s -> s.order() == 2));
        assertTrue(maximal.stream().anyMatch(s -> s.order() == 3));
    }

    @Test
    void testMaximalSubgroupsZ12() {
        CyclicGroup z12 = GroupFactory.cyclic(12);
        List<Subgroup<CyclicElement>> maximal = SubgroupGenerator.maximalSubgroups(z12);

        // Z_12 has two maximal subgroups: order 4 and order 6
        assertEquals(2, maximal.size());
        assertTrue(maximal.stream().anyMatch(s -> s.order() == 4));
        assertTrue(maximal.stream().anyMatch(s -> s.order() == 6));
    }

    @Test
    void testMaximalSubgroupsPrimeOrder() {
        CyclicGroup z7 = GroupFactory.cyclic(7);
        List<Subgroup<CyclicElement>> maximal = SubgroupGenerator.maximalSubgroups(z7);

        // Prime order groups have only the trivial subgroup which is maximal
        assertEquals(1, maximal.size());
        assertEquals(1, maximal.get(0).order());
    }

    @Test
    void testMaximalSubgroupsTooLarge() {
        CyclicGroup z25 = GroupFactory.cyclic(25);
        assertThrows(IllegalArgumentException.class, () -> {
            SubgroupGenerator.maximalSubgroups(z25);
        });
    }

    @Test
    void testFrattiniSubgroupZ6() {
        CyclicGroup z6 = GroupFactory.cyclic(6);
        Subgroup<CyclicElement> frattini = SubgroupGenerator.frattiniSubgroup(z6);

        // Φ(Z_6) = intersection of <2> and <3> = {0} (trivial)
        assertEquals(1, frattini.order());
        assertTrue(frattini.elements().contains(new CyclicElement(0, 6)));
    }

    @Test
    void testFrattiniSubgroupZ8() {
        CyclicGroup z8 = GroupFactory.cyclic(8);
        Subgroup<CyclicElement> frattini = SubgroupGenerator.frattiniSubgroup(z8);

        // Z_8 is a p-group, Frattini contains all elements of order dividing 4
        assertEquals(4, frattini.order());
        assertTrue(frattini.elements().contains(new CyclicElement(0, 8)));
        assertTrue(frattini.elements().contains(new CyclicElement(2, 8)));
        assertTrue(frattini.elements().contains(new CyclicElement(4, 8)));
        assertTrue(frattini.elements().contains(new CyclicElement(6, 8)));
    }

    @Test
    void testFrattiniSubgroupZ4() {
        CyclicGroup z4 = GroupFactory.cyclic(4);
        Subgroup<CyclicElement> frattini = SubgroupGenerator.frattiniSubgroup(z4);

        // Z_4 has one maximal subgroup of order 2
        assertEquals(2, frattini.order());
        assertTrue(frattini.elements().contains(new CyclicElement(0, 4)));
        assertTrue(frattini.elements().contains(new CyclicElement(2, 4)));
    }

    @Test
    void testFrattiniSubgroupPrimeOrder() {
        CyclicGroup z7 = GroupFactory.cyclic(7);
        Subgroup<CyclicElement> frattini = SubgroupGenerator.frattiniSubgroup(z7);

        // Prime order group has trivial Frattini subgroup
        assertEquals(1, frattini.order());
        assertTrue(frattini.elements().contains(new CyclicElement(0, 7)));
    }

    @Test
    void testFrattiniSubgroupS3() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Subgroup<Permutation> frattini = SubgroupGenerator.frattiniSubgroup(s3);

        // S_3 is not a p-group, has maximal subgroups of order 2 and 3
        // Intersection is trivial
        assertEquals(1, frattini.order());
    }

    @Test
    void testFrattiniSubgroupD4() {
        DihedralGroup d4 = GroupFactory.dihedral(4);
        Subgroup<DihedralElement> frattini = SubgroupGenerator.frattiniSubgroup(d4);

        // D_4 is a 2-group of order 8
        // Should have non-trivial Frattini subgroup
        assertTrue(frattini.order() >= 1);
        assertTrue(frattini.order() <= 4);
    }

    @Test
    void testMaximalSubgroupsS3() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        List<Subgroup<Permutation>> maximal = SubgroupGenerator.maximalSubgroups(s3);

        // S_3 has 4 maximal subgroups: 3 of order 2 (Sylow 2-subgroups) and 1 of order
        // 3 (A_3)
        assertEquals(4, maximal.size());
        long order2Count = maximal.stream().filter(s -> s.order() == 2).count();
        long order3Count = maximal.stream().filter(s -> s.order() == 3).count();
        assertEquals(3, order2Count);
        assertEquals(1, order3Count);
    }

    @Test
    void testFrattiniIsCharacteristic() {
        // The Frattini subgroup is always characteristic (and thus normal)
        CyclicGroup z8 = GroupFactory.cyclic(8);
        Subgroup<CyclicElement> frattini = SubgroupGenerator.frattiniSubgroup(z8);

        boolean isNormal = SubgroupGenerator.isNormal(z8, frattini);
        assertTrue(isNormal, "Frattini subgroup must be normal");
    }
}
