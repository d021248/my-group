package d021248.group.cyclic;

import java.util.HashSet;
import java.util.Set;

import d021248.group.base.AbstractGroup;

/**
 * The cyclic group of order n (integers mod n under addition).
 */
public class CyclicGroup extends AbstractGroup<CyclicElement> {
    private final int order;

    public CyclicGroup(int n) {
        super(CyclicGroupHelper.getGenerators(n), new CyclicOperation());
        this.order = n;
    }

    @Override
    public Set<CyclicElement> elements() {
        Set<CyclicElement> elements = new HashSet<>();
        for (int i = 0; i < order; i++) {
            elements.add(new CyclicElement(i, order));
        }
        return elements;
    }

    public int order() {
        return order;
    }
}
