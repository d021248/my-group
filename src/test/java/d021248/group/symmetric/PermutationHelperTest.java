package d021248.group.symmetric;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import d021248.group.groups.symmetric.Permutation;

class PermutationHelperTest {
    @Test
    void testInvalidPermutationMapping() {
        // Too short
        assertThrows(IllegalArgumentException.class, () -> new Permutation(new int[] {}));
        // Duplicate value
        assertThrows(IllegalArgumentException.class, () -> new Permutation(new int[] { 1, 2, 2 }));
        // Value out of range
        assertThrows(IllegalArgumentException.class, () -> new Permutation(new int[] { 0, 2, 3 }));
        assertThrows(IllegalArgumentException.class, () -> new Permutation(new int[] { 1, 2, 4 }));
    }
}
