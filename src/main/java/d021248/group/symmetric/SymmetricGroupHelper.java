package d021248.group.symmetric;

import java.util.Set;

public final class SymmetricGroupHelper {
    private SymmetricGroupHelper() {
    }

    public static Set<Permutation> getGenerators(int n) {
        if (n < 2)
            throw new IllegalArgumentException("n must be >= 2");
        // Standard generators: an n-cycle (1 2 ... n) and a simple transposition (1 2)
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
}
