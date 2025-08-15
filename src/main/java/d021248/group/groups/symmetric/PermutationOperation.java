package d021248.group.groups.symmetric;

import d021248.group.api.Operation;

public class PermutationOperation implements Operation<Permutation> {

    @Override
    public Permutation apply(Permutation left, Permutation right) {
        final int n = left.mapping().length;
        if (right.mapping().length != n) {
            throw new IllegalArgumentException("Permutations must be of the same size");
        }
        final int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = left.mapping()[right.mapping()[i] - 1];
        }
        return new Permutation(result);
    }
}
