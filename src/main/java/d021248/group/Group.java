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

    /**
     * Compute the inverse of an element in this group.
     * <p>
     * For every element g in the group, there exists an inverse g⁻¹ such that
     * g * g⁻¹ = g⁻¹ * g = identity.
     * </p>
     * 
     * @param element the element whose inverse to compute
     * @return the inverse of the element
     * @throws IllegalArgumentException if element is not in this group
     */
    E inverse(E element);

    /** Convenience: apply operation directly. Never returns null. */
    default E operate(E a, E b) {
        return operation().calculate(a, b);
    }

    /**
     * Compute the order of an element in this group.
     * <p>
     * The order is the smallest positive integer k such that g^k = e (identity).
     * </p>
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * {@code
     * CyclicGroup z12 = new CyclicGroup(12);
     * CyclicElement g = new CyclicElement(4, 12);
     * int order = z12.order(g); // 3, since 4+4+4 ≡ 0 (mod 12)
     * }
     * </pre>
     * 
     * @param element the element whose order to compute
     * @return the order of the element (always >= 1)
     */
    default int order(E element) {
        E current = element;
        E identity = identity();
        int order = 1;
        while (!current.equals(identity)) {
            current = operate(current, element);
            order++;
            if (order > 10000) // safety guard against infinite loops
                throw new IllegalStateException(
                        "Order computation exceeded limit (possible infinite group)");
        }
        return order;
    }
}
