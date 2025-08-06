package d021248.group.cyclic;

import d021248.group.api.Element;

/**
 * Represents an element of a cyclic group of order n.
 * The value is an integer in [0, n-1].
 */
public record CyclicElement(int value, int order) implements Element {
    @Override
    public CyclicElement inverse() {
        // The inverse of k mod n is (n - k) mod n
        return new CyclicElement((order - value) % order, order);
    }

    @Override
    public String toString() {
        return String.format("%d (mod %d)", value, order);
    }
}
