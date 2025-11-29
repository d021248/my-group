package d021248.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.product.DirectProduct;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Tests for group properties: isAbelian(), exponent().
 */
class GroupPropertiesTest {

    @Test
    void testIsAbelianCyclicGroup() {
        // All cyclic groups are abelian
        CyclicGroup z6 = GroupFactory.cyclic(6);
        assertTrue(z6.isAbelian());

        CyclicGroup z12 = GroupFactory.cyclic(12);
        assertTrue(z12.isAbelian());
    }

    @Test
    void testIsAbelianSymmetricGroup() {
        // S_1 and S_2 are abelian
        SymmetricGroup s1 = GroupFactory.symmetric(1);
        assertTrue(s1.isAbelian());

        SymmetricGroup s2 = GroupFactory.symmetric(2);
        assertTrue(s2.isAbelian());

        // S_n is non-abelian for n >= 3
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        assertFalse(s3.isAbelian());

        SymmetricGroup s4 = GroupFactory.symmetric(4);
        assertFalse(s4.isAbelian());
    }

    @Test
    void testIsAbelianDihedralGroup() {
        // D_2 is abelian (Klein 4-group)
        DihedralGroup d2 = GroupFactory.dihedral(2);
        assertTrue(d2.isAbelian());

        // D_n is non-abelian for n >= 3
        DihedralGroup d3 = GroupFactory.dihedral(3);
        assertFalse(d3.isAbelian());

        DihedralGroup d4 = GroupFactory.dihedral(4);
        assertFalse(d4.isAbelian());
    }

    @Test
    void testIsAbelianDirectProduct() {
        // Product of abelian groups is abelian
        var z2 = GroupFactory.cyclic(2);
        var z3 = GroupFactory.cyclic(3);
        var product = GroupFactory.directProduct(z2, z3);
        assertTrue(product.isAbelian());
    }

    @Test
    void testExponentCyclicGroup() {
        // For cyclic groups, exponent equals order
        CyclicGroup z6 = GroupFactory.cyclic(6);
        assertEquals(6, z6.exponent());

        CyclicGroup z12 = GroupFactory.cyclic(12);
        assertEquals(12, z12.exponent());

        CyclicGroup z1 = GroupFactory.cyclic(1);
        assertEquals(1, z1.exponent());
    }

    @Test
    void testExponentKleinFourGroup() {
        // Klein 4-group: all non-identity elements have order 2
        // So exponent = 2
        var z2a = GroupFactory.cyclic(2);
        var z2b = GroupFactory.cyclic(2);
        DirectProduct<?, ?> v4 = GroupFactory.directProduct(z2a, z2b);
        assertEquals(2, v4.exponent());
    }

    @Test
    void testExponentDirectProduct() {
        // Z_2 × Z_3: exponent = lcm(2,3) = 6
        var z2 = GroupFactory.cyclic(2);
        var z3 = GroupFactory.cyclic(3);
        var product = GroupFactory.directProduct(z2, z3);
        assertEquals(6, product.exponent());
    }

    @Test
    void testExponentZ2xZ4() {
        // Z_2 × Z_4: exponent = lcm(2,4) = 4
        var z2 = GroupFactory.cyclic(2);
        var z4 = GroupFactory.cyclic(4);
        var product = GroupFactory.directProduct(z2, z4);
        assertEquals(4, product.exponent());
    }

    @Test
    void testExponentDividesOrder() {
        // Exponent always divides order
        CyclicGroup z12 = GroupFactory.cyclic(12);
        assertTrue(z12.order() % z12.exponent() == 0);

        DihedralGroup d4 = GroupFactory.dihedral(4);
        assertTrue(d4.order() % d4.exponent() == 0);

        SymmetricGroup s3 = GroupFactory.symmetric(3);
        assertTrue(s3.order() % s3.exponent() == 0);
    }

    @Test
    void testExponentSymmetricGroup() {
        // S_3 has elements of orders 1, 2, 3
        // exponent = lcm(1,2,3) = 6
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        assertEquals(6, s3.exponent());
    }

    @Test
    void testExponentDihedralGroup() {
        // D_3 (order 6): rotations have order 1,3,3 and flips have order 2
        // exponent = lcm(1,2,3) = 6
        DihedralGroup d3 = GroupFactory.dihedral(3);
        assertEquals(6, d3.exponent());

        // D_4 (order 8): has elements of orders 1,2,4
        // exponent should be 4
        DihedralGroup d4 = GroupFactory.dihedral(4);
        assertEquals(4, d4.exponent());
    }
}
