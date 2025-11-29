package d021248.group.symmetric;

import java.util.Arrays;
import java.util.List;

/**
 * Factory methods for creating common permutation patterns.
 * <p>
 * This class provides convenient builders for identity permutations, cycles,
 * transpositions, and compositions from disjoint cycle representations.
 * </p>
 */
public final class PermutationFactory {
    private PermutationFactory() {
    }

    /**
     * Create identity permutation on n elements.
     * 
     * @param n number of elements (must be >= 1)
     * @return identity permutation [1, 2, 3, ..., n]
     * @throws IllegalArgumentException if n < 1
     */
    public static Permutation identity(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be >= 1");
        int[] mapping = new int[n];
        for (int i = 0; i < n; i++)
            mapping[i] = i + 1;
        return new Permutation(mapping);
    }

    /**
     * Create a cycle permutation from the given elements in cycle notation.
     * <p>
     * Example: {@code cycle(1, 3, 2)} creates the cycle (1 3 2), which maps 1→3,
     * 3→2, 2→1.
     * </p>
     * 
     * @param elements elements in the cycle (1-based positions)
     * @return permutation representing the cycle
     * @throws IllegalArgumentException if elements are invalid
     */
    public static Permutation cycle(int... elements) {
        if (elements == null || elements.length == 0)
            throw new IllegalArgumentException("cycle must have at least one element");
        int max = Arrays.stream(elements).max().orElse(0);
        int min = Arrays.stream(elements).min().orElse(Integer.MAX_VALUE);
        if (min < 1)
            throw new IllegalArgumentException("all elements must be >= 1");
        int[] mapping = new int[max];
        for (int i = 0; i < max; i++)
            mapping[i] = i + 1; // identity by default
        for (int i = 0; i < elements.length; i++) {
            int from = elements[i];
            int to = elements[(i + 1) % elements.length];
            mapping[from - 1] = to;
        }
        return new Permutation(mapping);
    }

    /**
     * Create a transposition (2-cycle) swapping two elements.
     * <p>
     * Example: {@code transposition(2, 5, 6)} creates (2 5) in S_6.
     * </p>
     * 
     * @param i first position (1-based)
     * @param j second position (1-based)
     * @param n size of permutation
     * @return transposition swapping positions i and j
     * @throws IllegalArgumentException if parameters are invalid
     */
    public static Permutation transposition(int i, int j, int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be >= 1");
        if (i < 1 || i > n || j < 1 || j > n)
            throw new IllegalArgumentException("positions must be in range [1, " + n + "]");
        int[] mapping = new int[n];
        for (int k = 0; k < n; k++)
            mapping[k] = k + 1;
        mapping[i - 1] = j;
        mapping[j - 1] = i;
        return new Permutation(mapping);
    }

    /**
     * Create permutation from disjoint cycle representation.
     * <p>
     * Example: {@code fromCycles(6, List.of(List.of(1,3,2), List.of(4,5)))} creates
     * (1 3 2)(4 5) in S_6.
     * </p>
     * 
     * @param n      size of permutation
     * @param cycles list of cycles, each cycle is a list of 1-based positions
     * @return permutation composed of the given cycles
     * @throws IllegalArgumentException if cycles are invalid
     */
    public static Permutation fromCycles(int n, List<List<Integer>> cycles) {
        if (n < 1)
            throw new IllegalArgumentException("n must be >= 1");
        if (cycles == null)
            throw new IllegalArgumentException("cycles must not be null");
        int[] mapping = new int[n];
        for (int i = 0; i < n; i++)
            mapping[i] = i + 1; // identity by default
        for (List<Integer> cycle : cycles) {
            if (cycle == null || cycle.isEmpty())
                continue;
            for (int i = 0; i < cycle.size(); i++) {
                int from = cycle.get(i);
                int to = cycle.get((i + 1) % cycle.size());
                if (from < 1 || from > n || to < 1 || to > n)
                    throw new IllegalArgumentException("cycle elements must be in range [1, " + n + "]");
                mapping[from - 1] = to;
            }
        }
        return new Permutation(mapping);
    }
}
