package d021248.group;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import d021248.group.api.Element;

public final class Generator {
    private Generator() {
        /* utility class */ }

    /**
     * Returns the closure of the provided generator elements under the group's
     * operation and inverses. The original {@code generators} set is not mutated.
     *
     * Contract (strict semantics):
     * - If {@code generators} is empty, the result contains only the identity.
     * - Identity is always included.
     * - Operation and inverse never return null; inputs must belong to the group.
     * - Null inputs throw {@link IllegalArgumentException}.
     */
    public static <E extends Element> Set<E> generate(Group<E> group, Set<E> generators) {
        Objects.requireNonNull(group, "group must not be null");
        Objects.requireNonNull(generators, "generators must not be null");

        Set<E> closure = new HashSet<>();
        Deque<E> work = new ArrayDeque<>();
        List<E> ordered = new ArrayList<>(); // stable ordered discovery

        seed(group, generators, closure, work, ordered);

        while (!work.isEmpty()) {
            E current = work.removeFirst();
            processInverse(group, current, closure, work);
            processProducts(group, current, closure, work, ordered);
            if (isComplete(group, closure))
                break;
        }
        return closure;
    }

    private static <E extends Element> void seed(Group<E> group, Set<E> generators, Set<E> closure, Deque<E> work,
            List<E> ordered) {
        closure.addAll(generators);
        for (E g : generators) {
            work.add(g);
            ordered.add(g);
        }
        E id = group.identity();
        if (closure.add(id)) {
            work.add(id);
            ordered.add(id);
        }
    }

    private static <E extends Element> void processInverse(Group<E> group, E current, Set<E> closure, Deque<E> work) {
        E inv = group.inverse(current);
        if (closure.add(inv)) {
            work.add(inv);
        }
    }

    private static <E extends Element> void processProducts(Group<E> group, E current, Set<E> closure, Deque<E> work,
            List<E> ordered) {
        int sizeAtStart = ordered.size();
        for (int i = 0; i < sizeAtStart; i++) {
            E other = ordered.get(i);
            E prod1 = group.operate(current, other);
            if (closure.add(prod1)) {
                work.add(prod1);
                ordered.add(prod1);
            }
            E prod2 = group.operate(other, current);
            if (closure.add(prod2)) {
                work.add(prod2);
                ordered.add(prod2);
            }
        }
    }

    private static boolean isComplete(Group<?> group, Set<?> closure) {
        try {
            return closure.size() == group.order();
        } catch (UnsupportedOperationException | NullPointerException e) {
            // elements() not available or not implemented - cannot determine completeness
            return false;
        }
    }
}
