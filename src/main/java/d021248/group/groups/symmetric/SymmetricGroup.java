package d021248.group.groups.symmetric;

import d021248.group.base.AbstractGroup;

public class SymmetricGroup extends AbstractGroup<Permutation> {

    public SymmetricGroup(int n) {
        super(new SymmetricGroupGeneratingSystem(n).get(), new PermutationOperation());
    }

}