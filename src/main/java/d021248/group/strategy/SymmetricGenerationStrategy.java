package d021248.group.strategy;

import java.util.Set;

import d021248.group.FiniteGroup;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Standard generating set for S_n: n-cycle and a simple transposition.
 * <p>
 * Replaces former SymmetricGroupHelper.getGenerators.
 * </p>
 */
public final class SymmetricGenerationStrategy implements GenerationStrategy<Permutation> {
    private static final SymmetricGenerationStrategy INSTANCE = new SymmetricGenerationStrategy();

    private SymmetricGenerationStrategy() {
    }

    public static SymmetricGenerationStrategy get() {
        return INSTANCE;
    }

    @Override
    public Set<Permutation> generators(FiniteGroup<Permutation> group) {
        int n = ((SymmetricGroup) group).degree();
        if (n < 2)
            throw new IllegalArgumentException("n must be >= 2");
        int[] cycle = new int[n];
        for (int i = 0; i < n - 1; i++)
            cycle[i] = i + 2;
        cycle[n - 1] = 1;
        Permutation nCycle = new Permutation(cycle);
        int[] trans = new int[n];
        for (int i = 0; i < n; i++)
            trans[i] = i + 1;
        trans[0] = 2;
        trans[1] = 1;
        Permutation swap12 = new Permutation(trans);
        return Set.of(nCycle, swap12);
    }

    @Override
    public String name() {
        return "symmetric-standard";
    }
}
