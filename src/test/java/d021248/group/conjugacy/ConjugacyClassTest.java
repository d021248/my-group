package d021248.group.conjugacy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * Tests for conjugacy class computation.
 */
class ConjugacyClassTest {

    @Test
    void testConjugacyClassesAbelianGroup() {
        // In abelian groups, each element forms its own conjugacy class
        CyclicGroup z6 = GroupFactory.cyclic(6);
        List<ConjugacyClass<CyclicElement>> classes = ConjugacyAnalyzer.conjugacyClasses(z6);

        assertEquals(6, classes.size());
        for (ConjugacyClass<CyclicElement> cl : classes) {
            assertEquals(1, cl.size()); // Singleton classes
        }
    }

    @Test
    void testConjugacyClassesS3() {
        // S_3 has 3 conjugacy classes:
        // - {identity}
        // - {3 transpositions}
        // - {2 3-cycles}
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        List<ConjugacyClass<Permutation>> classes = ConjugacyAnalyzer.conjugacyClasses(s3);

        assertEquals(3, classes.size());

        Map<Integer, Long> classSizes = ConjugacyAnalyzer.classEquation(s3);
        assertEquals(1, classSizes.get(1)); // 1 class of size 1
        assertEquals(1, classSizes.get(2)); // 1 class of size 2
        assertEquals(1, classSizes.get(3)); // 1 class of size 3
    }

    @Test
    void testConjugacyClassesS4() {
        // S_4 has 5 conjugacy classes
        SymmetricGroup s4 = GroupFactory.symmetric(4);
        List<ConjugacyClass<Permutation>> classes = ConjugacyAnalyzer.conjugacyClasses(s4);

        assertEquals(5, classes.size());
    }

    @Test
    void testClassEquationS3() {
        // Verify |G| = sum of class sizes
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        assertTrue(ConjugacyAnalyzer.verifyClassEquation(s3));

        Map<Integer, Long> equation = ConjugacyAnalyzer.classEquation(s3);
        int total = equation.entrySet().stream()
                .mapToInt(e -> e.getKey() * e.getValue().intValue())
                .sum();
        assertEquals(6, total);
    }

    @Test
    void testClassEquationDihedral() {
        DihedralGroup d3 = GroupFactory.dihedral(3);
        assertTrue(ConjugacyAnalyzer.verifyClassEquation(d3));

        DihedralGroup d4 = GroupFactory.dihedral(4);
        assertTrue(ConjugacyAnalyzer.verifyClassEquation(d4));
    }

    @Test
    void testAreConjugateAbelian() {
        // In abelian groups, only equal elements are conjugate
        CyclicGroup z6 = GroupFactory.cyclic(6);
        CyclicElement e1 = new CyclicElement(2, 6);
        CyclicElement e2 = new CyclicElement(3, 6);
        CyclicElement e3 = new CyclicElement(2, 6);

        assertFalse(ConjugacyAnalyzer.areConjugate(z6, e1, e2));
        assertTrue(ConjugacyAnalyzer.areConjugate(z6, e1, e3));
    }

    @Test
    void testConjugateElement() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Permutation g = new Permutation(new int[] { 2, 1, 3 }); // (1 2)
        Permutation x = new Permutation(new int[] { 2, 3, 1 }); // (1 2 3)

        Permutation conjugate = ConjugacyAnalyzer.conjugate(s3, g, x);

        // Verify it's in the same conjugacy class
        assertTrue(ConjugacyAnalyzer.areConjugate(s3, g, conjugate));
    }

    @Test
    void testElementCentralizer() {
        // Centralizer times conjugacy class size = group order
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Permutation g = new Permutation(new int[] { 2, 1, 3 }); // (1 2)

        Subgroup<Permutation> centralizer = ConjugacyAnalyzer.elementCentralizer(s3, g);
        Set<Permutation> conjugacyClass = ConjugacyAnalyzer.conjugacyClass(s3, g);

        assertEquals(6, centralizer.order() * conjugacyClass.size());
    }

    @Test
    void testCenterAsUnionOfSingletonClasses() {
        // Elements in center form singleton conjugacy classes
        DihedralGroup d4 = GroupFactory.dihedral(4);
        Subgroup<DihedralElement> center = SubgroupGenerator.center(d4);
        List<ConjugacyClass<DihedralElement>> classes = ConjugacyAnalyzer.conjugacyClasses(d4);

        long singletonClasses = classes.stream().filter(c -> c.size() == 1).count();
        assertEquals(center.order(), singletonClasses);
    }

    @Test
    void testNumberOfConjugacyClasses() {
        assertEquals(6, ConjugacyAnalyzer.numberOfConjugacyClasses(GroupFactory.cyclic(6)));
        assertEquals(3, ConjugacyAnalyzer.numberOfConjugacyClasses(GroupFactory.symmetric(3)));
        assertEquals(5, ConjugacyAnalyzer.numberOfConjugacyClasses(GroupFactory.symmetric(4)));
    }

    @Test
    void testConjugacyClassContains() {
        CyclicGroup z6 = GroupFactory.cyclic(6);
        CyclicElement e = new CyclicElement(3, 6);
        Set<CyclicElement> classElements = ConjugacyAnalyzer.conjugacyClass(z6, e);
        ConjugacyClass<CyclicElement> cl = new ConjugacyClass<>(z6, e, classElements);

        assertTrue(cl.contains(e));
        assertFalse(cl.contains(new CyclicElement(2, 6)));
    }

    @Test
    void testConjugacyClassToString() {
        CyclicGroup z6 = GroupFactory.cyclic(6);
        CyclicElement e = new CyclicElement(0, 6);
        Set<CyclicElement> classElements = ConjugacyAnalyzer.conjugacyClass(z6, e);
        ConjugacyClass<CyclicElement> cl = new ConjugacyClass<>(z6, e, classElements);

        String str = cl.toString();
        assertTrue(str.contains("cl("));
    }
}
