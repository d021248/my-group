package d021248.group.viz.examples;

import d021248.group.GroupFactory;
import d021248.group.viz.CayleyGraphViewer;
import d021248.group.viz.CayleyTableViewer;
import d021248.group.viz.SubgroupLatticeViewer;

/**
 * Quick test of the visualizations with a small group.
 */
public class QuickVizTest {
    public static void main(String[] args) {
        // Test with D4 (dihedral group of order 8)
        var d4 = GroupFactory.dihedral(4);

        System.out.println("Launching D_4 visualizations...");
        System.out.println("Order: " + d4.order());

        // Launch all three visualizations
        CayleyTableViewer.show(d4, "D_4 Cayley Table");

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
        }

        SubgroupLatticeViewer.show(d4, "D_4 Subgroup Lattice");

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
        }

        CayleyGraphViewer.show(d4, "D_4 Cayley Graph");
    }
}
