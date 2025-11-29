package d021248.group.subgroup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralElement;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Tests for center subgroup computation.
 */
class CenterSubgroupTest {

    @Test
    void testCenterOfAbelianGroup() {
        // For abelian groups, Z(G) = G
        CyclicGroup z6 = GroupFactory.cyclic(6);
        Subgroup<CyclicElement> center = SubgroupGenerator.center(z6);

        assertEquals(z6.order(), center.order());
        assertEquals(z6.elements(), center.elements());
    }

    @Test
    void testCenterOfCyclicGroup() {
        // All cyclic groups are abelian
        CyclicGroup z12 = GroupFactory.cyclic(12);
        Subgroup<CyclicElement> center = SubgroupGenerator.center(z12);

        assertEquals(12, center.order());
    }

    @Test
    void testCenterOfS3() {
        // S_3 is non-abelian, center should be trivial
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Subgroup<Permutation> center = SubgroupGenerator.center(s3);

        // Only identity element
        assertEquals(1, center.order());
        assertTrue(center.elements().contains(s3.identity()));
    }

    @Test
    void testCenterOfDihedralGroup() {
        // D_3 (order 6) has trivial center
        DihedralGroup d3 = GroupFactory.dihedral(3);
        Subgroup<DihedralElement> center = SubgroupGenerator.center(d3);

        assertEquals(1, center.order());
    }

    @Test
    void testCenterOfD4() {
        // D_4 (order 8) has center of order 2: {e, r^2}
        DihedralGroup d4 = GroupFactory.dihedral(4);
        Subgroup<DihedralElement> center = SubgroupGenerator.center(d4);

        assertEquals(2, center.order());
        assertTrue(center.elements().contains(d4.identity()));
    }

    @Test
    void testCenterIsNormal() {
        // Center is always normal
        SymmetricGroup s4 = GroupFactory.symmetric(4);
        Subgroup<Permutation> center = SubgroupGenerator.center(s4);

        assertTrue(SubgroupGenerator.isNormal(s4, center));
    }

    @Test
    void testCenterOfTrivialGroup() {
        CyclicGroup z1 = GroupFactory.cyclic(1);
        Subgroup<CyclicElement> center = SubgroupGenerator.center(z1);

        assertEquals(1, center.order());
    }
}
