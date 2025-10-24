package d021248.group.symmetric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import d021248.group.strategy.SymmetricGenerationStrategy;

class SymmetricGroupHelperTest {
    @Test
    void testGetGenerators() {
        SymmetricGroup s3 = new SymmetricGroup(3);
        Set<Permutation> gens = SymmetricGenerationStrategy.get().generators(s3);
        assertEquals(2, gens.size());
        assertTrue(gens.contains(new Permutation(new int[] { 2, 3, 1 })));
        assertTrue(gens.contains(new Permutation(new int[] { 2, 1, 3 })));
    }
}
