package d021248.group.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.subgroup.Subgroup;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Demonstrates group actions, orbits, stabilizers, and Burnside's Lemma.
 */
public class ActionDemo {

    public static void main(String[] args) {
        demonstrateConjugationAction();
        System.out.println();
        demonstrateOrbitStabilizer();
        System.out.println();
        demonstratePermutationAction();
        System.out.println();
        demonstrateBurnsideLemma();
    }

    private static void demonstrateConjugationAction() {
        System.out.println("=== Conjugation Action: G acts on itself ===");
        SymmetricGroup s3 = GroupFactory.symmetric(3);

        Action<Permutation, Permutation> conjugation = new Action<>(
                s3,
                s3.elements(),
                (g, h) -> {
                    Permutation gInv = s3.inverse(g);
                    return s3.operate(s3.operate(g, h), gInv);
                });

        System.out.println("Group: S_3 (order " + s3.order() + ")");
        System.out.println("Action: g · h = ghg⁻¹ (conjugation)");

        System.out.println("\nVerifying action axioms:");
        System.out.println("  Is valid action: " + ActionAnalyzer.isAction(conjugation));

        List<Orbit<Permutation>> orbits = ActionAnalyzer.orbits(conjugation);
        System.out.println("\nOrbits (= conjugacy classes):");
        System.out.println("  Number of orbits: " + orbits.size());

        for (int i = 0; i < orbits.size(); i++) {
            Orbit<Permutation> orb = orbits.get(i);
            System.out.println("\n  Orbit " + (i + 1) + " (size " + orb.size() + "):");
            System.out.println("    Representative: " + orb.representative());
            System.out.println("    Elements: " + orb.elements());
        }

        System.out.println("\nAction properties:");
        System.out.println("  Is transitive: " + ActionAnalyzer.isTransitive(conjugation));
        System.out.println("  Is free: " + ActionAnalyzer.isFree(conjugation));
    }

    private static void demonstrateOrbitStabilizer() {
        System.out.println("=== Orbit-Stabilizer Theorem ===");
        SymmetricGroup s3 = GroupFactory.symmetric(3);

        Action<Permutation, Permutation> conjugation = new Action<>(
                s3,
                s3.elements(),
                (g, h) -> {
                    Permutation gInv = s3.inverse(g);
                    return s3.operate(s3.operate(g, h), gInv);
                });

        Permutation transposition = new Permutation(new int[] { 2, 1, 3 });

        System.out.println("Element: " + transposition);

        Orbit<Permutation> orb = ActionAnalyzer.orbit(conjugation, transposition);
        System.out.println("\nOrbit (conjugacy class):");
        System.out.println("  Size: " + orb.size());
        System.out.println("  Elements: " + orb.elements());

        Subgroup<Permutation> stab = ActionAnalyzer.stabilizer(conjugation, transposition);
        System.out.println("\nStabilizer (centralizer):");
        System.out.println("  Order: " + stab.order());
        System.out.println("  Elements: " + stab.elements());

        System.out.println("\nOrbit-Stabilizer Theorem:");
        System.out.println("  |orb(x)| × |Stab(x)| = " + orb.size() + " × " + stab.order() +
                " = " + (orb.size() * stab.order()));
        System.out.println("  |G| = " + s3.order());
        System.out.println("  Theorem holds: " + ActionAnalyzer.verifyOrbitStabilizer(conjugation, transposition));
    }

    private static void demonstratePermutationAction() {
        System.out.println("=== S_3 acts on {1, 2, 3} by permutation ===");
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Set<Integer> integers = Set.of(1, 2, 3);

        Action<Permutation, Integer> permAction = new Action<>(
                s3,
                integers,
                (p, i) -> p.mapping()[i - 1]);

        System.out.println("Group: S_3 (order " + s3.order() + ")");
        System.out.println("Set: {1, 2, 3}");
        System.out.println("Action: σ · i = σ(i)");

        System.out.println("\nProperties:");
        System.out.println("  Is valid action: " + ActionAnalyzer.isAction(permAction));
        System.out.println("  Is transitive: " + ActionAnalyzer.isTransitive(permAction));
        System.out.println("  Is free: " + ActionAnalyzer.isFree(permAction));

        List<Orbit<Integer>> orbits = ActionAnalyzer.orbits(permAction);
        System.out.println("\nOrbits:");
        System.out.println("  Number: " + orbits.size());
        System.out.println("  Single orbit: " + orbits.get(0).elements());

        System.out.println("\nStabilizers:");
        for (int i = 1; i <= 3; i++) {
            Subgroup<Permutation> stab = ActionAnalyzer.stabilizer(permAction, i);
            System.out.println("  Stab(" + i + ") order: " + stab.order());
            System.out.println("    (permutations fixing " + i + ")");
        }

        System.out.println("\nFixed points:");
        Permutation identity = new Permutation(new int[] { 1, 2, 3 });
        Permutation trans12 = new Permutation(new int[] { 2, 1, 3 });
        Permutation cycle123 = new Permutation(new int[] { 2, 3, 1 });

        System.out.println("  Fix(identity) = " + ActionAnalyzer.fixedPoints(permAction, identity));
        System.out.println("  Fix((1 2)) = " + ActionAnalyzer.fixedPoints(permAction, trans12));
        System.out.println("  Fix((1 2 3)) = " + ActionAnalyzer.fixedPoints(permAction, cycle123));
    }

    private static void demonstrateBurnsideLemma() {
        System.out.println("=== Burnside's Lemma ===");
        CyclicGroup z4 = GroupFactory.cyclic(4);

        Action<CyclicElement, CyclicElement> conjugation = new Action<>(
                z4,
                z4.elements(),
                (g, h) -> {
                    CyclicElement gInv = z4.inverse(g);
                    return z4.operate(z4.operate(g, h), gInv);
                });

        System.out.println("Group: Z_4 (abelian, order 4)");
        System.out.println("Action: conjugation (g · h = ghg⁻¹)");

        Map<CyclicElement, Integer> fixedCounts = ActionAnalyzer.fixedPointCounts(conjugation);

        System.out.println("\nFixed point counts:");
        int totalFixed = 0;
        for (Map.Entry<CyclicElement, Integer> entry : fixedCounts.entrySet()) {
            System.out.println("  |Fix(" + entry.getKey() + ")| = " + entry.getValue());
            totalFixed += entry.getValue();
        }

        System.out.println("\nBurnside's Lemma:");
        System.out.println("  |X/G| = (1/|G|) × Σ |Fix(g)|");
        System.out.println("  |X/G| = (1/" + z4.order() + ") × " + totalFixed);
        System.out.println("  |X/G| = " + (totalFixed / z4.order()));

        int numOrbits = ActionAnalyzer.burnsideLemma(conjugation);
        System.out.println("\nNumber of orbits: " + numOrbits);

        List<Orbit<CyclicElement>> orbits = ActionAnalyzer.orbits(conjugation);
        System.out.println("Verification (direct count): " + orbits.size());
        System.out.println("\nNote: Z_4 is abelian, so each element is its own orbit");
        System.out.println("      (conjugacy classes are singletons)");
    }
}
