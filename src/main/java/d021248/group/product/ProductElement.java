package d021248.group.product;

import d021248.group.api.Element;

/**
 * Element of a direct product group, represented as a pair of elements from
 * constituent groups.
 * 
 * @param <E1> Type of first component element
 * @param <E2> Type of second component element
 */
public record ProductElement<E1 extends Element, E2 extends Element>(E1 first, E2 second) implements Element {
    public ProductElement {
        if (first == null || second == null)
            throw new IllegalArgumentException("Product element components must not be null");
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
