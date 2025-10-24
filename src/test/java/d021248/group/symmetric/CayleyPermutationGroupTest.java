package d021248.group.symmetric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;

class CayleyPermutationGroupTest {
    @Test
    void cyclicGroupCayleyRepresentation() {
        CyclicGroup z5 = new CyclicGroup(5);
        CayleyPermutationGroup<CyclicElement> permGroup = new CayleyPermutationGroup<>(z5);

        // Order preserved (Cayley's theorem)
        assertEquals(z5.order(), permGroup.order());

        // Identity permutation corresponds to left multiplication by identity
        Permutation idPerm = permGroup.identity();
        assertEquals(0, idPerm.transpositionLength()); // identity should be all fixed points

        // All permutations should be 5-cycles for generators and identity for 0
        Set<Permutation> perms = permGroup.elements();
        assertEquals(5, perms.size());
        assertTrue(perms.stream().anyMatch(p -> p.toTupleString().equals("(1,2,3,4,5)")), "identity tuple must exist");
    }
}
