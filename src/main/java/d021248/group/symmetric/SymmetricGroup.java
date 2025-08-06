package d021248.group.symmetric;

import d021248.group.base.AbstractGroup;

public class SymmetricGroup extends AbstractGroup<Permutation> {

    public SymmetricGroup(int n) {
        super(SymmetricGroupHelper.getGenerators(n), new PermutationOperation());
    }

}
