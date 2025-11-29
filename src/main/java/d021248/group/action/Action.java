package d021248.group.action;

import java.util.Objects;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;

/**
 * Concrete implementation of a group action with explicit group and set.
 *
 * @param <E> element type of the acting group
 * @param <X> element type of the set being acted upon
 */
public record Action<E extends Element, X>(
        Group<E> group,
        Set<X> set,
        GroupAction<E, X> action) implements GroupAction<E, X> {

    public Action {
        Objects.requireNonNull(group, "Group cannot be null");
        Objects.requireNonNull(set, "Set cannot be null");
        Objects.requireNonNull(action, "Action cannot be null");
        if (set.isEmpty()) {
            throw new IllegalArgumentException("Set cannot be empty");
        }
    }

    @Override
    public X act(E g, X x) {
        return action.act(g, x);
    }
}
