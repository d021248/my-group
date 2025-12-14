package d021248.group.demo;

import java.util.Set;

import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.quotient.Coset;
import d021248.group.quotient.QuotientGroup;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupGenerator;
import d021248.group.symmetric.AlternatingGroup;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Demonstrates quotient groups G/H where H is a normal subgroup.
 */
public final class QuotientGroupDemo {
    private static final String ORDER_PREFIX = "  Order: ";
    private static final String ELEMENTS_PREFIX = "  Elements: ";

    public static void main(String[] args) {
        demonstrateCyclicQuotients();
        System.out.println();
        demonstrateSymmetricQuotient();
        System.out.println();
        demonstrateFirstIsomorphismTheorem();
    }

    private static void demonstrateCyclicQuotients() {
        System.out.println("=== Cyclic Group Quotients ===");

        // Z_6 / <3> where <3> = {0, 3}
        CyclicGroup z6 = new CyclicGroup(6);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(3, 6)));
        QuotientGroup<CyclicElement> z6modH = new QuotientGroup<>(z6, h);

        System.out.println("Z_6 / <3>:");
        System.out.println(ORDER_PREFIX + z6modH.order() + " (= 6/2)");
        System.out.println(ELEMENTS_PREFIX);
        for (Coset<CyclicElement> coset : z6modH.elements()) {
            System.out.println("    " + coset + " = " + coset.elements());
        }

        // Verify operation: 1H + 1H = 2H
        Coset<CyclicElement> coset1 = new Coset<>(z6, h, new CyclicElement(1, 6));
        Coset<CyclicElement> product = z6modH.operate(coset1, coset1);
        System.out.println("\n  Operation: 1H + 1H = " + product);

        // Z_12 / <4> where <4> = {0, 4, 8}
        System.out.println("\nZ_12 / <4>:");
        CyclicGroup z12 = new CyclicGroup(12);
        Subgroup<CyclicElement> h4 = SubgroupGenerator.generate(z12, Set.of(new CyclicElement(4, 12)));
        QuotientGroup<CyclicElement> z12modH4 = new QuotientGroup<>(z12, h4);
        System.out.println(ORDER_PREFIX + z12modH4.order() + " (= 12/3)");
        System.out.println(ELEMENTS_PREFIX);
        for (Coset<CyclicElement> coset : z12modH4.elements()) {
            System.out.println("    " + coset);
        }
    }

    private static void demonstrateSymmetricQuotient() {
        System.out.println("=== S_3 / A_3 ===");

        // S_3 / A_3 ≅ Z_2 (quotient by alternating group)
        SymmetricGroup s3 = new SymmetricGroup(3);
        AlternatingGroup a3Impl = new AlternatingGroup(3);
        Subgroup<Permutation> a3 = new Subgroup<>(s3, a3Impl.elements());

        System.out.println("S_3 order: " + s3.order());
        System.out.println("A_3 order: " + a3.order());
        System.out.println("A_3 is normal: " + SubgroupGenerator.isNormal(s3, a3));

        QuotientGroup<Permutation> quotient = new QuotientGroup<>(s3, a3);
        System.out.println("\nS_3 / A_3:");
        System.out.println(ORDER_PREFIX + quotient.order() + " (= 6/3)");
        System.out.println(ELEMENTS_PREFIX);

        for (Coset<Permutation> coset : quotient.elements()) {
            System.out.println("    " + coset + " (" + coset.elements().size() + " permutations)");
            System.out.print("      Contains: ");
            for (Permutation p : coset.elements()) {
                System.out.print(p + " ");
            }
            System.out.println();
        }
    }

    private static void demonstrateFirstIsomorphismTheorem() {
        System.out.println("=== First Isomorphism Theorem Example ===");

        // For the sign homomorphism φ: S_3 → Z_2
        // ker(φ) = A_3 and S_3 / A_3 ≅ Z_2
        System.out.println("Sign homomorphism φ: S_3 → {-1, +1}");
        System.out.println("  ker(φ) = A_3 (even permutations)");
        System.out.println("  im(φ) = {-1, +1} ≅ Z_2");
        System.out.println("  By First Isomorphism Theorem: S_3 / A_3 ≅ Z_2");

        SymmetricGroup s3 = new SymmetricGroup(3);
        AlternatingGroup a3Impl = new AlternatingGroup(3);
        Subgroup<Permutation> kernel = new Subgroup<>(s3, a3Impl.elements());
        QuotientGroup<Permutation> quotient = new QuotientGroup<>(s3, kernel);

        System.out.println("\n  Verified: |S_3 / A_3| = " + quotient.order());

        // Show the two cosets correspond to even and odd permutations
        System.out.println("\n  The two cosets:");
        for (Coset<Permutation> coset : quotient.elements()) {
            Permutation rep = coset.representative();
            int sign = rep.sign();
            System.out.println("    " + coset + ": sign = " + sign);
        }
    }
}
