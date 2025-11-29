package d021248.group.homomorphism;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralElement;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupGenerator;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Tests for group homomorphisms.
 */
class HomomorphismTest {

    @Test
    void testTrivialHomomorphism() {
        CyclicGroup z6 = GroupFactory.cyclic(6);
        CyclicGroup z1 = GroupFactory.cyclic(1);

        Homomorphism<CyclicElement, CyclicElement> trivial = new Homomorphism<>(
                z6, z1,
                e -> z1.identity());

        assertTrue(HomomorphismAnalyzer.isHomomorphism(trivial));
        assertTrue(HomomorphismAnalyzer.isSurjective(trivial));
        assertFalse(HomomorphismAnalyzer.isInjective(trivial));

        Subgroup<CyclicElement> kernel = HomomorphismAnalyzer.kernel(trivial);
        assertEquals(6, kernel.order());
    }

    @Test
    void testSignHomomorphism() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        CyclicGroup z2 = GroupFactory.cyclic(2);

        Homomorphism<Permutation, CyclicElement> sign = new Homomorphism<>(
                s3, z2,
                p -> new CyclicElement(p.sign() == 1 ? 0 : 1, 2));

        assertTrue(HomomorphismAnalyzer.isHomomorphism(sign));
        assertTrue(HomomorphismAnalyzer.isSurjective(sign));
        assertFalse(HomomorphismAnalyzer.isInjective(sign));

        Subgroup<Permutation> kernel = HomomorphismAnalyzer.kernel(sign);
        assertEquals(3, kernel.order());

        Subgroup<CyclicElement> image = HomomorphismAnalyzer.image(sign);
        assertEquals(2, image.order());
    }

    @Test
    void testFirstIsomorphismTheoremSign() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        CyclicGroup z2 = GroupFactory.cyclic(2);

        Homomorphism<Permutation, CyclicElement> sign = new Homomorphism<>(
                s3, z2,
                p -> new CyclicElement(p.sign() == 1 ? 0 : 1, 2));

        int quotientOrder = HomomorphismAnalyzer.firstIsomorphismTheorem(sign);
        assertEquals(2, quotientOrder);
    }

    @Test
    void testIsomorphismCyclicGroups() {
        CyclicGroup z4a = GroupFactory.cyclic(4);
        CyclicGroup z4b = GroupFactory.cyclic(4);

        Homomorphism<CyclicElement, CyclicElement> iso = new Homomorphism<>(
                z4a, z4b,
                e -> new CyclicElement(e.value(), 4));

        assertTrue(HomomorphismAnalyzer.isHomomorphism(iso));
        assertTrue(HomomorphismAnalyzer.isInjective(iso));
        assertTrue(HomomorphismAnalyzer.isSurjective(iso));
        assertTrue(HomomorphismAnalyzer.isIsomorphism(iso));

        Subgroup<CyclicElement> kernel = HomomorphismAnalyzer.kernel(iso);
        assertEquals(1, kernel.order());
    }

    @Test
    void testProjectionHomomorphism() {
        CyclicGroup z6 = GroupFactory.cyclic(6);
        CyclicGroup z3 = GroupFactory.cyclic(3);

        Homomorphism<CyclicElement, CyclicElement> projection = new Homomorphism<>(
                z6, z3,
                e -> new CyclicElement(e.value() % 3, 3));

        assertTrue(HomomorphismAnalyzer.isHomomorphism(projection));
        assertTrue(HomomorphismAnalyzer.isSurjective(projection));
        assertFalse(HomomorphismAnalyzer.isInjective(projection));

        Subgroup<CyclicElement> kernel = HomomorphismAnalyzer.kernel(projection);
        assertEquals(2, kernel.order());
    }

    @Test
    void testCompositionHomomorphisms() {
        CyclicGroup z6 = GroupFactory.cyclic(6);
        CyclicGroup z3 = GroupFactory.cyclic(3);
        CyclicGroup z1 = GroupFactory.cyclic(1);

        Homomorphism<CyclicElement, CyclicElement> phi = new Homomorphism<>(
                z6, z3,
                e -> new CyclicElement(e.value() % 3, 3));

        Homomorphism<CyclicElement, CyclicElement> psi = new Homomorphism<>(
                z3, z1,
                e -> z1.identity());

        Homomorphism<CyclicElement, CyclicElement> composed = HomomorphismAnalyzer.compose(phi, psi);

        assertTrue(HomomorphismAnalyzer.isHomomorphism(composed));
        assertEquals(z6, composed.source());
        assertEquals(z1, composed.target());
    }

    @Test
    void testKernelIsNormal() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        CyclicGroup z2 = GroupFactory.cyclic(2);

        Homomorphism<Permutation, CyclicElement> sign = new Homomorphism<>(
                s3, z2,
                p -> new CyclicElement(p.sign() == 1 ? 0 : 1, 2));

        Subgroup<Permutation> kernel = HomomorphismAnalyzer.kernel(sign);

        assertTrue(SubgroupGenerator.isNormal(s3, kernel));
    }

    @Test
    void testDihedralHomomorphism() {
        DihedralGroup d4 = GroupFactory.dihedral(4);
        CyclicGroup z2 = GroupFactory.cyclic(2);

        Homomorphism<DihedralElement, CyclicElement> phi = new Homomorphism<>(
                d4, z2,
                e -> new CyclicElement(e.flip().ordinal(), 2));

        assertTrue(HomomorphismAnalyzer.isHomomorphism(phi));

        Subgroup<DihedralElement> kernel = HomomorphismAnalyzer.kernel(phi);
        assertEquals(4, kernel.order());
    }

    @Test
    void testImageIsSubgroup() {
        CyclicGroup z12 = GroupFactory.cyclic(12);
        CyclicGroup z4 = GroupFactory.cyclic(4);

        Homomorphism<CyclicElement, CyclicElement> phi = new Homomorphism<>(
                z12, z4,
                e -> new CyclicElement(e.value() % 4, 4));

        Subgroup<CyclicElement> image = HomomorphismAnalyzer.image(phi);

        assertEquals(4, image.order());
        assertTrue(HomomorphismAnalyzer.isSurjective(phi));
    }

    @Test
    void testInjectiveImpliesTrivialKernel() {
        CyclicGroup z5 = GroupFactory.cyclic(5);
        CyclicGroup z5copy = GroupFactory.cyclic(5);

        Homomorphism<CyclicElement, CyclicElement> iso = new Homomorphism<>(
                z5, z5copy,
                e -> new CyclicElement(e.value(), 5));

        assertTrue(HomomorphismAnalyzer.isInjective(iso));

        Subgroup<CyclicElement> kernel = HomomorphismAnalyzer.kernel(iso);
        assertEquals(1, kernel.order());
        assertTrue(kernel.elements().contains(z5.identity()));
    }
}
