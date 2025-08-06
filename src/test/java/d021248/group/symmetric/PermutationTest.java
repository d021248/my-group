package d021248.group.symmetric;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class PermutationTest {
    @Test
    void testInverse() {
        Permutation p = new Permutation(new int[] { 2, 3, 1 });
        Permutation inv = p.inverse();
        assertArrayEquals(new int[] { 3, 1, 2 }, inv.mapping());
        assertEquals(p, inv.inverse());
    }

    @Test
    void testEqualsAndHashCode() {
        Permutation p1 = new Permutation(new int[] { 1, 2, 3 });
        Permutation p2 = new Permutation(new int[] { 1, 2, 3 });
        Permutation p3 = new Permutation(new int[] { 2, 1, 3 });
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        Permutation p = new Permutation(new int[] { 2, 3, 1 });
        assertEquals("[2, 3, 1]", p.toString());
    }
}
