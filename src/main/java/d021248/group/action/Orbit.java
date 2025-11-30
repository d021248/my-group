package d021248.group.action;

import java.util.Objects;
import java.util.Set;

/**
 * An orbit under a group action.
 * <p>
 * The orbit of x under action of G is: orb(x) = {g · x | g ∈ G}
 *
 * @param <X> element type of the set
 */
public record Orbit<X>(X representative, Set<X> elements) {

    public Orbit {
        Objects.requireNonNull(representative, "Representative cannot be null");
        Objects.requireNonNull(elements, "Elements cannot be null");
        elements = Set.copyOf(elements);
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("Orbit cannot be empty");
        }
        if (!elements.contains(representative)) {
            throw new IllegalArgumentException("Representative must be in orbit");
        }
    }

    /**
     * Size of the orbit.
     *
     * @return number of elements in orbit
     */
    public int size() {
        return elements.size();
    }

    /**
     * Check if element is in this orbit.
     *
     * @param x element to check
     * @return true if x is in orbit
     */
    public boolean contains(X x) {
        return elements.contains(x);
    }

    @Override
    public String toString() {
        return "orb(" + representative + ")";
    }
}
