package d021248.group.automorphism;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.homomorphism.GroupHomomorphism;

/**
 * An automorphism of a group G is an isomorphism φ: G → G.
 * <p>
 * Automorphisms preserve group structure while permuting elements.
 * They form a group under composition: Aut(G).
 *
 * @param group   the group
 * @param mapping the automorphism function
 * @param <E>     element type of the group
 */
public record Automorphism<E extends Element>(
        Group<E> group,
        GroupHomomorphism<E, E> mapping) implements GroupHomomorphism<E, E> {

    @Override
    public E apply(E element) {
        return mapping.apply(element);
    }
}
