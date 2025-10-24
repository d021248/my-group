package d021248.group;

import d021248.group.api.Element;

/**
 * Extension of {@link Group} for finite groups providing their order.
 * <p>
 * Order equals the size of {@link Group#elements()} and is strictly positive.
 * </p>
 */
public interface FiniteGroup<E extends Element> extends Group<E> {
    /** Order (number of elements) of the finite group. */
    default int order() {
        return elements().size();
    }
}
