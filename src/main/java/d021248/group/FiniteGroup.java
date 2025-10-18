package d021248.group;

import d021248.group.api.Element;

/**
 * Extension of Group for finite groups providing their order.
 * Kept lightweight to avoid forcing enumeration strategies.
 */
public interface FiniteGroup<E extends Element> extends Group<E> {
    /** Order (number of elements) of the finite group. */
    int order();
}
