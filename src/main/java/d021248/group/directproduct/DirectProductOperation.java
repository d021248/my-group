package d021248.group.directproduct;

import d021248.group.api.Element;
import d021248.group.api.Operation;

public class DirectProductOperation<A extends Element, B extends Element>
        implements Operation<DirectProductElement<A, B>> {
    private final Operation<A> opA;
    private final Operation<B> opB;

    public DirectProductOperation(Operation<A> opA, Operation<B> opB) {
        this.opA = opA;
        this.opB = opB;
    }

    @Override
    public DirectProductElement<A, B> apply(DirectProductElement<A, B> left, DirectProductElement<A, B> right) {
        return new DirectProductElement<>(
                opA.apply(left.first(), right.first()),
                opB.apply(left.second(), right.second()));
    }
}
