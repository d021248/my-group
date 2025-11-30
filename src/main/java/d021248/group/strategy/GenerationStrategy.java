package d021248.group.strategy;

import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;

/**
 * Strategy for selecting a (usually minimal) generating set for a finite group
 * instance.
 */
@FunctionalInterface
public interface GenerationStrategy<E extends Element> {
    Set<E> generators(Group<E> group);

    default String name() {
        return getClass().getSimpleName();
    }
}
