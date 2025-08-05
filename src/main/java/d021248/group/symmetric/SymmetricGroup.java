package d021248.group.symmetric;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import d021248.group.base.AbstractGroup;

public class SymmetricGroup extends AbstractGroup<Permutation> {

    public SymmetricGroup(int n) {
        super(generators(n), new PermutationMultiplication());
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
        gens.add(new Permutation(List.of(nCycle), n));
        // transposition: (1 2)
        gens.add(new Permutation(List.of(List.of(1, 2)), n));
        return gens;
    }

}
