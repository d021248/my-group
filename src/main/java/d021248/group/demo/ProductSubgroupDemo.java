package d021248.group.demo;

import java.util.List;
import java.util.Set;

import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralElement;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.product.DirectProduct;
import d021248.group.product.ProductElement;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupGenerator;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Demonstration of direct product and subgroup features.
 */
public class ProductSubgroupDemo {
    private static final String ORDER_PREFIX = "  Order: ";
    private static final String ELEMENTS_PREFIX = "  Elements: ";

    public static void main(String[] args) {
        demonstrateDirectProducts();
        System.out.println();
        demonstrateSubgroups();
        System.out.println();
        demonstrateSubgroupTheory();
    }

    private static void demonstrateDirectProducts() {
        System.out.println("=== Direct Products ===");

        // 1. Klein four-group: Z_2 × Z_2
        CyclicGroup z2a = GroupFactory.cyclic(2);
        CyclicGroup z2b = GroupFactory.cyclic(2);
        DirectProduct<CyclicElement, CyclicElement> v4 = new DirectProduct<>(z2a, z2b);
        System.out.println("Klein four-group V_4 = Z_2 × Z_2:");
        System.out.println(ORDER_PREFIX + v4.order());
        System.out.println(ELEMENTS_PREFIX + v4.elements());

        // 2. Z_3 × Z_4 (order 12, cyclic)
        var z3xz4 = GroupFactory.directProduct(GroupFactory.cyclic(3), GroupFactory.cyclic(4));
        System.out.println("\nZ_3 × Z_4:");
        System.out.println(ORDER_PREFIX + z3xz4.order());
        ProductElement<CyclicElement, CyclicElement> gen = new ProductElement<>(new CyclicElement(1, 3),
                new CyclicElement(1, 4));
        System.out.println("  Generator: " + gen);

        // 3. D_3 × Z_2 (dihedral × cyclic)
        DihedralGroup d3 = GroupFactory.dihedral(3);
        CyclicGroup z2 = GroupFactory.cyclic(2);
        DirectProduct<DihedralElement, CyclicElement> d3xz2 = new DirectProduct<>(d3, z2);
        System.out.println("\nD_3 × Z_2:");
        System.out.println(ORDER_PREFIX + d3xz2.order() + " (6 × 2)");
        System.out.println("  Identity: " + d3xz2.identity());
    }

    private static void demonstrateSubgroups() {
        System.out.println("=== Subgroup Generation ===");

        // 1. Generate subgroup from generators
        CyclicGroup z12 = GroupFactory.cyclic(12);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z12, Set.of(new CyclicElement(3, 12)));
        System.out.println("Subgroup <3> of Z_12:");
        System.out.println(ELEMENTS_PREFIX + h.elements());
        System.out.println(ORDER_PREFIX + h.order());
        System.out.println("  Index: " + h.index() + " (12 / " + h.order() + ")");

        // 2. All subgroups of small group
        CyclicGroup z6 = GroupFactory.cyclic(6);
        List<Subgroup<CyclicElement>> allSubs = SubgroupGenerator.allSubgroups(z6);
        System.out.println("  All subgroups:");
        for (Subgroup<CyclicElement> sub : allSubs) {
            System.out.println("    Order " + sub.order() + ": " + sub.elements());
        }

        // 3. Cyclic subgroups
        System.out.println("\nCyclic subgroups of Z_12 (by order):");
        List<Subgroup<CyclicElement>> cyclicSubs = SubgroupGenerator.cyclicSubgroups(z12);
        cyclicSubs.stream().sorted((a, b) -> Integer.compare(a.order(), b.order()))
                .forEach(s -> System.out.println(
                        "    Order " + s.order() + ": " + s.elements().size() + " elements"));
    }

    private static void demonstrateSubgroupTheory() {
        System.out.println("=== Subgroup Theory ===");

        // 1. Normal subgroups in abelian groups
        CyclicGroup z8 = GroupFactory.cyclic(8);
        Subgroup<CyclicElement> h = SubgroupGenerator.generate(z8, Set.of(new CyclicElement(2, 8)));
        System.out.println("Subgroup <2> of Z_8:");
        System.out.println("  Is normal? " + SubgroupGenerator.isNormal(z8, h));
        System.out.println("  (All subgroups of abelian groups are normal)");

        // 2. Non-normal subgroup in S_3
        SymmetricGroup s3 = GroupFactory.symmetric(3);
        List<Subgroup<Permutation>> s3Subs = SubgroupGenerator.allSubgroups(s3);
        System.out.println("\nSubgroups of S_3:");
        for (var sub : s3Subs) {
            boolean normal = SubgroupGenerator.isNormal(s3, sub);
            System.out.println("    Order " + sub.order() + ": normal=" + normal);
        }

        // 3. Normalizer and centralizer
        CyclicGroup z6 = GroupFactory.cyclic(6);
        Subgroup<CyclicElement> k = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(2, 6)));
        Subgroup<CyclicElement> normalizer = SubgroupGenerator.normalizer(z6, k);
        Subgroup<CyclicElement> centralizer = SubgroupGenerator.centralizer(z6, k);
        System.out.println("\nFor subgroup <2> of Z_6:");
        System.out.println("  Normalizer order: " + normalizer.order());
        System.out.println("  Centralizer order: " + centralizer.order());
        System.out.println("  (In abelian groups: N(H) = C(H) = G)");

        // 4. Subgroup lattice structure
        System.out.println("\nSubgroup lattice of Z_12 (divisor structure):");
        CyclicGroup z12 = GroupFactory.cyclic(12);
        List<Subgroup<CyclicElement>> z12Subs = SubgroupGenerator.cyclicSubgroups(z12);
        z12Subs.stream().sorted((a, b) -> Integer.compare(a.order(), b.order()))
                .forEach(s -> System.out.println("  " + s.order() + " divides 12"));
    }

    private ProductSubgroupDemo() {
    }
}
