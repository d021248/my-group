package d021248.group;

import java.util.Set;

import d021248.group.api.Element;
import d021248.group.api.Operation;

public interface Group<E extends Element> {
    /** Return all elements of the group (finite groups assumed). */
    Set<E> elements();

    /** Return the underlying binary operation. */
    Operation<E> operation();

    /** Identity element of the group (may be null if not defined). */
    E identity();

    /** Inverse of a given element (may be null if not defined). */
    E inverse(E e);

    /** Convenience: apply operation directly. */
    default E operate(E a, E b) {
        return operation().calculate(a, b);
    }
}
