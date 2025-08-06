package d021248.group.symmetric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SymmetricGroupTest {
    @Test
    void testSymmetricGroupOrder() {
        SymmetricGroup s3 = new SymmetricGroup(3);
        // S_3 has 6 elements
        assertEquals(6, s3.elements().size());
    }

    @Test
    void testSymmetricGroupContainsIdentity() {
        SymmetricGroup s3 = new SymmetricGroup(3);
        Permutation identity = new Permutation(new int[] { 1, 2, 3 });
        assertTrue(s3.elements().contains(identity));
    }
}
