package d021248.group.directproduct;

import java.util.HashSet;
import java.util.Set;

import d021248.group.api.Element;
import d021248.group.api.Operation;
import d021248.group.base.AbstractGroup;

public class DirectProductGroup<A extends Element, B extends Element>
        extends AbstractGroup<DirectProductElement<A, B>> {
    private final Set<A> firstElements;
    private final Set<B> secondElements;

    public DirectProductGroup(Set<A> firstElements, Set<B> secondElements, Operation<A> opA, Operation<B> opB) {
        super(null, new DirectProductOperation<>(opA, opB)); // Pass operations to DirectProductOperation
        this.firstElements = firstElements;
        this.secondElements = secondElements;
    }

    @Override
    public Set<DirectProductElement<A, B>> elements() {
        Set<DirectProductElement<A, B>> elements = new HashSet<>();
        for (A a : firstElements) {
            for (B b : secondElements) {
                elements.add(new DirectProductElement<>(a, b));
            }
        }
        return elements;
    }
}
