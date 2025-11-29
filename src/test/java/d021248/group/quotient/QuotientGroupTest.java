package d021248.group.quotient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupGenerator;
import d021248.group.symmetric.AlternatingGroup;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Tests for quotient groups G/H.
 */
class QuotientGroupTest {

    @Test
    void testZ6modZ3() {
        // Z_6 / <3> where <3> = {0, 3} has order 2
        // Quotient has order 6/2 = 3
        CyclicGroup z6 = new CyclicGroup(6);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(3, 6)));

        QuotientGroup<CyclicElement> quotient = new QuotientGroup<>(z6, h);

        assertEquals(3, quotient.order()); // |G/H| = 6/2 = 3
        assertEquals(z6, quotient.parent());
        assertEquals(h, quotient.normalSubgroup());
    }

    @Test
    void testZ12modZ4() {
        // Z_12 / <4> where <4> = {0, 4, 8} has order 3
        // Quotient has order 12/3 = 4
        CyclicGroup z12 = new CyclicGroup(12);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z12, Set.of(new CyclicElement(4, 12)));

        QuotientGroup<CyclicElement> quotient = new QuotientGroup<>(z12, h);

        assertEquals(4, quotient.order()); // |G/H| = 12/3 = 4

        // Verify all cosets are distinct
        Set<Coset<CyclicElement>> cosets = quotient.elements();
        assertEquals(4, cosets.size());
    }

    @Test
    void testCosetEquality() {
        CyclicGroup z6 = new CyclicGroup(6);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(3, 6)));

        // 0H and 3H should be the same coset
        Coset<CyclicElement> coset0 = new Coset<>(z6, h, new CyclicElement(0, 6));
        Coset<CyclicElement> coset3 = new Coset<>(z6, h, new CyclicElement(3, 6));
        assertEquals(coset0, coset3);

        // 1H and 4H should be the same coset
        Coset<CyclicElement> coset1 = new Coset<>(z6, h, new CyclicElement(1, 6));
        Coset<CyclicElement> coset4 = new Coset<>(z6, h, new CyclicElement(4, 6));
        assertEquals(coset1, coset4);

        // But 0H ≠ 1H
        assertNotEquals(coset0, coset1);
    }

    @Test
    void testCosetElements() {
        CyclicGroup z6 = new CyclicGroup(6);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(2, 6)));

        Coset<CyclicElement> coset1 = new Coset<>(z6, h, new CyclicElement(1, 6));
        Set<CyclicElement> elements = coset1.elements();

        assertEquals(3, elements.size()); // |H| = 3, so each coset has 3 elements
        assertTrue(elements.contains(new CyclicElement(1, 6)));
        assertTrue(elements.contains(new CyclicElement(3, 6)));
        assertTrue(elements.contains(new CyclicElement(5, 6)));
    }

    @Test
    void testQuotientGroupOperation() {
        CyclicGroup z6 = new CyclicGroup(6);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(3, 6)));
        QuotientGroup<CyclicElement> quotient = new QuotientGroup<>(z6, h);

        Coset<CyclicElement> coset1H = new Coset<>(z6, h, new CyclicElement(1, 6));
        Coset<CyclicElement> coset2H = new Coset<>(z6, h, new CyclicElement(2, 6));

        // 1H + 1H = 2H
        Coset<CyclicElement> product = quotient.operate(coset1H, coset1H);
        assertEquals(coset2H, product);
    }

    @Test
    void testQuotientGroupIdentity() {
        CyclicGroup z6 = new CyclicGroup(6);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(3, 6)));
        QuotientGroup<CyclicElement> quotient = new QuotientGroup<>(z6, h);

        Coset<CyclicElement> identity = quotient.identity();
        assertEquals("H", identity.toString());

        // Identity coset should contain the subgroup elements
        assertEquals(h.elements(), identity.elements());
    }

    @Test
    void testCosetInverse() {
        CyclicGroup z6 = new CyclicGroup(6);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(3, 6)));

        Coset<CyclicElement> coset1 = new Coset<>(z6, h, new CyclicElement(1, 6));
        Coset<CyclicElement> inverse = coset1.inverse();

        // In Z_6, inverse of 1 is 5
        // 5H = {5,2} should equal inverse coset
        Coset<CyclicElement> coset5 = new Coset<>(z6, h, new CyclicElement(5, 6));
        assertEquals(coset5, inverse);
    }

    @Test
    void testS3moduloA3() {
        // S_3 / A_3 ≅ Z_2 (quotient by alternating group)
        SymmetricGroup s3 = new SymmetricGroup(3);
        AlternatingGroup a3Impl = new AlternatingGroup(3);
        Subgroup<Permutation> a3 = new Subgroup<>(s3, a3Impl.elements());

        QuotientGroup<Permutation> quotient = new QuotientGroup<>(s3, a3);

        assertEquals(2, quotient.order()); // |S_3| / |A_3| = 6 / 3 = 2
    }

    @Test
    void testNonNormalSubgroupThrows() {
        // In S_3, the subgroup generated by a single transposition is not normal
        SymmetricGroup s3 = new SymmetricGroup(3);
        Permutation trans = Permutation.transposition(1, 2, 3);
        Subgroup<Permutation> h = SubgroupGenerator.generate(s3, Set.of(trans));

        assertFalse(SubgroupGenerator.isNormal(s3, h));

        assertThrows(IllegalArgumentException.class, () -> {
            new QuotientGroup<>(s3, h);
        });
    }

    @Test
    void testTrivialQuotient() {
        CyclicGroup z6 = new CyclicGroup(6);
        Subgroup<CyclicElement> g = new Subgroup<>(z6, z6.elements());

        QuotientGroup<CyclicElement> quotient = new QuotientGroup<>(z6, g);

        assertEquals(1, quotient.order());
    }

    @Test
    void testQuotientByIdentity() {
        CyclicGroup z6 = new CyclicGroup(6);
        Subgroup<CyclicElement> identity = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(0, 6)));

        QuotientGroup<CyclicElement> quotient = new QuotientGroup<>(z6, identity);

        assertEquals(z6.order(), quotient.order());
    }

    @Test
    void testQuotientGroupAxioms() {
        CyclicGroup z12 = new CyclicGroup(12);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z12, Set.of(new CyclicElement(4, 12)));
        QuotientGroup<CyclicElement> quotient = new QuotientGroup<>(z12, h);

        Coset<CyclicElement> identity = quotient.identity();

        // Test identity element
        for (Coset<CyclicElement> coset : quotient.elements()) {
            assertEquals(coset, quotient.operate(identity, coset));
            assertEquals(coset, quotient.operate(coset, identity));
        }

        // Test inverses
        for (Coset<CyclicElement> coset : quotient.elements()) {
            Coset<CyclicElement> inv = quotient.inverse(coset);
            assertEquals(identity, quotient.operate(coset, inv));
            assertEquals(identity, quotient.operate(inv, coset));
        }
    }

    @Test
    void testCosetToString() {
        CyclicGroup z6 = new CyclicGroup(6);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(3, 6)));

        Coset<CyclicElement> identityCoset = new Coset<>(z6, h, new CyclicElement(0, 6));
        assertEquals("H", identityCoset.toString());

        Coset<CyclicElement> coset1 = new Coset<>(z6, h, new CyclicElement(1, 6));
        assertTrue(coset1.toString().contains("H"));
    }

    @Test
    void testFactoryMethod() {
        CyclicGroup z6 = GroupFactory.cyclic(6);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(3, 6)));

        QuotientGroup<CyclicElement> quotient = GroupFactory.quotient(z6, h);

        assertEquals(3, quotient.order());
        assertNotNull(quotient);
    }
}
