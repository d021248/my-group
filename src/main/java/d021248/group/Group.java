package d021248.group;

import java.util.Set;

import d021248.group.api.Element;
import d021248.group.api.Operation;

public interface Group<E extends Element> {
    /**
     * Return all elements of the group (finite groups assumed for enumeration).
     * Must be non-empty and contain the identity.
     */
    Set<E> elements();

    /**
     * Return the underlying total binary operation. Implementations MUST be
     * closed over {@link #elements()} and never return null.
     */
    Operation<E> operation();

    /** Identity element of the group. Never null. */
    E identity();

    /** Inverse of a given element. Never null; throws if e not in group. */
    E inverse(E e);

    /** Convenience: apply operation directly. Never returns null. */
    default E operate(E a, E b) {
        return operation().calculate(a, b);
    }
}
