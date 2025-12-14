package d021248.group.demo.archive;

import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralElement;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupGenerator;
import d021248.group.symmetric.SymmetricGroup;
import d021248.group.util.Constants;

/**
 * Demonstrates maximal subgroups and Frattini subgroup computation.
 */
public final class FrattiniDemo {
    private static final String MAXIMAL_PREFIX = "  Maximal subgroups: ";
    private static final String ORDER_PREFIX = "    Order ";

    private FrattiniDemo() {
    }

    public static void main(String[] args) {
        System.out.println(Constants.SEPARATOR_60);
        System.out.println("Maximal Subgroups and Frattini Subgroup Demo");
        System.out.println(Constants.SEPARATOR_60);

        demonstrateCyclicGroups();
        demonstrateSymmetricGroup();
        demonstrateDihedralGroup();
        demonstratePGroups();
    }

    private static void demonstrateCyclicGroups() {
        System.out.println("\n--- Cyclic Groups ---");

        CyclicGroup z6 = GroupFactory.cyclic(6);
        System.out.println("\nZ_6 (order 6):");
        var maxZ6 = SubgroupGenerator.maximalSubgroups(z6);
        System.out.println(MAXIMAL_PREFIX + maxZ6.size());
        for (Subgroup<CyclicElement> m : maxZ6) {
            System.out.println(ORDER_PREFIX + m.order() + ": " + m.elements());
        }
        var frattiniZ6 = SubgroupGenerator.frattiniSubgroup(z6);
        System.out.println("  Φ(Z_6) order: " + frattiniZ6.order() + " - " + frattiniZ6.elements());

        CyclicGroup z12 = GroupFactory.cyclic(12);
        System.out.println("\nZ_12 (order 12):");
        var maxZ12 = SubgroupGenerator.maximalSubgroups(z12);
        System.out.println(MAXIMAL_PREFIX + maxZ12.size());
        for (Subgroup<CyclicElement> m : maxZ12) {
            System.out.println(ORDER_PREFIX + m.order() + ": generators=" + getGeneratorValue(m));
        }
        var frattiniZ12 = SubgroupGenerator.frattiniSubgroup(z12);
        System.out.println("  Φ(Z_12) order: " + frattiniZ12.order() + " (trivial)");
    }

    private static void demonstrateSymmetricGroup() {
        System.out.println("\n--- Symmetric Group ---");

        SymmetricGroup s3 = GroupFactory.symmetric(3);
        System.out.println("\nS_3 (order 6):");
        var maxS3 = SubgroupGenerator.maximalSubgroups(s3);
        System.out.println(MAXIMAL_PREFIX + maxS3.size());

        long order2 = maxS3.stream().filter(s -> s.order() == 2).count();
        long order3 = maxS3.stream().filter(s -> s.order() == 3).count();
        System.out.println("    " + order2 + " of order 2 (Sylow 2-subgroups)");
        System.out.println("    " + order3 + " of order 3 (A_3, the alternating group)");

        var frattiniS3 = SubgroupGenerator.frattiniSubgroup(s3);
        System.out.println("  Φ(S_3) order: " + frattiniS3.order() + " (trivial - not a p-group)");
    }

    private static void demonstrateDihedralGroup() {
        System.out.println("\n--- Dihedral Group ---");

        DihedralGroup d4 = GroupFactory.dihedral(4);
        System.out.println("\nD_4 (order 8, a 2-group):");
        var maxD4 = SubgroupGenerator.maximalSubgroups(d4);
        System.out.println(MAXIMAL_PREFIX + maxD4.size());
        for (Subgroup<DihedralElement> m : maxD4) {
            System.out.println(ORDER_PREFIX + m.order());
        }
        var frattiniD4 = SubgroupGenerator.frattiniSubgroup(d4);
        System.out.println("  Φ(D_4) order: " + frattiniD4.order() + " (non-trivial, as expected for 2-groups)");
    }

    private static void demonstratePGroups() {
        System.out.println("\n--- p-Groups (Powers of Prime) ---");

        CyclicGroup z8 = GroupFactory.cyclic(8);
        System.out.println("\nZ_8 (order 2³):");
        var frattiniZ8 = SubgroupGenerator.frattiniSubgroup(z8);
        System.out.println("  Φ(Z_8) order: " + frattiniZ8.order());
        System.out.println("  Contains: " + frattiniZ8.elements());
        System.out.println("  (For cyclic p-groups, Φ(G) consists of elements whose order divides p^(k-1))");

        CyclicGroup z9 = GroupFactory.cyclic(9);
        System.out.println("\nZ_9 (order 3²):");
        var frattiniZ9 = SubgroupGenerator.frattiniSubgroup(z9);
        System.out.println("  Φ(Z_9) order: " + frattiniZ9.order());
        System.out.println("  Contains: " + frattiniZ9.elements());
    }

    private static String getGeneratorValue(Subgroup<CyclicElement> subgroup) {
        if (subgroup.order() == 1) {
            return "{0}";
        }
        // Find a non-identity element to represent the generator
        for (CyclicElement e : subgroup.elements()) {
            if (e.value() != 0) {
                return "<" + e.value() + ">";
            }
        }
        return "?";
    }
}
