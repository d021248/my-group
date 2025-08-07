package d021248.group.alternating;

import java.util.HashSet;
import java.util.Set;

import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.PermutationHelper;

/**
 * Utility methods for alternating groups.
 */
public class AlternatingGroupHelper {
    private AlternatingGroupHelper() {
    }

    /**
     * Returns the set of generators for the alternating group A_n.
     * For simplicity, use 3-cycles as generators.
     */
    public static Set<Permutation> getGenerators(int n) {
        Set<Permutation> generators = new HashSet<>();
        if (n >= 3) {
            for (int i = 1; i <= n - 2; i++) {
                int[] cycle = new int[n];
                for (int j = 0; j < n; j++)
                    cycle[j] = j + 1;
                int tmp = cycle[i - 1];
                cycle[i - 1] = cycle[i];
                cycle[i] = cycle[i + 1];
                cycle[i + 1] = tmp;
                generators.add(new Permutation(cycle));
            }
        }
        return generators;
    }

    /**
     * Returns all even permutations of n elements.
     */
    public static Set<Permutation> getEvenPermutations(int n) {
        Set<Permutation> evenPerms = new HashSet<>();
        PermutationHelper.generateAllPermutations(n, perm -> {
            if (PermutationHelper.isEven(perm.mapping())) {
                evenPerms.add(perm);
            }
        });
        return evenPerms;
    }
}
