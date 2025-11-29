package d021248.group.product;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.api.Operation;

/**
 * Direct product G₁ × G₂ of two finite groups.
 * <p>
 * Elements are ordered pairs (g₁, g₂). The group operation is component-wise:
 * (a₁, a₂) * (b₁, b₂) = (a₁ *₁ b₁, a₂ *₂ b₂).
 * </p>
 * <p>
 * Order equals |G₁| · |G₂|.
 * </p>
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * {@code
 * CyclicGroup z2 = new CyclicGroup(2);
 * CyclicGroup z3 = new CyclicGroup(3);
 * DirectProduct<CyclicElement, CyclicElement> z2xz3 = new DirectProduct<>(z2, z3);
 * System.out.println(z2xz3.order()); // 6 (= 2 * 3)
 * }
 * </pre>
 *
 * @param <E1> type of elements in the first group
 * @param <E2> type of elements in the second group
 */
public final class DirectProduct<E1 extends Element, E2 extends Element>
        implements Group<ProductElement<E1, E2>> {
    private final Group<E1> group1;
    private final Group<E2> group2;
    private final Set<ProductElement<E1, E2>> elements;
    private final Operation<ProductElement<E1, E2>> op;
    private final ProductElement<E1, E2> identity;

    public DirectProduct(Group<E1> group1, Group<E2> group2) {
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
    public Group<E1> firstGroup() {
        return group1;
    }

    /** Second component group (G₂). */
    public Group<E2> secondGroup() {
        return group2;
    }
}
