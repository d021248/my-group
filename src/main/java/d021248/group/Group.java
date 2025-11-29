package d021248.group;

import java.util.Set;

import d021248.group.api.Element;
import d021248.group.api.Operation;

/**
 * Core interface for finite groups.
 * <p>
 * A group is a set with an associative binary operation, an identity element,
 * and inverses for all elements. This interface represents finite groups where
 * all elements can be enumerated.
 * </p>
 * <p>
 * The order of the group equals the size of {@link #elements()} and is strictly
 * positive. By Lagrange's theorem, the order of any element or subgroup divides
 * the group order.
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
 *     System.out.println(z6.order()); // 6
 * }
 * </pre>
 * 
 * @param <E> the type of group elements
 */
public interface Group<E extends Element> {
    /**
     * Return all elements of the group.
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

    /**
     * Return the order (number of elements) of this finite group.
     * 
     * @return the number of elements in the group (always >= 1)
     */
    default int order() {
        return elements().size();
    }

    /**
     * Check if this group is abelian (commutative).
     * <p>
     * A group is abelian if for all elements a,b: a*b = b*a
     * </p>
     * 
     * @return true if the group is abelian, false otherwise
     */
    default boolean isAbelian() {
        Set<E> elems = elements();
        for (E a : elems) {
            for (E b : elems) {
                if (!operate(a, b).equals(operate(b, a))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Compute the exponent of this group.
     * <p>
     * The exponent is the smallest positive integer n such that g^n = e for all
     * g ∈ G. Equivalently, it is the least common multiple of the orders of all
     * elements.
     * </p>
     * <p>
     * Properties:
     * </p>
     * <ul>
     * <li>exponent(G) divides |G| (order of group)</li>
     * <li>For cyclic groups, exponent equals order</li>
     * <li>For abelian groups, exponent = lcm of cyclic component orders</li>
     * </ul>
     * 
     * @return the exponent of the group
     */
    default int exponent() {
        int exp = 1;
        for (E g : elements()) {
            int elementOrder = order(g);
            exp = lcm(exp, elementOrder);
        }
        return exp;
    }

    /**
     * Compute least common multiple of two positive integers.
     */
    private static int lcm(int a, int b) {
        return (a / gcd(a, b)) * b;
    }

    /**
     * Compute greatest common divisor using Euclidean algorithm.
     */
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
