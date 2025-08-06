package d021248.group.symmetric;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility methods for permutations.
 */
public class SymmetricGroupHelper {
    private SymmetricGroupHelper() {
    }

    /**
     * Returns the set of generators for the symmetric group S_n.
     * This typically includes the n-cycle (1 2 ... n) and a transposition (1 2).
     *
     * @param n the size of the symmetric group
     * @return a set of generator permutations
     */
    public static Set<Permutation> getGenerators(int n) {
        Set<Permutation> generators = new HashSet<>();
        // n-cycle: (1 2 ... n)
        int[] nCycle = new int[n];
        for (int i = 0; i < n - 1; i++) {
            nCycle[i] = i + 2;
        }
        nCycle[n - 1] = 1;
        generators.add(new Permutation(nCycle));
        // transposition: (1 2)
        int[] transposition = new int[n];
        for (int i = 0; i < n; i++) {
            transposition[i] = i + 1;
        }
        if (n > 1) {
            transposition[0] = 2;
            transposition[1] = 1;
        }
        generators.add(new Permutation(transposition));
        return generators;
    }
}
