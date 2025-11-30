package d021248.group.automorphism;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.homomorphism.GroupHomomorphism;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

class AutomorphismTest {

    @Test
    void testIdentityAutomorphism() {
        CyclicGroup z4 = GroupFactory.cyclic(4);
        Automorphism<CyclicElement> id = AutomorphismAnalyzer.identity(z4);

        for (CyclicElement e : z4.elements()) {
            assertEquals(e, id.apply(e));
        }

        assertTrue(AutomorphismAnalyzer.isAutomorphism(id));
    }

    @Test
    void testInnerAutomorphismAbelianGroup() {
        // For abelian groups, all inner automorphisms are identity
        CyclicGroup z6 = GroupFactory.cyclic(6);
        CyclicElement g = new CyclicElement(2, 6);

        Automorphism<CyclicElement> inner = AutomorphismAnalyzer.innerAutomorphism(z6, g);

        // In abelian group: ghg^-1 = gg^-1h = h
        for (CyclicElement h : z6.elements()) {
            assertEquals(h, inner.apply(h));
        }
    }

    @Test
    void testInnerAutomorphismNonAbelianGroup() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Permutation g = new Permutation(new int[] { 2, 1, 3 }); // (1 2)
        Permutation h = new Permutation(new int[] { 2, 3, 1 }); // (1 2 3)

        Automorphism<Permutation> inner = AutomorphismAnalyzer.innerAutomorphism(s3, g);

        // Compute ghg^-1
        Permutation conjugate = inner.apply(h);

        // (1 2) · (1 2 3) · (1 2) = (1 3 2)
        Permutation expected = new Permutation(new int[] { 3, 1, 2 }); // (1 3 2)
        assertEquals(expected, conjugate);

        assertTrue(AutomorphismAnalyzer.isAutomorphism(inner));
    }

    @Test
    void testAllInnerAutomorphismsAbelian() {
        CyclicGroup z4 = GroupFactory.cyclic(4);
        Set<Automorphism<CyclicElement>> innerAutos = AutomorphismAnalyzer.innerAutomorphisms(z4);

        // Should have 4 inner automorphisms (one per element)
        assertEquals(4, innerAutos.size());

        // All should be identity in abelian group
        Automorphism<CyclicElement> id = AutomorphismAnalyzer.identity(z4);
        for (Automorphism<CyclicElement> auto : innerAutos) {
            assertTrue(AutomorphismAnalyzer.areEqual(auto, id));
        }
    }

    @Test
    void testAllInnerAutomorphismsNonAbelian() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Set<Automorphism<Permutation>> innerAutos = AutomorphismAnalyzer.innerAutomorphisms(s3);

        // Should have 6 inner automorphisms (one per element)
        assertEquals(6, innerAutos.size());

        // Each should be a valid automorphism
        for (Automorphism<Permutation> auto : innerAutos) {
            assertTrue(AutomorphismAnalyzer.isAutomorphism(auto));
        }
    }

    @Test
    void testCountDistinctInnerAutomorphisms() {
        // For S_3: |Inn(S_3)| = |S_3| / |Z(S_3)| = 6 / 1 = 6
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        int distinct = AutomorphismAnalyzer.countDistinctInnerAutomorphisms(s3);
        assertEquals(6, distinct);

        // For Z_6: |Inn(Z_6)| = |Z_6| / |Z(Z_6)| = 6 / 6 = 1 (abelian)
        CyclicGroup z6 = GroupFactory.cyclic(6);
        distinct = AutomorphismAnalyzer.countDistinctInnerAutomorphisms(z6);
        assertEquals(1, distinct);
    }

    @Test
    void testComposeAutomorphisms() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Permutation g1 = new Permutation(new int[] { 2, 1, 3 }); // (1 2)
        Permutation g2 = new Permutation(new int[] { 2, 3, 1 }); // (1 2 3)

        Automorphism<Permutation> auto1 = AutomorphismAnalyzer.innerAutomorphism(s3, g1);
        Automorphism<Permutation> auto2 = AutomorphismAnalyzer.innerAutomorphism(s3, g2);

        Automorphism<Permutation> composed = AutomorphismAnalyzer.compose(auto1, auto2);

        assertTrue(AutomorphismAnalyzer.isAutomorphism(composed));

        // Verify composition: (auto1 ∘ auto2)(h) = auto1(auto2(h))
        Permutation h = new Permutation(new int[] { 1, 3, 2 }); // (2 3)
        Permutation result = composed.apply(h);
        Permutation expected = auto1.apply(auto2.apply(h));
        assertEquals(expected, result);
    }

    @Test
    void testInverseAutomorphism() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Permutation g = new Permutation(new int[] { 2, 3, 1 }); // (1 2 3)

        Automorphism<Permutation> auto = AutomorphismAnalyzer.innerAutomorphism(s3, g);
        Automorphism<Permutation> inv = AutomorphismAnalyzer.inverse(auto);

        assertTrue(AutomorphismAnalyzer.isAutomorphism(inv));

        // Verify inverse: auto ∘ inv = id
        Automorphism<Permutation> composed = AutomorphismAnalyzer.compose(auto, inv);
        Automorphism<Permutation> identity = AutomorphismAnalyzer.identity(s3);

        assertTrue(AutomorphismAnalyzer.areEqual(composed, identity));
    }

    @Test
    void testIsInner() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Permutation g = new Permutation(new int[] { 2, 1, 3 }); // (1 2)

        Automorphism<Permutation> inner = AutomorphismAnalyzer.innerAutomorphism(s3, g);
        assertTrue(AutomorphismAnalyzer.isInner(inner));

        Automorphism<Permutation> id = AutomorphismAnalyzer.identity(s3);
        assertTrue(AutomorphismAnalyzer.isInner(id)); // Identity is Inn_e
    }

    @Test
    void testAutomorphismEquality() {
        CyclicGroup z4 = GroupFactory.cyclic(4);
        CyclicElement g1 = new CyclicElement(0, 4);
        CyclicElement g2 = new CyclicElement(2, 4);

        Automorphism<CyclicElement> auto1 = AutomorphismAnalyzer.innerAutomorphism(z4, g1);
        Automorphism<CyclicElement> auto2 = AutomorphismAnalyzer.innerAutomorphism(z4, g2);

        // Both should be identity in abelian group
        assertTrue(AutomorphismAnalyzer.areEqual(auto1, auto2));
    }

    @Test
    void testIsComplete() {
        // S_3 has trivial center, so it should be complete
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        assertTrue(AutomorphismAnalyzer.isComplete(s3));

        // Z_6 has non-trivial center (entire group is abelian), so not complete
        CyclicGroup z6 = GroupFactory.cyclic(6);
        assertFalse(AutomorphismAnalyzer.isComplete(z6));
    }

    @Test
    void testCustomAutomorphism() {
        // Create a custom automorphism on Z_4: φ(n) = 3n (mod 4)
        CyclicGroup z4 = GroupFactory.cyclic(4);
        GroupHomomorphism<CyclicElement, CyclicElement> phi = e -> new CyclicElement((e.value() * 3) % 4, 4);

        Automorphism<CyclicElement> auto = new Automorphism<>(z4, phi);

        // Check it's an automorphism
        assertTrue(AutomorphismAnalyzer.isAutomorphism(auto));

        // φ(1) = 3, φ(2) = 2, φ(3) = 1
        assertEquals(new CyclicElement(3, 4), auto.apply(new CyclicElement(1, 4)));
        assertEquals(new CyclicElement(2, 4), auto.apply(new CyclicElement(2, 4)));
        assertEquals(new CyclicElement(1, 4), auto.apply(new CyclicElement(3, 4)));

        // This is not an inner automorphism (Z_4 has only identity inner auto)
        assertFalse(AutomorphismAnalyzer.isInner(auto));
    }
}
