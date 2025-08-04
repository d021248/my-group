package d021248.group.permutation;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SymmetricGroupGeneratorFactory {
    // Private constructor to prevent instantiation
    private SymmetricGroupGeneratorFactory() {
    }

    /**
     * Returns the standard generators of the symmetric group S_n:
     * - the n-cycle (1 2 ... n)
     * - the transposition (1 2)
     */
    public static Set<Permutation> generators(int n) {
        if (n < 2) {
            throw new IllegalArgumentException("n must be at least 2");
        }
        Set<Permutation> gens = new LinkedHashSet<>();
        // n-cycle: (1 2 ... n)
        List<Integer> nCycle = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            nCycle.add(i);
        }
        gens.add(new Permutation(List.of(nCycle)));
        // transposition: (1 2)
        gens.add(new Permutation(List.of(List.of(1, 2))));
        return gens;
    }
}
