package d021248.group.dihedral;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility methods for dihedral groups.
 */
public class DihedralGroupHelper {
    private DihedralGroupHelper() {
    }

    /**
     * Returns the set of generators for the dihedral group D_n.
     * The standard generators are r (rotation by 1) and s (a reflection).
     */
    public static Set<DihedralElement> getGenerators(int n) {
        Set<DihedralElement> generators = new HashSet<>();
        generators.add(new DihedralElement(1, 0, n)); // r
        generators.add(new DihedralElement(0, 1, n)); // s
        return generators;
    }
}
