package d021248.group.cyclic;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility methods for cyclic groups.
 */
public class CyclicGroupHelper {
    private CyclicGroupHelper() {
    }

    /**
     * Returns the set of generators for the cyclic group of order n.
     * The generator is 1 (mod n) if n > 1.
     */
    public static Set<CyclicElement> getGenerators(int n) {
        Set<CyclicElement> generators = new HashSet<>();
        if (n > 1) {
            generators.add(new CyclicElement(1, n));
        }
        return generators;
    }
}
