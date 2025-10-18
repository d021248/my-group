package d021248.group.symmetric;

import d021248.group.api.Operation;

public final class PermutationOperation implements Operation<Permutation> {
    @Override
    public Permutation calculate(Permutation left, Permutation right) {
        return left.compose(right); // left ∘ right
    }
}
