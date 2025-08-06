package d021248.group.symmetric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

class SymmetricGroupHelperTest {
    @Test
    void testGetGenerators() {
        Set<Permutation> gens = SymmetricGroupHelper.getGenerators(3);
        assertEquals(2, gens.size());
        assertTrue(gens.contains(new Permutation(new int[] { 2, 3, 1 })));
        assertTrue(gens.contains(new Permutation(new int[] { 2, 1, 3 })));
    }
}
