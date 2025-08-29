package d021248.group.groups.cyclic;

import java.util.function.BinaryOperator;

/**
 * The group operation for a cyclic group: addition modulo n.
 */
public class CyclicOperation implements BinaryOperator<CyclicElement> {
    @Override
    public CyclicElement apply(CyclicElement left, CyclicElement right) {
        int n = left.order();
        return new CyclicElement((left.value() + right.value()) % n, n);
    }
}
