package d021248.group.demo.archive;

import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.homomorphism.Homomorphism;
import d021248.group.homomorphism.HomomorphismAnalyzer;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupGenerator;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Demonstrates group homomorphisms and the First Isomorphism Theorem.
 */
public final class HomomorphismDemo {
    private static final String SIGN_PREFIX = "  sign(";
    private static final String ORDER_PREFIX = "  Order: ";
    private static final String ELEMENTS_PREFIX = "  Elements: ";

    private HomomorphismDemo() {
    }

    public static void main(String[] args) {
        demonstrateSignHomomorphism();
        System.out.println();
        demonstrateFirstIsomorphismTheorem();
        System.out.println();
        demonstrateProjection();
        System.out.println();
        demonstrateComposition();
    }

    private static void demonstrateSignHomomorphism() {
        System.out.println("=== Sign Homomorphism: S_3 → Z_2 ===");
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        CyclicGroup z2 = GroupFactory.cyclic(2);

        Homomorphism<Permutation, CyclicElement> sign = new Homomorphism<>(
                s3, z2,
                p -> new CyclicElement(p.sign() == 1 ? 0 : 1, 2));

        System.out.println("Source: S_3 (order " + s3.order() + ")");
        System.out.println("Target: Z_2 (order " + z2.order() + ")");

        System.out.println("\nMapping examples:");
        Permutation identity = new Permutation(new int[] { 1, 2, 3 });
        Permutation transposition = new Permutation(new int[] { 2, 1, 3 });
        Permutation threeCycle = new Permutation(new int[] { 2, 3, 1 });

        System.out.println(SIGN_PREFIX + identity + ") = " + sign.apply(identity) + " (even)");
        System.out.println(SIGN_PREFIX + transposition + ") = " + sign.apply(transposition) + " (odd)");
        System.out.println(SIGN_PREFIX + threeCycle + ") = " + sign.apply(threeCycle) + " (even)");

        System.out.println("\nProperties:");
        System.out.println("  Is homomorphism: " + HomomorphismAnalyzer.isHomomorphism(sign));
        System.out.println("  Is injective: " + HomomorphismAnalyzer.isInjective(sign));
        System.out.println("  Is surjective: " + HomomorphismAnalyzer.isSurjective(sign));
        System.out.println("  Is isomorphism: " + HomomorphismAnalyzer.isIsomorphism(sign));

        Subgroup<Permutation> kernel = HomomorphismAnalyzer.kernel(sign);
        System.out.println("\nKernel (alternating group A_3):");
        System.out.println(ORDER_PREFIX + kernel.order());
        System.out.println("  Is normal: " + SubgroupGenerator.isNormal(s3, kernel));

        Subgroup<CyclicElement> image = HomomorphismAnalyzer.image(sign);
        System.out.println("\nImage:");
        System.out.println(ORDER_PREFIX + image.order());
        System.out.println(ELEMENTS_PREFIX + image.elements());
    }

    private static void demonstrateFirstIsomorphismTheorem() {
        System.out.println("=== First Isomorphism Theorem: S_3/A_3 ≅ Z_2 ===");
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        CyclicGroup z2 = GroupFactory.cyclic(2);

        Homomorphism<Permutation, CyclicElement> sign = new Homomorphism<>(
                s3, z2,
                p -> new CyclicElement(p.sign() == 1 ? 0 : 1, 2));

        Subgroup<Permutation> kernel = HomomorphismAnalyzer.kernel(sign);
        Subgroup<CyclicElement> image = HomomorphismAnalyzer.image(sign);

        System.out.println("Given: φ: S_3 → Z_2 (sign homomorphism)");
        System.out.println("Kernel: A_3 (order " + kernel.order() + ")");
        System.out.println("Image: Z_2 (order " + image.order() + ")");

        int quotientOrder = s3.order() / kernel.order();
        System.out.println("\nBy Lagrange's Theorem:");
        System.out.println("  |S_3/A_3| = |S_3| / |A_3| = " + s3.order() + " / " + kernel.order() +
                " = " + quotientOrder);

        System.out.println("\nFirst Isomorphism Theorem states:");
        System.out.println("  G/ker(φ) ≅ im(φ)");
        System.out.println("  S_3/A_3 ≅ Z_2");

        int theoremOrder = HomomorphismAnalyzer.firstIsomorphismTheorem(sign);
        System.out.println("\nVerification:");
        System.out.println("  |S_3/A_3| = " + quotientOrder);
        System.out.println("  |im(φ)| = " + image.order());
        System.out.println("  Both equal: " + theoremOrder + " ✓");
    }

    private static void demonstrateProjection() {
        System.out.println("=== Projection: Z_6 → Z_3 ===");
        CyclicGroup z6 = GroupFactory.cyclic(6);
        CyclicGroup z3 = GroupFactory.cyclic(3);

        Homomorphism<CyclicElement, CyclicElement> projection = new Homomorphism<>(
                z6, z3,
                e -> new CyclicElement(e.value() % 3, 3));

        System.out.println("Projection mapping (mod 3):");
        for (CyclicElement e : z6.elements()) {
            System.out.println("  " + e + " ↦ " + projection.apply(e));
        }

        System.out.println("\nProperties:");
        System.out.println("  Is surjective: " + HomomorphismAnalyzer.isSurjective(projection));

        Subgroup<CyclicElement> kernel = HomomorphismAnalyzer.kernel(projection);
        System.out.println("\nKernel: {0, 3} in Z_6");
        System.out.println(ORDER_PREFIX + kernel.order());
        System.out.println(ELEMENTS_PREFIX + kernel.elements());

        int quotientOrder = HomomorphismAnalyzer.firstIsomorphismTheorem(projection);
        System.out.println("\nFirst Isomorphism Theorem: Z_6/<0,3> ≅ Z_3");
        System.out.println("  Both have order: " + quotientOrder);
    }

    private static void demonstrateComposition() {
        System.out.println("=== Composition of Homomorphisms ===");
        CyclicGroup z12 = GroupFactory.cyclic(12);
        CyclicGroup z4 = GroupFactory.cyclic(4);
        CyclicGroup z2 = GroupFactory.cyclic(2);

        Homomorphism<CyclicElement, CyclicElement> phi = new Homomorphism<>(
                z12, z4,
                e -> new CyclicElement(e.value() % 4, 4));

        Homomorphism<CyclicElement, CyclicElement> psi = new Homomorphism<>(
                z4, z2,
                e -> new CyclicElement(e.value() % 2, 2));

        System.out.println("φ: Z_12 → Z_4 (mod 4)");
        System.out.println("ψ: Z_4 → Z_2 (mod 2)");
        System.out.println("(ψ ∘ φ): Z_12 → Z_2");

        Homomorphism<CyclicElement, CyclicElement> composed = HomomorphismAnalyzer.compose(phi, psi);

        System.out.println("\nComposition mapping:");
        for (int i = 0; i < 12; i += 2) {
            CyclicElement e = new CyclicElement(i, 12);
            System.out.println("  " + i + " ↦ " + composed.apply(e));
        }

        System.out.println("\nVerification:");
        System.out.println("  Is homomorphism: " + HomomorphismAnalyzer.isHomomorphism(composed));
        System.out.println("  Source: " + composed.source().order());
        System.out.println("  Target: " + composed.target().order());

        Subgroup<CyclicElement> kernel = HomomorphismAnalyzer.kernel(composed);
        System.out.println("\nKernel of composition:");
        System.out.println(ORDER_PREFIX + kernel.order());
        System.out.println(ELEMENTS_PREFIX + kernel.elements());
    }
}
