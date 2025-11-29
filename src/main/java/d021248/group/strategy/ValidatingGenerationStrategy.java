package d021248.group.strategy;

import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;

/** Optional extension adding validation hook for generated set semantics. */
public interface ValidatingGenerationStrategy<E extends Element> extends GenerationStrategy<E> {
    /**
     * Return true if generators pass internal validation (e.g., non-empty,
     * minimality).
     */
    boolean validates(Group<E> group, Set<E> generators);
}
