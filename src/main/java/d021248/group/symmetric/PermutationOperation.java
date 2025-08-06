package d021248.group.symmetric;

import d021248.group.api.Operation;

public class PermutationOperation implements Operation<Permutation> {

    @Override
    public Permutation calculate(Permutation left, Permutation right) {
        return PermutationHelper.multiply(left, right);
    }
}
