package d021248.group.automorphism;

import java.util.Set;

import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.homomorphism.GroupHomomorphism;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Demonstrates automorphism functionality including inner automorphisms,
 * composition, and the relationship between Inn(G) and G/Z(G).
 */
public final class AutomorphismDemo {

    private AutomorphismDemo() {
    }

    public static void main(String[] args) {
        demonstrateInnerAutomorphisms();
        System.out.println();
        demonstrateComposition();
        System.out.println();
        demonstrateInnGroupStructure();
        System.out.println();
        demonstrateOuterAutomorphism();
    }

    /**
     * Shows inner automorphisms via conjugation.
     */
    private static void demonstrateInnerAutomorphisms() {
        System.out.println("=== Inner Automorphisms (Conjugation) ===");

        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Permutation g = new Permutation(new int[] { 2, 1, 3 }); // (1 2)
        Permutation h = new Permutation(new int[] { 2, 3, 1 }); // (1 2 3)

        System.out.println("Group: S_3");
        System.out.println("Element g = (1 2): " + g);
        System.out.println("Element h = (1 2 3): " + h);
        System.out.println();

        // Inner automorphism by conjugation
        Automorphism<Permutation> inner = AutomorphismAnalyzer.innerAutomorphism(s3, g);
        Permutation conjugate = inner.apply(h);

        System.out.println("Conjugation Inn_g(h) = g·h·g⁻¹:");
        System.out.println("  " + g + " · " + h + " · " + g + " = " + conjugate);
        System.out.println("  Expected: (1 3 2) = " + new Permutation(new int[] { 3, 1, 2 }));
        System.out.println();

        // Verify it's a valid automorphism
        System.out.println("Is valid automorphism: " +
                AutomorphismAnalyzer.isAutomorphism(inner));
        System.out.println("Is inner automorphism: " +
                AutomorphismAnalyzer.isInner(inner));
    }

    /**
     * Shows composition of automorphisms.
     */
    private static void demonstrateComposition() {
        System.out.println("=== Composition of Automorphisms ===");

        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Permutation g1 = new Permutation(new int[] { 2, 1, 3 }); // (1 2)
        Permutation g2 = new Permutation(new int[] { 2, 3, 1 }); // (1 2 3)

        Automorphism<Permutation> auto1 = AutomorphismAnalyzer.innerAutomorphism(s3, g1);
        Automorphism<Permutation> auto2 = AutomorphismAnalyzer.innerAutomorphism(s3, g2);

        // Compose: (φ ∘ ψ)(h) = φ(ψ(h))
        Automorphism<Permutation> composed = AutomorphismAnalyzer.compose(auto1, auto2);

        Permutation test = new Permutation(new int[] { 1, 3, 2 }); // (2 3)
        System.out.println("Test element: " + test);
        System.out.println("Auto1(test): " + auto1.apply(test));
        System.out.println("Auto2(test): " + auto2.apply(test));
        System.out.println("(Auto1 ∘ Auto2)(test): " + composed.apply(test));
        System.out.println();

        // Verify inverse
        Automorphism<Permutation> inv = AutomorphismAnalyzer.inverse(auto1);
        Automorphism<Permutation> identity = AutomorphismAnalyzer.compose(auto1, inv);

        System.out.println("Composition with inverse:");
        for (Permutation p : s3.elements()) {
            Permutation result = identity.apply(p);
            boolean isIdentity = p.equals(result);
            if (!isIdentity) {
                System.out.println("  " + p + " → " + result + " (NOT identity!)");
            }
        }
        System.out.println("Auto1 ∘ Auto1⁻¹ = identity automorphism ✓");
    }

    /**
     * Shows the structure of Inn(G) and its size |G|/|Z(G)|.
     */
    private static void demonstrateInnGroupStructure() {
        System.out.println("=== Inn(G) ≅ G/Z(G) ===");

        // S_3 (non-abelian)
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        int s3Size = s3.elements().size();
        int s3InnerCount = AutomorphismAnalyzer.countDistinctInnerAutomorphisms(s3);

        System.out.println("S_3:");
        System.out.println("  |S_3| = " + s3Size);
        System.out.println("  |Z(S_3)| = 1 (trivial center)");
        System.out.println("  |Inn(S_3)| = |S_3|/|Z(S_3)| = " + s3Size + "/1 = " + s3InnerCount);
        System.out.println("  Distinct inner automorphisms: " + s3InnerCount);
        System.out.println("  Is complete (Z(G) = {e}): " +
                AutomorphismAnalyzer.isComplete(s3));
        System.out.println();

        // Z_6 (abelian)
        CyclicGroup z6 = GroupFactory.cyclic(6);
        int z6Size = z6.elements().size();
        int z6InnerCount = AutomorphismAnalyzer.countDistinctInnerAutomorphisms(z6);

        System.out.println("Z_6 (abelian):");
        System.out.println("  |Z_6| = " + z6Size);
        System.out.println("  |Z(Z_6)| = 6 (entire group is center)");
        System.out.println("  |Inn(Z_6)| = |Z_6|/|Z(Z_6)| = " + z6Size + "/" + z6Size + " = " + z6InnerCount);
        System.out.println("  Distinct inner automorphisms: " + z6InnerCount + " (only identity)");
        System.out.println("  Is complete (Z(G) = {e}): " +
                AutomorphismAnalyzer.isComplete(z6));
    }

    /**
     * Shows an outer automorphism (not inner).
     */
    private static void demonstrateOuterAutomorphism() {
        System.out.println("=== Outer Automorphism ===");

        CyclicGroup z4 = GroupFactory.cyclic(4);

        // φ(n) = 3n (mod 4) - this is an automorphism of Z_4
        GroupHomomorphism<CyclicElement, CyclicElement> phi = e -> new CyclicElement((e.value() * 3) % 4, 4);

        Automorphism<CyclicElement> auto = new Automorphism<>(z4, phi);

        System.out.println("Z_4 automorphism φ(n) = 3n (mod 4):");
        for (CyclicElement e : z4.elements()) {
            System.out.println("  φ(" + e.value() + ") = " + auto.apply(e).value());
        }
        System.out.println();

        boolean isAuto = AutomorphismAnalyzer.isAutomorphism(auto);
        boolean isInner = AutomorphismAnalyzer.isInner(auto);

        System.out.println("Is valid automorphism: " + isAuto);
        System.out.println("Is inner automorphism: " + isInner);
        System.out.println();

        if (!isInner) {
            System.out.println("This is an OUTER automorphism!");
            System.out.println("(Z_4 is abelian, so only inner auto is identity)");
        }

        System.out.println();
        System.out.println("All inner automorphisms of Z_4:");
        Set<Automorphism<CyclicElement>> innerAutos = AutomorphismAnalyzer.innerAutomorphisms(z4);
        Automorphism<CyclicElement> id = AutomorphismAnalyzer.identity(z4);

        boolean allIdentity = true;
        for (Automorphism<CyclicElement> inner : innerAutos) {
            if (!AutomorphismAnalyzer.areEqual(inner, id)) {
                allIdentity = false;
                break;
            }
        }

        System.out.println("  Count: " + innerAutos.size());
        System.out.println("  All equal to identity: " + allIdentity);
        System.out.println("  (Expected for abelian groups)");
    }
}
