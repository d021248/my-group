package d021248.group.viz;

import d021248.group.Group;
import d021248.group.GroupFactory;

/**
 * Quick demo to show S_4 visualizations (table and graph only - no lattice due
 * to size).
 */
public class S4Demo {
    public static void main(String[] args) {
        System.out.println("Launching S_4 visualizations...");

        // SymmetricGroup s4 = GroupFactory.symmetric(4);
        Group s4 = GroupFactory.cyclic(61);
        System.out.println("S_4 order: " + s4.order());
        System.out.println("\nNote: Subgroup lattice not shown (S_4 is too large)");
        System.out.println("Showing Cayley Table and Cayley Graph instead.\n");

        CayleyTableViewer.show(s4, "S_4 Cayley Table");

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        CayleyGraphViewer.show(s4, "S_4 Cayley Graph");
    }
}
