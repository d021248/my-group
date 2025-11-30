package d021248.group.subgroup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralElement;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.symmetric.AlternatingGroup;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Tests for commutator subgroup computation.
 */
class CommutatorSubgroupTest {

    @Test
    void commutatorOfAbelianGroup() {
        CyclicGroup z6 = GroupFactory.cyclic(6);
        Subgroup<CyclicElement> commutator = SubgroupGenerator.commutatorSubgroup(z6);

        assertEquals(1, commutator.order());
        assertTrue(commutator.elements().contains(z6.identity()));
    }

    @Test
    void testCommutatorOfCyclicGroup() {
        // All cyclic groups are abelian
        CyclicGroup z12 = GroupFactory.cyclic(12);
        Subgroup<CyclicElement> commutator = SubgroupGenerator.commutatorSubgroup(z12);

        assertEquals(1, commutator.order());
    }

    @Test
    void testCommutatorOfS3() {
        // [S_3, S_3] = A_3 (alternating group)
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Subgroup<Permutation> commutator = SubgroupGenerator.commutatorSubgroup(s3);

        // A_3 has order 3
        assertEquals(3, commutator.order());

        // Verify it equals A_3
        AlternatingGroup a3 = GroupFactory.alternating(3);
        assertEquals(a3.elements(), commutator.elements());
    }

    @Test
    void testCommutatorOfS4() {
        // [S_4, S_4] = A_4
        SymmetricGroup s4 = GroupFactory.symmetric(4);
        Subgroup<Permutation> commutator = SubgroupGenerator.commutatorSubgroup(s4);

        // A_4 has order 12
        assertEquals(12, commutator.order());

        // Verify it equals A_4
        AlternatingGroup a4 = GroupFactory.alternating(4);
        assertEquals(a4.elements(), commutator.elements());
    }

    @Test
    void testCommutatorIsNormal() {
        // Commutator subgroup is always normal
        DihedralGroup d4 = GroupFactory.dihedral(4);
        Subgroup<DihedralElement> commutator = SubgroupGenerator.commutatorSubgroup(d4);

        assertTrue(SubgroupGenerator.isNormal(d4, commutator));
    }

    @Test
    void testCommutatorOfDihedralGroup() {
        // [D_n, D_n] is the cyclic subgroup of rotations
        DihedralGroup d3 = GroupFactory.dihedral(3);
        Subgroup<DihedralElement> commutator = SubgroupGenerator.commutatorSubgroup(d3);

        // For D_3, commutator has order 3 (the rotation subgroup)
        assertEquals(3, commutator.order());
    }

    @Test
    void testCommutatorOfTrivialGroup() {
        CyclicGroup z1 = GroupFactory.cyclic(1);
        Subgroup<CyclicElement> commutator = SubgroupGenerator.commutatorSubgroup(z1);

        assertEquals(1, commutator.order());
    }
}
