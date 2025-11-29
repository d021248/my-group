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
    public ProductElement<E1, E2> inverse() {
        @SuppressWarnings("unchecked")
        E1 inv1 = (E1) first.inverse();
        @SuppressWarnings("unchecked")
        E2 inv2 = (E2) second.inverse();
        return new ProductElement<>(inv1, inv2);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
