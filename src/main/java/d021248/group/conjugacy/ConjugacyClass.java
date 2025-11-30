package d021248.group.conjugacy;

import java.util.Objects;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;

/**
 * Represents a conjugacy class in a group.
 * <p>
 * The conjugacy class of an element g in group G is the set:
 * cl(g) = {xgx⁻¹ : x ∈ G}
 * </p>
 * <p>
 * Two elements are conjugate if they are in the same conjugacy class.
 * </p>
 * 
 * @param <E> the type of group elements
 */
public final class ConjugacyClass<E extends Element> {
    private final Group<E> parent;
    private final E representative;
    private final Set<E> elements;

    /**
     * Create a conjugacy class.
     * 
     * @param parent         the parent group
     * @param representative a representative element from the class
     * @param elements       all elements in the conjugacy class
     */
    public ConjugacyClass(Group<E> parent, E representative, Set<E> elements) {
        this.parent = Objects.requireNonNull(parent, "parent must not be null");
        this.representative = Objects.requireNonNull(representative, "representative must not be null");
        this.elements = Set.copyOf(Objects.requireNonNull(elements, "elements must not be null"));

        if (elements.isEmpty()) {
            throw new IllegalArgumentException("conjugacy class cannot be empty");
        }
        if (!elements.contains(representative)) {
            throw new IllegalArgumentException("representative must be in the conjugacy class");
        }
    }

    /**
     * Return the parent group.
     */
    public Group<E> parent() {
        return parent;
    }

    /**
     * Return a representative element from this conjugacy class.
     */
    public E representative() {
        return representative;
    }

    /**
     * Return all elements in this conjugacy class.
     */
    public Set<E> elements() {
        return elements;
    }

    /**
     * Return the size of this conjugacy class.
     * <p>
     * By the orbit-stabilizer theorem: |cl(g)| = |G| / |C_G(g)|
     * where C_G(g) is the centralizer of g.
     * </p>
     */
    public int size() {
        return elements.size();
    }

    /**
     * Check if an element is in this conjugacy class.
     */
    public boolean contains(E element) {
        return elements.contains(element);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ConjugacyClass<?> other))
            return false;
        return elements.equals(other.elements);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    @Override
    public String toString() {
        if (size() == 1) {
            return "cl(" + representative + ") = {" + representative + "}";
        }
        return "cl(" + representative + ") (" + size() + " elements)";
    }
}
