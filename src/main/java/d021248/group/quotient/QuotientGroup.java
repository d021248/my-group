package d021248.group.quotient;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import d021248.group.FiniteGroup;
import d021248.group.api.Element;
import d021248.group.api.Operation;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupGenerator;

/**
 * Quotient group G/H where H is a normal subgroup of G.
 * <p>
 * The quotient group consists of cosets gH where g ∈ G, with the operation
 * (g₁H)(g₂H) = (g₁g₂)H. This operation is well-defined only when H is normal
 * (gH = Hg for all g ∈ G).
 * </p>
 * <p>
 * The order of G/H equals |G|/|H| by Lagrange's theorem.
 * </p>
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * {
 *     &#64;code
 *     // Z_6 / {0,3} ≅ Z_2
 *     CyclicGroup z6 = new CyclicGroup(6);
 *     Subgroup<CyclicElement> h = SubgroupGenerator.generate(z6, Set.of(new CyclicElement(3, 6)));
 *     QuotientGroup<CyclicElement> quotient = new QuotientGroup<>(z6, h);
 * 
 *     System.out.println(quotient.order()); // 2 (= 6/3)
 * 
 *     // S_3 / A_3 ≅ Z_2 (sign homomorphism)
 *     SymmetricGroup s3 = new SymmetricGroup(3);
 *     AlternatingGroup a3Impl = new AlternatingGroup(3);
 *     Subgroup<Permutation> a3 = new Subgroup<>(s3, a3Impl.elements());
 *     QuotientGroup<Permutation> s3modA3 = new QuotientGroup<>(s3, a3);
 *     System.out.println(s3modA3.order()); // 2 (= 6/3)
 * }
 * </pre>
 * 
 * @param <E> the type of group elements
 */
public final class QuotientGroup<E extends Element> implements FiniteGroup<Coset<E>> {
    private final FiniteGroup<E> parent;
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
    public QuotientGroup(FiniteGroup<E> parent, Subgroup<E> normalSubgroup) {
        this.parent = Objects.requireNonNull(parent, "parent group must not be null");
        this.normalSubgroup = Objects.requireNonNull(normalSubgroup, "normal subgroup must not be null");

        if (!SubgroupGenerator.isNormal(parent, normalSubgroup)) {
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

    /**
     * Return the parent group G.
     */
    public FiniteGroup<E> parent() {
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
