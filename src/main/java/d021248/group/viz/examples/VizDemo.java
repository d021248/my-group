package d021248.group.viz.examples;

import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.symmetric.SymmetricGroup;
import d021248.group.viz.CayleyGraphViewer;
import d021248.group.viz.CayleyTableViewer;
import d021248.group.viz.SubgroupLatticeViewer;

/**
 * Demo application showing all three visualization types.
 * <p>
 * Launches interactive windows for Cayley table, subgroup lattice,
 * and Cayley graph visualizations.
 * </p>
 */
public final class VizDemo {

    public static void main(String[] args) {
        // Parse command line arguments
        String groupType = args.length > 0 ? args[0] : "S4";
        int param = args.length > 1 ? Integer.parseInt(args[1]) : 4;

        // Create group based on type
        var group = switch (groupType.toUpperCase()) {
            case "Z", "CYCLIC" -> {
                CyclicGroup g = GroupFactory.cyclic(param);
                System.out.println("Visualizing Z_" + param);
                yield g;
            }
            case "D", "DIHEDRAL" -> {
                DihedralGroup g = GroupFactory.dihedral(param);
                System.out.println("Visualizing D_" + param);
                yield g;
            }
            case "S", "SYMMETRIC" -> {
                SymmetricGroup g = GroupFactory.symmetric(param);
                System.out.println("Visualizing S_" + param);
                yield g;
            }
            case "A", "ALTERNATING" -> {
                var g = GroupFactory.alternating(param);
                System.out.println("Visualizing A_" + param);
                yield g;
            }
            default -> {
                System.out.println("Unknown group type. Using S_4");
                System.out.println("Usage: java VizDemo <type> <param>");
                System.out.println("  Types: Z/Cyclic, D/Dihedral, S/Symmetric, A/Alternating");
                yield GroupFactory.symmetric(4);
            }
        };

        // Launch visualizations
        System.out.println("Launching visualizations...");
        System.out.println("- Cayley Table (interactive operation table)");
        System.out.println("- Subgroup Lattice (Hasse diagram)");
        System.out.println("- Cayley Graph (generator visualization)");

        CayleyTableViewer.show(group, "Cayley Table - " + groupType.toUpperCase() + "_" + param);

        try {
            Thread.sleep(300); // Stagger window creation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        SubgroupLatticeViewer.show(group, "Subgroup Lattice - " + groupType.toUpperCase() + "_" + param);

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        CayleyGraphViewer.show(group, "Cayley Graph - " + groupType.toUpperCase() + "_" + param);
    }

    private VizDemo() {
    }
}
