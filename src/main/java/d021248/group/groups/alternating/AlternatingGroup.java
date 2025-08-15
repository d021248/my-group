package d021248.group.groups.alternating;

import java.util.Set;
import java.util.stream.Collectors;

import d021248.group.groups.symmetric.Permutation;
import d021248.group.groups.symmetric.SymmetricGroup;

public class AlternatingGroup extends SymmetricGroup {

    private final Set<Permutation> elements;

    public AlternatingGroup(int n) {
        super(n);
        this.elements = super.elements()
                .stream()
                .filter(Permutation::isEven)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Permutation> elements() {
        return elements;
    }

}
