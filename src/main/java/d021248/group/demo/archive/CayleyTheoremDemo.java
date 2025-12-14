package d021248.group.demo.archive;

import d021248.group.Group;
import d021248.group.GroupFactory;
import d021248.group.api.Element;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralElement;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.symmetric.CayleyPermutationGroup;
import d021248.group.symmetric.Permutation;
import d021248.group.viz.CayleyGraphViewer;
import d021248.group.viz.CayleyTableViewer;

/**
 * Demonstrates Cayley's Theorem: converting any group to a permutation group.
 * Every group G is isomorphic to a subgroup of S_n via the left regular
 * representation.
 */
public final class CayleyTheoremDemo {
    private static final String ORDER_PREFIX = "Order: ";

    public static void main(String[] args) {
        demonstrateCyclicGroup();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateDihedralGroup();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateVisualization();
    }

    private static void demonstrateCyclicGroup() {
        System.out.println("=== Example 1: Cyclic Group Z_4 ===\n");

        CyclicGroup z4 = GroupFactory.cyclic(4);
        System.out.println("Original group: Z_4 (" + ORDER_PREFIX + z4.order() + ")");
        System.out.println("Elements: " + z4.elements());

        // Convert to permutation group using Cayley's theorem
        CayleyPermutationGroup<CyclicElement> permZ4 = new CayleyPermutationGroup<>(z4);

        System.out.println("\nCayley permutation representation:");
        System.out.println(ORDER_PREFIX + permZ4.order() + " (same as original!)");
        System.out.println("\nElement mapping (g -> L_g where L_g(x) = g*x):");

        var ordered = permZ4.orderedElements();

        for (int i = 0; i < ordered.size(); i++) {
            CyclicElement g = ordered.get(i);
            Permutation perm = findPermFor(permZ4, g);
            System.out.printf("  %s -> %s\n", g, perm);

            // Show what this permutation does
            System.out.print("       (maps: ");
            for (int j = 0; j < ordered.size(); j++) {
                CyclicElement x = ordered.get(j);
                CyclicElement gx = z4.operate(g, x);
                System.out.printf("%s->%s ", x, gx);
            }
            System.out.println(")");
        }

        System.out.println("\nVerification: The permutation group is isomorphic to Z_4!");
    }

    private static void demonstrateDihedralGroup() {
        System.out.println("=== Example 2: Dihedral Group D_3 ===\n");

        DihedralGroup d3 = GroupFactory.dihedral(3);
        System.out.println("Original group: D_3 (symmetries of triangle)");
        System.out.println(ORDER_PREFIX + d3.order());
        System.out.println("Non-abelian: " + !d3.isAbelian());

        // Convert to permutation group
        CayleyPermutationGroup<DihedralElement> permD3 = new CayleyPermutationGroup<>(d3);

        System.out.println("\nCayley permutation representation:");
        System.out.println(ORDER_PREFIX + permD3.order());

        var ordered = permD3.orderedElements();
        System.out.println("\nFirst 3 element mappings:");

        for (int i = 0; i < Math.min(3, ordered.size()); i++) {
            DihedralElement g = ordered.get(i);
            Permutation perm = findPermFor(permD3, g);
            System.out.printf("  %s -> %s\n", g, perm);
        }

        System.out.println("\nNote: D_3 is isomorphic to S_3, so this embeds it into S_6!");
    }

    private static void demonstrateVisualization() {
        System.out.println("=== Visualizing Permutation Groups ===\n");

        System.out.println("You can visualize the converted permutation group:");
        System.out.println("Example code:\n");

        System.out.println("  CyclicGroup z5 = GroupFactory.cyclic(5);");
        System.out.println("  CayleyPermutationGroup<CyclicElement> permZ5 = new CayleyPermutationGroup<>(z5);");
        System.out.println("  ");
        System.out.println("  // Now visualize it!");
        System.out.println("  CayleyTableViewer.show(permZ5, \"Z_5 as Permutations\");");
        System.out.println("  CayleyGraphViewer.show(permZ5, \"Z_5 Permutation Graph\");");

        System.out.println("\nLaunching visualization for Z_4 as permutations...");

        CyclicGroup z4 = GroupFactory.cyclic(4);
        CayleyPermutationGroup<CyclicElement> permZ4 = new CayleyPermutationGroup<>(z4);

        CayleyTableViewer.show(permZ4, "Z_4 as Permutation Group (Cayley's Theorem)");

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        CayleyGraphViewer.show(permZ4, "Z_4 Permutation Graph");
    }

    private static <E extends Element> Permutation findPermFor(
            CayleyPermutationGroup<E> permGroup, E element) {
        var ordered = permGroup.orderedElements();
        var source = permGroup.sourceGroup();

        int n = ordered.size();
        int[] mapping = new int[n];

        for (int i = 0; i < n; i++) {
            E x = ordered.get(i);
            E gx = source.operate(element, x);
            int idx = ordered.indexOf(gx);
            mapping[i] = idx + 1; // 1-based
        }

        return new Permutation(mapping);
    }

    /**
     * Generic converter: any group -> permutation group
     */
    public static <E extends Element> CayleyPermutationGroup<E> toPermutationGroup(Group<E> group) {
        return new CayleyPermutationGroup<>(group);
    }
}
