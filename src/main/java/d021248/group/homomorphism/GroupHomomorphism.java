package d021248.group.homomorphism;

import d021248.group.Group;
import d021248.group.api.Element;

/**
 * A structure-preserving map between two groups.
 * <p>
 * A group homomorphism φ: G → H satisfies φ(g₁ ∘ g₂) = φ(g₁) ∘ φ(g₂)
 * for all g₁, g₂ ∈ G.
 *
 * @param <E1> element type of the source group
 * @param <E2> element type of the target group
 */
@FunctionalInterface
public interface GroupHomomorphism<E1 extends Element, E2 extends Element> {

    /**
     * Apply the homomorphism to an element.
     *
     * @param element element from source group
     * @return image in target group
     */
    E2 apply(E1 element);

    /**
     * Source group (domain).
     *
     * @return the source group
     */
    default Group<E1> source() {
        throw new UnsupportedOperationException("Source group not specified");
    }

    /**
     * Target group (codomain).
     *
     * @return the target group
     */
    default Group<E2> target() {
        throw new UnsupportedOperationException("Target group not specified");
    }
}
