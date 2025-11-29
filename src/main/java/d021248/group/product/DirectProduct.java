package d021248.group.product;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import d021248.group.FiniteGroup;
import d021248.group.api.Element;
import d021248.group.api.Operation;

/**
 * Direct product of two finite groups G₁ × G₂.
 * <p>
 * The direct product has elements (g₁, g₂) where g₁ ∈ G₁ and g₂ ∈ G₂, with
 * component-wise operation: (g₁, g₂) * (h₁, h₂) = (g₁*h₁, g₂*h₂).
 * </p>
 * <p>
 * Order of the product equals |G₁| × |G₂|.
 * </p>
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * {@code
 * // Klein four-group V₄ ≅ Z₂ × Z₂
 * CyclicGroup z2a = new CyclicGroup(2);
 * CyclicGroup z2b = new CyclicGroup(2);
 * DirectProduct<CyclicElement, CyclicElement> v4 = new DirectProduct<>(z2a, z2b);
 * System.out.println(v4.order()); // 4
 * 
 * // All non-identity elements have order 2
 * ProductElement<CyclicElement, CyclicElement> a = new ProductElement<>(new CyclicElement(1, 2),
 *         new CyclicElement(0, 2));
 * System.out.println(v4.order(a)); // 2
 * }
 * </pre>
 */
public final class DirectProduct<E1 extends Element, E2 extends Element>
        implements FiniteGroup<ProductElement<E1, E2>> {
    private final FiniteGroup<E1> group1;
    private final FiniteGroup<E2> group2;
    private final Set<ProductElement<E1, E2>> elements;
    private final Operation<ProductElement<E1, E2>> op;
    private final ProductElement<E1, E2> identity;

    public DirectProduct(FiniteGroup<E1> group1, FiniteGroup<E2> group2) {
        this.group1 = Objects.requireNonNull(group1, "group1 must not be null");
        this.group2 = Objects.requireNonNull(group2, "group2 must not be null");
        this.elements = buildElements();
        this.op = this::operateInternal;
        this.identity = new ProductElement<>(group1.identity(), group2.identity());
    }

    private Set<ProductElement<E1, E2>> buildElements() {
        Set<ProductElement<E1, E2>> result = new HashSet<>();
        for (E1 e1 : group1.elements()) {
            for (E2 e2 : group2.elements()) {
                result.add(new ProductElement<>(e1, e2));
            }
        }
        return Set.copyOf(result);
    }

    private ProductElement<E1, E2> operateInternal(ProductElement<E1, E2> a, ProductElement<E1, E2> b) {
        E1 r1 = group1.operate(a.first(), b.first());
        E2 r2 = group2.operate(a.second(), b.second());
        return new ProductElement<>(r1, r2);
    }

    @Override
    public Set<ProductElement<E1, E2>> elements() {
        return elements;
    }

    @Override
    public Operation<ProductElement<E1, E2>> operation() {
        return op;
    }

    @Override
    public ProductElement<E1, E2> identity() {
        return identity;
    }

    @Override
    public ProductElement<E1, E2> inverse(ProductElement<E1, E2> element) {
        E1 inv1 = group1.inverse(element.first());
        E2 inv2 = group2.inverse(element.second());
        return new ProductElement<>(inv1, inv2);
    }

    /** First component group (G₁). */
    public FiniteGroup<E1> firstGroup() {
        return group1;
    }

    /** Second component group (G₂). */
    public FiniteGroup<E2> secondGroup() {
        return group2;
    }
}
