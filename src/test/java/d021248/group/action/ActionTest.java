package d021248.group.action;

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
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Tests for group actions.
 */
class ActionTest {

    @Test
    void testConjugationActionAbelian() {
        CyclicGroup z4 = GroupFactory.cyclic(4);

        Action<CyclicElement, CyclicElement> conjugation = new Action<>(
                z4,
                z4.elements(),
                (g, h) -> {
                    CyclicElement gInv = z4.inverse(g);
                    return z4.operate(z4.operate(g, h), gInv);
                });

        assertTrue(ActionAnalyzer.isAction(conjugation));

        List<Orbit<CyclicElement>> orbits = ActionAnalyzer.orbits(conjugation);
        assertEquals(4, orbits.size());

        for (CyclicElement x : z4.elements()) {
            Orbit<CyclicElement> orb = ActionAnalyzer.orbit(conjugation, x);
            assertEquals(1, orb.size());
        }
    }

    @Test
    void testConjugationActionNonAbelian() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);

        Action<Permutation, Permutation> conjugation = new Action<>(
                s3,
                s3.elements(),
                (g, h) -> {
                    Permutation gInv = s3.inverse(g);
                    return s3.operate(s3.operate(g, h), gInv);
                });

        assertTrue(ActionAnalyzer.isAction(conjugation));

        List<Orbit<Permutation>> orbits = ActionAnalyzer.orbits(conjugation);
        assertEquals(3, orbits.size());
    }

    @Test
    void testOrbitStabilizerTheorem() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);

        Action<Permutation, Permutation> conjugation = new Action<>(
                s3,
                s3.elements(),
                (g, h) -> {
                    Permutation gInv = s3.inverse(g);
                    return s3.operate(s3.operate(g, h), gInv);
                });

        for (Permutation x : s3.elements()) {
            assertTrue(ActionAnalyzer.verifyOrbitStabilizer(conjugation, x));
        }
    }

    @Test
    void testPermutationActionOnIntegers() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Set<Integer> integers = Set.of(1, 2, 3);

        Action<Permutation, Integer> permAction = new Action<>(
                s3,
                integers,
                (p, i) -> p.mapping()[i - 1]);

        assertTrue(ActionAnalyzer.isAction(permAction));
        assertTrue(ActionAnalyzer.isTransitive(permAction));

        List<Orbit<Integer>> orbits = ActionAnalyzer.orbits(permAction);
        assertEquals(1, orbits.size());
        assertEquals(3, orbits.get(0).size());
    }

    @Test
    void testStabilizerSubgroup() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Set<Integer> integers = Set.of(1, 2, 3);

        Action<Permutation, Integer> permAction = new Action<>(
                s3,
                integers,
                (p, i) -> p.mapping()[i - 1]);

        Subgroup<Permutation> stab1 = ActionAnalyzer.stabilizer(permAction, 1);
        assertEquals(2, stab1.order());

        Subgroup<Permutation> stab2 = ActionAnalyzer.stabilizer(permAction, 2);
        assertEquals(2, stab2.order());
    }

    @Test
    void testTrivialActionIsFree() {
        CyclicGroup z5 = GroupFactory.cyclic(5);

        Action<CyclicElement, CyclicElement> trivial = new Action<>(
                z5,
                z5.elements(),
                (g, x) -> x);

        assertTrue(ActionAnalyzer.isAction(trivial));
        assertFalse(ActionAnalyzer.isFree(trivial));

        for (CyclicElement x : z5.elements()) {
            Subgroup<CyclicElement> stab = ActionAnalyzer.stabilizer(trivial, x);
            assertEquals(5, stab.order());
        }
    }

    @Test
    void testLeftMultiplicationAction() {
        CyclicGroup z6 = GroupFactory.cyclic(6);

        Action<CyclicElement, CyclicElement> leftMult = new Action<>(
                z6,
                z6.elements(),
                (g, x) -> z6.operate(g, x));

        assertTrue(ActionAnalyzer.isAction(leftMult));
        assertTrue(ActionAnalyzer.isTransitive(leftMult));
        assertTrue(ActionAnalyzer.isFree(leftMult));
    }

    @Test
    void testFixedPoints() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Set<Integer> integers = Set.of(1, 2, 3);

        Action<Permutation, Integer> permAction = new Action<>(
                s3,
                integers,
                (p, i) -> p.mapping()[i - 1]);

        Permutation identity = new Permutation(new int[] { 1, 2, 3 });
        Set<Integer> fixedByIdentity = ActionAnalyzer.fixedPoints(permAction, identity);
        assertEquals(3, fixedByIdentity.size());

        Permutation transposition = new Permutation(new int[] { 2, 1, 3 });
        Set<Integer> fixedByTransposition = ActionAnalyzer.fixedPoints(permAction, transposition);
        assertEquals(1, fixedByTransposition.size());
        assertTrue(fixedByTransposition.contains(3));
    }

    @Test
    void testBurnsideLemma() {
        SymmetricGroup s3 = GroupFactory.symmetric(3);

        Action<Permutation, Permutation> conjugation = new Action<>(
                s3,
                s3.elements(),
                (g, h) -> {
                    Permutation gInv = s3.inverse(g);
                    return s3.operate(s3.operate(g, h), gInv);
                });

        int numOrbits = ActionAnalyzer.burnsideLemma(conjugation);
        assertEquals(3, numOrbits);

        List<Orbit<Permutation>> orbits = ActionAnalyzer.orbits(conjugation);
        assertEquals(numOrbits, orbits.size());
    }

    @Test
    void testFixedPointCounts() {
        DihedralGroup d3 = GroupFactory.dihedral(3);

        Action<DihedralElement, DihedralElement> conjugation = new Action<>(
                d3,
                d3.elements(),
                (g, h) -> {
                    DihedralElement gInv = d3.inverse(g);
                    return d3.operate(d3.operate(g, h), gInv);
                });

        Map<DihedralElement, Integer> counts = ActionAnalyzer.fixedPointCounts(conjugation);

        DihedralElement identity = d3.identity();
        assertEquals(6, counts.get(identity));
    }

    @Test
    void testOrbitContains() {
        CyclicGroup z4 = GroupFactory.cyclic(4);

        Action<CyclicElement, CyclicElement> leftMult = new Action<>(
                z4,
                z4.elements(),
                (g, x) -> z4.operate(g, x));

        CyclicElement e = new CyclicElement(0, 4);
        Orbit<CyclicElement> orb = ActionAnalyzer.orbit(leftMult, e);

        assertTrue(orb.contains(new CyclicElement(0, 4)));
        assertTrue(orb.contains(new CyclicElement(1, 4)));
        assertTrue(orb.contains(new CyclicElement(2, 4)));
        assertTrue(orb.contains(new CyclicElement(3, 4)));
    }
}
