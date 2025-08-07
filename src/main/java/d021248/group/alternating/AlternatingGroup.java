package d021248.group.alternating;

import java.util.Set;

import d021248.group.base.AbstractGroup;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.PermutationOperation;

/**
 * The alternating group A_n (even permutations).
 */
public class AlternatingGroup extends AbstractGroup<Permutation> {
    private final int order;

    public AlternatingGroup(int n) {
        super(AlternatingGroupHelper.getGenerators(n), new PermutationOperation());
        this.order = n;
    }

    @Override
    public Set<Permutation> elements() {
        return AlternatingGroupHelper.getEvenPermutations(order);
    }

    public int order() {
        return order;
    }
}
