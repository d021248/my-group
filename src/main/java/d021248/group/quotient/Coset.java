package d021248.group.quotient;

import java.util.Objects;
import java.util.Set;

import d021248.group.FiniteGroup;
import d021248.group.api.Element;
import d021248.group.subgroup.Subgroup;

/**
 * Represents a coset gH in a quotient group G/H.
 * <p>
 * A coset is the set {gh : h ∈ H} for some g ∈ G and subgroup H ≤ G.
 * Two cosets are equal if they represent the same set of elements.
 * </p>
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * {
 *     &#64;code
 *     // In Z_6 with subgroup H = {0, 3}
 *     // Cosets are: 0H = {0,3}, 1H = {1,4}, 2H = {2,5}
 *     CyclicGroup z6 = new CyclicGroup(6);
 *     Subgroup<CyclicElement> h = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(3, 6)));
 * 
 *     Coset<CyclicElement> coset0 = new Coset<>(z6, h, new CyclicElement(0, 6));
 *     Coset<CyclicElement> coset1 = new Coset<>(z6, h, new CyclicElement(1, 6));
 * }
 * </pre>
 * 
 * @param <E> the type of group elements
 */
public record Coset<E extends Element>(FiniteGroup<E> parent, Subgroup<E> subgroup,
        E representative) implements Element {

    public Coset {
        Objects.requireNonNull(parent, "parent group must not be null");
        Objects.requireNonNull(subgroup, "subgroup must not be null");
        Objects.requireNonNull(representative, "representative must not be null");
        if (!parent.elements().contains(representative)) {
            throw new IllegalArgumentException("representative must be in parent group");
        }
    }

    /**
     * Compute all elements in this coset (left coset gH).
     */
    public Set<E> elements() {
        return subgroup.elements().stream()
                .map(h -> parent.operate(representative, h))
                .collect(java.util.stream.Collectors.toUnmodifiableSet());
    }

    /**
     * Two cosets are equal if they contain the same elements.
     * <p>
     * For normal subgroups, this is equivalent to checking if representatives
     * differ by an element of H.
     * </p>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Coset<?> other))
            return false;
        return elements().equals(other.elements());
    }

    @Override
    public int hashCode() {
        // Use hash of element set for consistency with equals
        return elements().hashCode();
    }

    @Override
    public String toString() {
        if (representative.equals(parent.identity())) {
            return "H";
        }
        return representative + "H";
    }
}
