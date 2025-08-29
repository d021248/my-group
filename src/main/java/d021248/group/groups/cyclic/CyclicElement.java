package d021248.group.groups.cyclic;

/**
 * Represents an element of a cyclic group of order n.
 * The value is an integer in [0, n-1].
 */
public record CyclicElement(int value, int order) {
    public CyclicElement inverse() {
        return CyclicElementHelper.inverse(this);
    }

    public String toString() {
        return String.format("%d (mod %d)", value, order);
    }
}
