package d021248.group;

import java.util.Set;

import d021248.group.api.Element;
import d021248.group.api.Operation;

/**
 * Core interface for mathematical groups.
 * <p>
 * A group is a set with an associative binary operation, an identity element,
 * and inverses for all elements. This interface provides the minimal contract.
 * </p>
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * {
 *     &#64;code
 *     CyclicGroup z6 = new CyclicGroup(6);
 *     CyclicElement a = new CyclicElement(2, 6);
 *     CyclicElement b = new CyclicElement(3, 6);
 *     CyclicElement c = z6.operate(a, b); // 5 (mod 6)
 *     CyclicElement inv = z6.inverse(a); // 4 (mod 6)
 * }
 * </pre>
 * 
 * @param <E> the type of group elements
 */
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
    default E inverse(E e) {
        @SuppressWarnings("unchecked")
        E inv = (E) e.inverse();
        return inv;
    }

    /** Convenience: apply operation directly. Never returns null. */
    default E operate(E a, E b) {
        return operation().calculate(a, b);
    }
}
