package d021248.group.homomorphism;

import java.util.Objects;

import d021248.group.Group;
import d021248.group.api.Element;

/**
 * Concrete implementation of a group homomorphism with source and target groups.
 *
 * @param <E1> element type of source group
 * @param <E2> element type of target group
 */
public record Homomorphism<E1 extends Element, E2 extends Element>(
        Group<E1> source,
        Group<E2> target,
        GroupHomomorphism<E1, E2> mapping) implements GroupHomomorphism<E1, E2> {

    public Homomorphism {
        Objects.requireNonNull(source, "Source group cannot be null");
        Objects.requireNonNull(target, "Target group cannot be null");
        Objects.requireNonNull(mapping, "Mapping cannot be null");
    }

    @Override
    public E2 apply(E1 element) {
        return mapping.apply(element);
    }


}
