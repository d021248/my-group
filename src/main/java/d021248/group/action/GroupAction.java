package d021248.group.action;

import d021248.group.Group;
import d021248.group.api.Element;

/**
 * A group action of G on a set X.
 * <p>
 * A group action is a function G × X → X satisfying:
 * <ul>
 * <li>Identity: e · x = x for all x ∈ X</li>
 * <li>Compatibility: (g₁ ∘ g₂) · x = g₁ · (g₂ · x) for all g₁, g₂ ∈ G, x ∈
 * X</li>
 * </ul>
 *
 * @param <E> element type of the group
 * @param <X> element type of the set being acted upon
 */
@FunctionalInterface
public interface GroupAction<E extends Element, X> {

    /**
     * Apply a group element to a set element.
     *
     * @param g group element
     * @param x set element
     * @return result of g acting on x
     */
    X act(E g, X x);

    /**
     * The group performing the action.
     *
     * @return the acting group
     */
    default Group<E> group() {
        throw new UnsupportedOperationException("Group not specified");
    }
}
