package d021248.group.cyclic;

import d021248.group.api.Operation;

/**
 * The group operation for a cyclic group: addition modulo n.
 */
public class CyclicOperation implements Operation<CyclicElement> {
    @Override
    public CyclicElement calculate(CyclicElement left, CyclicElement right) {
        int n = left.order();
        return new CyclicElement((left.value() + right.value()) % n, n);
    }
}
