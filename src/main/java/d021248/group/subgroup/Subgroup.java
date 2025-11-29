package d021248.group.subgroup;

import java.util.Objects;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.api.Operation;

/**
 * Subgroup of a parent group sharing the same operation.
 * <p>
 * A subgroup H ≤ G is a subset closed under the group operation, containing
 * the identity, and containing inverses.
 * </p>
 */
public final class Subgroup<E extends Element> implements Group<E> {
    private final Group<E> parent;
    private final Set<E> elements;

    public Subgroup(Group<E> parent, Set<E> elements) {
        this.parent = Objects.requireNonNull(parent, "parent group must not be null");
        Objects.requireNonNull(elements, "element set must not be null");
        if (elements.isEmpty())
            throw new IllegalArgumentException("Subgroup must contain at least the identity");
        if (!elements.contains(parent.identity()))
            throw new IllegalArgumentException("Subgroup must contain the identity element");
        verifySubgroup(parent, elements);
        this.elements = Set.copyOf(elements);
    }

    private static <E extends Element> void verifySubgroup(Group<E> parent, Set<E> elements) {
        // Check closure under operation
        for (E a : elements) {
            for (E b : elements) {
                E prod = parent.operate(a, b);
                if (!elements.contains(prod)) {
                    throw new IllegalArgumentException(
                            "Not closed under operation: " + a + " * " + b + " = " + prod + " not in subset");
                }
            }
        }
        // Check inverses
        for (E e : elements) {
            E inv = parent.inverse(e);
            if (!elements.contains(inv)) {
                throw new IllegalArgumentException("Inverse of " + e + " not in subset");
            }
        }
    }

    @Override
    public Set<E> elements() {
        return elements;
    }

    @Override
    public Operation<E> operation() {
        return parent.operation();
    }

    @Override
    public E identity() {
        return parent.identity();
    }

    @Override
    public E inverse(E element) {
        return parent.inverse(element);
    }

    /** The parent group from which this subgroup is derived. */
    public Group<E> parent() {
        return parent;
    }

    /**
     * Index of this subgroup in the parent group (|G| / |H|). Only defined when H
     * divides G evenly (Lagrange's theorem).
     */
    public int index() {
        int parentOrder = parent.order();
        int subgroupOrder = order();
        if (parentOrder % subgroupOrder != 0) {
            throw new IllegalStateException(
                    "Subgroup order " + subgroupOrder + " does not divide parent order " + parentOrder);
        }
        return parentOrder / subgroupOrder;
    }
}
