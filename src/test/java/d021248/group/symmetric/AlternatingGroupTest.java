package d021248.group.symmetric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import d021248.group.GroupFactory;

/**
 * Tests for AlternatingGroup.
 */
class AlternatingGroupTest {

    @Test
    void testA3Order() {
        AlternatingGroup a3 = new AlternatingGroup(3);
        assertEquals(3, a3.order()); // 3!/2 = 3
        assertEquals(3, a3.degree());
    }

    @Test
    void testA4Order() {
        AlternatingGroup a4 = new AlternatingGroup(4);
        assertEquals(12, a4.order()); // 4!/2 = 12
    }

    @Test
    void testA5Order() {
        AlternatingGroup a5 = new AlternatingGroup(5);
        assertEquals(60, a5.order()); // 5!/2 = 60
    }

    @Test
    void testAllElementsEven() {
        AlternatingGroup a4 = new AlternatingGroup(4);
        for (Permutation p : a4.elements()) {
            assertEquals(1, p.sign(), "All permutations in A_n must have sign +1");
        }
    }

    @Test
    void testIdentityIncluded() {
        AlternatingGroup a3 = new AlternatingGroup(3);
        Permutation id = Permutation.identity(3);
        assertTrue(a3.elements().contains(id));
        assertEquals(id, a3.identity());
    }

    @Test
    void testClosure() {
        // A_n is closed under composition
        AlternatingGroup a4 = new AlternatingGroup(4);
        for (Permutation p : a4.elements()) {
            for (Permutation q : a4.elements()) {
                Permutation product = a4.operate(p, q);
                assertTrue(a4.elements().contains(product),
                        "A_n must be closed under permutation composition");
                assertEquals(1, product.sign(), "Product of even permutations is even");
            }
        }
    }

    @Test
    void testInverses() {
        AlternatingGroup a4 = new AlternatingGroup(4);
        for (Permutation p : a4.elements()) {
            Permutation inv = a4.inverse(p);
            assertTrue(a4.elements().contains(inv), "Inverse of even permutation must be even");
            assertEquals(a4.identity(), a4.operate(p, inv));
        }
    }

    @Test
    void testFactoryMethod() {
        var a5 = GroupFactory.alternating(5);
        assertEquals(60, a5.order());
        assertTrue(a5 instanceof AlternatingGroup);
    }
}
