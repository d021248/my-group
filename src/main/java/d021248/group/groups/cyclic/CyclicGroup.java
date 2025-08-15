package d021248.group.groups.cyclic;

import d021248.group.base.AbstractGroup;

/**
 * The cyclic group of order n (integers mod n under addition).
 */
public class CyclicGroup extends AbstractGroup<CyclicElement> {
    private final int order;

    public CyclicGroup(int n) {
        super(new CyclicGroupGeneratingSystem(n), new CyclicOperation());
        this.order = n;
    }

    public int order() {
        return order;
    }
}
