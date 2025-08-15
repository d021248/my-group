package d021248.group.groups.cyclic;

import d021248.group.api.Element;

/**
 * Represents an element of a cyclic group of order n.
 * The value is an integer in [0, n-1].
 */
public record CyclicElement(int value, int order) implements Element {

    public CyclicElement {
        CyclicElementHelper.validate(value, order);
    }

    @Override
    public CyclicElement inverse() {
        return CyclicElementHelper.inverse(this);
    }

    @Override
    public String toString() {
        return String.format("%d (mod %d)", value, order);
    }
}
