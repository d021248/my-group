package d021248.group.cyclicproduct;

import d021248.group.api.Operation;
import d021248.group.cyclic.CyclicElement;

public class CyclicProductOperation implements Operation<CyclicProductElement> {
    @Override
    public CyclicProductElement calculate(CyclicProductElement left, CyclicProductElement right) {
        return new CyclicProductElement(
                left.first().order() == right.first().order() ? new CyclicElement(
                        (left.first().value() + right.first().value()) % left.first().order(), left.first().order())
                        : left.first(),
                left.second().order() == right.second().order() ? new CyclicElement(
                        (left.second().value() + right.second().value()) % left.second().order(), left.second().order())
                        : left.second());
    }
}
