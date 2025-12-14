package d021248.group.quotient;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.api.Operation;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupAnalyzer;

/**
 * Quotient group G/H where H is a normal subgroup of G.
 * <p>
 * Elements are left cosets gH = {gh : h ∈ H}. The operation is
 * (g₁H)(g₂H) = (g₁g₂)H.
 * </p>
 * <p>
 * Requires H to be normal in G: for all g ∈ G and h ∈ H, ghg⁻¹ ∈ H
 * (equivalently
 * gH = Hg). If H is not normal, construction throws IllegalArgumentException.
 * </p>
 * <p>
 * The quotient group has order |G|/|H| by Lagrange's theorem.
 * </p>
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 * {@code
 * SymmetricGroup s3 = new SymmetricGroup(3);
 * AlternatingGroup a3 = new AlternatingGroup(3);
 * Subgroup<Permutation> subA3 = new Subgroup<>(s3, a3.elements());
 * QuotientGroup<Permutation> s3modA3 = new QuotientGroup<>(s3, subA3);
 * System.out.println(s3modA3.order()); // 2 (= 6/3)
 * }
 * </pre>
 * 
 * @param <E> the type of group elements
 */
public final class QuotientGroup<E extends Element> implements Group<Coset<E>> {
    private final Group<E> parent;
    private final Subgroup<E> normalSubgroup;
    private final Set<Coset<E>> cosets;
    private final Operation<Coset<E>> operation;
    private final Coset<E> identity;

    /**
     * Create quotient group G/H.
     * 
     * @param parent         the parent group G
     * @param normalSubgroup the normal subgroup H
     * @throws IllegalArgumentException if H is not normal in G
     */
    public QuotientGroup(Group<E> parent, Subgroup<E> normalSubgroup) {
        this.parent = Objects.requireNonNull(parent, "parent group must not be null");
        this.normalSubgroup = Objects.requireNonNull(normalSubgroup, "normal subgroup must not be null");

        if (!SubgroupAnalyzer.isNormal(parent, normalSubgroup)) {
            throw new IllegalArgumentException("Subgroup must be normal for quotient group to be well-defined");
        }

        this.cosets = generateCosets();
        this.operation = this::operateInternal;
        this.identity = new Coset<>(parent, normalSubgroup, parent.identity());
    }

    private Set<Coset<E>> generateCosets() {
        Set<Coset<E>> result = new HashSet<>();
        Set<Set<E>> seenCosetElements = new HashSet<>();

        for (E g : parent.elements()) {
            Coset<E> coset = new Coset<>(parent, normalSubgroup, g);
            Set<E> cosetElements = coset.elements();

            // Only add if we haven't seen this coset before
            if (!seenCosetElements.contains(cosetElements)) {
                result.add(coset);
                seenCosetElements.add(cosetElements);
            }
        }

        return Set.copyOf(result);
    }

    private Coset<E> operateInternal(Coset<E> a, Coset<E> b) {
        // (g₁H)(g₂H) = (g₁g₂)H
        E product = parent.operate(a.representative(), b.representative());
        return new Coset<>(parent, normalSubgroup, product);
    }

    @Override
    public Set<Coset<E>> elements() {
        return cosets;
    }

    @Override
    public Operation<Coset<E>> operation() {
        return operation;
    }

    @Override
    public Coset<E> identity() {
        return identity;
    }

    @Override
    public Coset<E> inverse(Coset<E> element) {
        E invRep = parent.inverse(element.representative());
        return new Coset<>(parent, normalSubgroup, invRep);
    }

    /**
     * Return the parent group G.
     */
    public Group<E> parent() {
        return parent;
    }

    /**
     * Return the normal subgroup H.
     */
    public Subgroup<E> normalSubgroup() {
        return normalSubgroup;
    }

    @Override
    public String toString() {
        return "G/H where |G|=" + parent.order() + ", |H|=" + normalSubgroup.order() + ", |G/H|=" + order();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof QuotientGroup<?> other))
            return false;
        return parent.equals(other.parent) && normalSubgroup.equals(other.normalSubgroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, normalSubgroup);
    }
}
